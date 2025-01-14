package com.mortgage.api.v1.business.rules.mortgages;

import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class MortgageIncomeRuleImpl implements MortgageRule {

    @Value("${mortgage.api.business.income.threshold.factor:#{4}}")
    private long incomeThresholdFactor;

    @Override
    public boolean passed(MortgageEnquiryDto enquiryDto) {
        var incomeThreshold = enquiryDto.getYearlyIncomeEuros().multiply(BigDecimal.valueOf(incomeThresholdFactor));
        return (enquiryDto.getLoanPrincipal().compareTo(incomeThreshold) <= 0);
    }

}
