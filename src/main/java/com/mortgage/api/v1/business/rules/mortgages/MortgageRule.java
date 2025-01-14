package com.mortgage.api.v1.business.rules.mortgages;

import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;

@FunctionalInterface
public interface MortgageRule {

    boolean passed(MortgageEnquiryDto enquiryDto);

}
