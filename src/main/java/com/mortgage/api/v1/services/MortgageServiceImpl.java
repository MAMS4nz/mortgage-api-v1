package com.mortgage.api.v1.services;

import com.mortgage.api.v1.business.rules.mortgages.MortgageRule;
import com.mortgage.api.v1.mappers.MortgageMapper;
import com.mortgage.api.v1.models.domain.MortgageRate;
import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import com.mortgage.api.v1.models.dtos.MortgageFeasibilityDto;
import com.mortgage.api.v1.models.entities.MortgageRateEntity;
import com.mortgage.api.v1.repositories.MortgageRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Service
public class MortgageServiceImpl implements MortgageService {
    
    private final MortgageRateRepository mortgageRateRepository;
    private MortgageMapper mortgageMapper;
    private final List<MortgageRule> businessRules;
    
    @Autowired
    public MortgageServiceImpl(
            MortgageRateRepository mortgageRateRepository,
            MortgageMapper mortgageMapper,
            List<MortgageRule> businessRules
    ) {
        this.mortgageRateRepository = mortgageRateRepository;
        this.mortgageMapper = mortgageMapper;
        this.businessRules = businessRules;
    }
    
    
    @Override
    public List<MortgageRate> findAllRates() {
        return this.mortgageMapper.entityToDomain(
                this.mortgageRateRepository.findAll()
        );
    }

    protected BigDecimal calcFrenchAmortizationMonthlyPayment(
            MortgageEnquiryDto enquiryDto,
            MortgageRateEntity mortgageRateEntity
    ) {
        BigDecimal monthlyInterest = mortgageRateEntity
                .getYearlyInterestPercentageRate()
                .divide(
                        BigDecimal.valueOf(100L),
                        6,
                        RoundingMode.HALF_EVEN
                ) //To move from a percentage to a purely decimal value.
                .divide(
                        BigDecimal.valueOf(12L),
                        6,
                        RoundingMode.HALF_EVEN
                );
        BigDecimal commonFactor = (monthlyInterest.add(BigDecimal.ONE))
                .setScale(6, RoundingMode.HALF_EVEN)
                .pow(enquiryDto.getMaturityPeriodMonths());
        BigDecimal dividend = enquiryDto.getLoanPrincipal()
                .multiply(monthlyInterest)
                .multiply(commonFactor)
                .setScale(6, RoundingMode.HALF_EVEN);
        BigDecimal divisor = commonFactor
                .subtract(BigDecimal.ONE)
                .setScale(6, RoundingMode.HALF_EVEN);;
        return dividend.divide(
                divisor,
                2, //To return something not more fine-grained than Euro cents.
                RoundingMode.HALF_EVEN
        );
    }

    @Override
    public MortgageFeasibilityDto checkFeasibility(MortgageEnquiryDto enquiryDto) {

        int tranchesSizeMonths = 60;
        int maturityPeriodMonths = 420;
        if (enquiryDto.getMaturityPeriodMonths() != 480) {
            maturityPeriodMonths = (enquiryDto.getMaturityPeriodMonths() / tranchesSizeMonths) * tranchesSizeMonths;
        }
        var mortgageRateEntity = this.mortgageRateRepository
                .findByMaturityPeriodMonths(maturityPeriodMonths)
                .orElseThrow(
                        () -> new RuntimeException(
                                String.format(
                                        "We could not recover the mortgage rate entity corresponding to this maturity period: %d ",
                                        enquiryDto.getMaturityPeriodMonths()
                                )

                        )
                );
        var feasible = businessRules.stream().allMatch(rule -> rule.passed(enquiryDto));
        var feasibilityDto = MortgageFeasibilityDto.builder()
                .feasible(feasible)
                .build();
        if (feasible) {
            //We will apply the classic model of the French amortization, so that
            //the total amount of the debt (loan principal plus the accumulated interest)
            //is split up in identical payments all along the devolution period.
            feasibilityDto.setMonthlyPayment(
                    calcFrenchAmortizationMonthlyPayment(
                            enquiryDto,
                            mortgageRateEntity
                    )
            );
        } else {
            feasibilityDto.setMonthlyPayment(BigDecimal.ZERO);
        }
        return feasibilityDto;
    }

}
