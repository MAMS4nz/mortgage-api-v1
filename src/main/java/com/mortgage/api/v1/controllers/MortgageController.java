package com.mortgage.api.v1.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@RestController
@Validated
@RequestMapping(
        path = "/api/v1/mortgages",
        produces = APPLICATION_JSON_VALUE
)
public class MortgageController {

    @GetMapping("/active")
    public String getActiveProfile() {
        return "ACTIVE!";
    }

}
