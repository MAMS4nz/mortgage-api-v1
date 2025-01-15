package com.mortgage.api.v1.controllers;

import com.mortgage.api.v1.mappers.MortgageMapper;
import com.mortgage.api.v1.models.dtos.MortgageEnquiryDto;
import com.mortgage.api.v1.models.dtos.MortgageFeasibilityDto;
import com.mortgage.api.v1.models.dtos.MortgageRateDtoList;
import com.mortgage.api.v1.services.MortgageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@RestController
@Validated
@RequestMapping(
        path = "/api/v1/mortgages",
        produces = APPLICATION_JSON_VALUE
)
public class MortgageController {

    private final MortgageMapper mortgageMapper;
    private final MortgageService mortgageService;

    @Autowired
    public MortgageController(
            MortgageMapper mortgageMapper,
            MortgageService mortgageService
    ) {
        this.mortgageMapper = mortgageMapper;
        this.mortgageService = mortgageService;
    }

    @GetMapping("/rates")
    public ResponseEntity<MortgageRateDtoList> getRates() {
        return ResponseEntity.ok(
                MortgageRateDtoList.builder()
                        .rates(
                                this.mortgageMapper.domainToDto(
                                        this.mortgageService.findAllRates()
                                )
                        )
                        .build()
        );
    }

    @PostMapping("/enquiries")
    public ResponseEntity<MortgageFeasibilityDto> checkFeasibility(
            @RequestBody @Valid MortgageEnquiryDto enquiryDto
    ) {
        return ResponseEntity.ok(
                this.mortgageService.checkFeasibility(enquiryDto)
        );
    }

}
