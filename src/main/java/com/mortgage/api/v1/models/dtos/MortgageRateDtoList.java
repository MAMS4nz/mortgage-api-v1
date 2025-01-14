package com.mortgage.api.v1.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MortgageRateDtoList {

    @Size(min = 1, max = 1000)
    private List<@Valid MortgageRateDto> rates;

}
