package com.mortgage.api.v1.business.rules.mortgages;

import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import org.springframework.stereotype.Component;


@Component
public class MortgageHomeValueRuleImpl implements MortgageRule {

    @Override
    public boolean passed(MortgageEnquiryDto enquiryDto) {
        return (enquiryDto.getLoanPrincipal().compareTo(enquiryDto.getMarketHomeValue()) <= 0);
    }

}
