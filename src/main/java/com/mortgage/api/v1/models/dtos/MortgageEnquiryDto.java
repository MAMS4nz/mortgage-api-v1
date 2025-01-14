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
public class MortgageEnquiryDto {

    @NotNull
    @Min(20000)
    @Max(1000000000)
    private BigDecimal yearlyIncomeEuros;

    @NotNull
    @Min(120)
    @Max(480)
    private Integer maturityPeriodMonths;

    @NotNull
    @Min(50000)
    @Max(50000000)
    private BigDecimal loanPrincipal;

    @NotNull
    @Min(20000)
    @Max(2000000000) //e. g.: Antilia
    private BigDecimal marketHomeValue;

}
