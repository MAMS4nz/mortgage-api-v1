package com.mortgage.api.v1.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MortgageRateDto {

    @Min(1)
    @NotNull
    private Integer id;

    @Min(120)
    @Max(480)
    @NotNull
    private Integer maturityPeriodMonths;

    @Min(0)
    @Max(100)
    @NotNull
    private BigDecimal yearlyInterestPercentageRate;

}
