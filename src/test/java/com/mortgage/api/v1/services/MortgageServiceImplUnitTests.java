package com.mortgage.api.v1.services;

import com.mortgage.api.v1.business.rules.mortgages.MortgageRule;
import com.mortgage.api.v1.mappers.MortgageMapper;
import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import com.mortgage.api.v1.models.entities.MortgageRateEntity;
import com.mortgage.api.v1.repositories.MortgageRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MortgageServiceImplUnitTests {


    @Mock
    private MortgageRateRepository mortgageRateRepository;

    @Mock
    private MortgageMapper mortgageMapper;

    @Mock
    private List<MortgageRule> businessRules;

    @InjectMocks
    private MortgageServiceImpl mortgageService;


    @Test
    public void testCheckFeasibility() {

        var mortgageEnquiryDto = MortgageEnquiryDto.builder()
                .yearlyIncomeEuros(
                        BigDecimal.valueOf(60000L)
                )
                .maturityPeriodMonths(120)
                .loanPrincipal(
                        BigDecimal.valueOf(100000L)
                )
                .marketHomeValue(
                        BigDecimal.valueOf(450000)
                )
                .build();

        var mortgageRateEntityOneTwenty = MortgageRateEntity.builder()
                .id(1)
                .maturityPeriodMonths(120)
                .yearlyInterestPercentageRate(
                        BigDecimal.valueOf(37500L, 4)
                )
                .build();
        var mortgageRateEntityFourTwenty = MortgageRateEntity.builder()
                .id(1)
                .maturityPeriodMonths(240)
                .yearlyInterestPercentageRate(
                        BigDecimal.valueOf(50000L, 4)
                )
                .build();

        MortgageServiceImpl mortgageServiceSpy = Mockito.spy(this.mortgageService);

        when(mortgageRateRepository.findByMaturityPeriodMonths(Mockito.anyInt())).thenAnswer(invocation -> {
            int id = invocation.getArgument(0);
            if (id == 120) {
                return Optional.of(mortgageRateEntityOneTwenty);
            }
            if (id == 420) {
                return Optional.of(mortgageRateEntityFourTwenty);
            }
            //I.O.C.:
            throw new RuntimeException(
                    String.format(
                            "We could not recover the mortgage rate entity corresponding to this maturity period: %d ",
                            mortgageEnquiryDto.getMaturityPeriodMonths()
                    )
            );
        });


        Stream<MortgageRule> mockStream = mock(Stream.class);
        when(businessRules.stream()).thenReturn(mockStream);
        when(mockStream.allMatch(Mockito.any())).thenReturn(true);

        when(mortgageServiceSpy
                .calcFrenchAmortizationMonthlyPayment(
                        mortgageEnquiryDto,
                        mortgageRateEntityOneTwenty
                )
        ).thenReturn(BigDecimal.valueOf(100061L, 2));

        //Happy paths -

        //1
        var feasibilityDto = mortgageServiceSpy.checkFeasibility(mortgageEnquiryDto);
        assertTrue(feasibilityDto.getFeasible());
        assertEquals(BigDecimal.valueOf(100061L, 2), feasibilityDto.getMonthlyPayment());

        //2
        mortgageEnquiryDto.setMaturityPeriodMonths(480);
        when(mortgageServiceSpy
                .calcFrenchAmortizationMonthlyPayment(
                        mortgageEnquiryDto,
                        mortgageRateEntityFourTwenty
                )
        ).thenReturn(BigDecimal.valueOf(48222L, 2));
        feasibilityDto = mortgageServiceSpy.checkFeasibility(mortgageEnquiryDto);
        assertTrue(feasibilityDto.getFeasible());
        assertEquals(BigDecimal.valueOf(48222L, 2), feasibilityDto.getMonthlyPayment());

        //Not-so-happy paths

        //1
        Stream<MortgageRule> fakeStream = mock(Stream.class);
        when(businessRules.stream()).thenReturn(fakeStream);
        when(fakeStream.allMatch(Mockito.any())).thenReturn(false);
        feasibilityDto = mortgageServiceSpy.checkFeasibility(mortgageEnquiryDto);
        assertFalse(feasibilityDto.getFeasible());
        assertEquals(BigDecimal.ZERO, feasibilityDto.getMonthlyPayment());

        //2
        mortgageEnquiryDto.setMaturityPeriodMonths(500);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mortgageServiceSpy.checkFeasibility(mortgageEnquiryDto);
        });
        assertEquals(
                "We could not recover the mortgage rate entity corresponding to this maturity period: 500 ",
                exception.getMessage()
        );

    }

    @Test
    public void testCalcFrenchAmortizationMonthlyPayment() {

        BigDecimal monthlyPayment;
        var enquiryDto = MortgageEnquiryDto.builder().build();
        var mortgageRateEntity = MortgageRateEntity.builder().build();

        //1
        enquiryDto.setYearlyIncomeEuros(BigDecimal.valueOf(60000L));
        enquiryDto.setMaturityPeriodMonths(120);
        enquiryDto.setLoanPrincipal(BigDecimal.valueOf(100000L));
        enquiryDto.setMarketHomeValue(BigDecimal.valueOf(450000L));

        mortgageRateEntity.setMaturityPeriodMonths(120);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(37500L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(100061L, 2), monthlyPayment);

        //2
        enquiryDto.setMaturityPeriodMonths(136);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(90371L, 2), monthlyPayment);

        //3
        enquiryDto.setMaturityPeriodMonths(180);
        mortgageRateEntity.setMaturityPeriodMonths(180);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(40000L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(73967L, 2), monthlyPayment);

        //4
        enquiryDto.setMaturityPeriodMonths(188);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(71671L, 2), monthlyPayment);

        //5
        enquiryDto.setMaturityPeriodMonths(240);
        mortgageRateEntity.setMaturityPeriodMonths(240);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(42500L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(61926L, 2), monthlyPayment);

        //6
        enquiryDto.setMaturityPeriodMonths(242);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(61601L, 2), monthlyPayment);

        //7
        enquiryDto.setMaturityPeriodMonths(300);
        mortgageRateEntity.setMaturityPeriodMonths(300);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(45000L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(55583L, 2), monthlyPayment);

        //8
        enquiryDto.setMaturityPeriodMonths(310);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(54616L, 2), monthlyPayment);

        //9
        enquiryDto.setMaturityPeriodMonths(360);
        mortgageRateEntity.setMaturityPeriodMonths(360);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(47500L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(52162L, 2), monthlyPayment);

        //7
        enquiryDto.setMaturityPeriodMonths(363);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(51968L, 2), monthlyPayment);

        //8
        enquiryDto.setMaturityPeriodMonths(420);
        mortgageRateEntity.setMaturityPeriodMonths(420);
        mortgageRateEntity.setYearlyInterestPercentageRate(BigDecimal.valueOf(50000L, 4));

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(50471L, 2), monthlyPayment);

        //9
        enquiryDto.setMaturityPeriodMonths(430);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(50041L, 2), monthlyPayment);

        //10
        enquiryDto.setMaturityPeriodMonths(480);

        monthlyPayment = mortgageService.calcFrenchAmortizationMonthlyPayment(
                enquiryDto,
                mortgageRateEntity
        );
        assertEquals(BigDecimal.valueOf(48222L, 2), monthlyPayment);

    }

}
