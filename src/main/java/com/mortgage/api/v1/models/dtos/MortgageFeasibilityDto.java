package com.mortgage.api.v1.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MortgageFeasibilityDto {

    @NotNull
    private Boolean feasible;

    @PositiveOrZero
    @NotNull
    private BigDecimal monthlyPayment;

}
