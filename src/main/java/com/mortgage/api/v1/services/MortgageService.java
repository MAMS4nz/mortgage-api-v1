package com.mortgage.api.v1.services;

import com.mortgage.api.v1.models.domain.MortgageRate;
import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import com.mortgage.api.v1.models.dtos.MortgageFeasibilityDto;

import java.util.List;

public interface MortgageService {

    List<MortgageRate> findAllRates();
    MortgageFeasibilityDto checkFeasibility(MortgageEnquiryDto enquiryDto);

}
