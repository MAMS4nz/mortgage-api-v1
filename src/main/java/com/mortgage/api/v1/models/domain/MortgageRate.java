package com.mortgage.api.v1.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MortgageRate {

    private Integer id;
    private Integer maturityPeriodMonths;
    private BigDecimal yearlyInterestPercentageRate;
    private Audit audit;

}
