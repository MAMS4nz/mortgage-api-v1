package com.mortgage.api.v1.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration-tests.properties")
public class MortgageControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testRatesPresent_HappyPaths() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/mortgages/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(
                        jsonPath("$.rates[*].maturityPeriodMonths",
                        contains(120, 180, 240, 300, 360, 420))
                );
    }

    @Test
    void testMortgageFeasibility_HappyPaths() throws Exception {

        //1- "maturityPeriodMonths" lower limit.
        String requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 120,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(1000.61));

        //2- Interest rates' tranche #0
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 136,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(903.71));

        //3- Threshold
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 180,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(739.67));

        //4- Interest rates' tranche #1
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 188,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(716.71));

        //5- Threshold
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 240,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(619.26));

        //6- Interest rates' tranche #2
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 242,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(616.01));

        //7- Threshold
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 300,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(555.83));

        //8- Interest rates' tranche #3
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 310,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(546.16));

        //9- Threshold
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 360,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(521.62));

        //10- Interest rates' tranche #4
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 363,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(519.68));

        //11- Threshold
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 420,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(504.71));

        //12- Interest rates' tranche #5
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 466,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(486.81));

        //13- "maturityPeriodMonths" upper limit.
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 480,
                "loanPrincipal": 100000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(482.22));

        //14- First business rule corner case.
        requestBody = """
            {
                "yearlyIncomeEuros": 60000,
                "maturityPeriodMonths": 360,
                "loanPrincipal": 240000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(1251.90));

        //15- Second business rule corner case.
        requestBody = """
            {
                "yearlyIncomeEuros": 200000,
                "maturityPeriodMonths": 360,
                "loanPrincipal": 450000,
                "marketHomeValue": 450000
            }
        """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyPayment").value(2347.30));

    }


    @Test
    void testMortgageFeasibility_SorryPaths() throws Exception {

        //1- First business rule ("income threshold").
        String requestBody = """
                    {
                        "yearlyIncomeEuros": 60000,
                        "maturityPeriodMonths": 360,
                        "loanPrincipal": 240000.01,
                        "marketHomeValue": 450000
                    }
                """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(false))
                .andExpect(jsonPath("$.monthlyPayment").value(0));

        //2- Second business rule ("loan principal vs market home value").
        requestBody = """
                    {
                        "yearlyIncomeEuros": 80000,
                        "maturityPeriodMonths": 360,
                        "loanPrincipal": 250000.01,
                        "marketHomeValue": 250000
                    }
                """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.feasible").value(false))
                .andExpect(jsonPath("$.monthlyPayment").value(0));

    }


    @Test
    void testMortgageFeasibility_FailingValidations() throws Exception {

        //Checking for nulls.
        String requestBody = "{ }";

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("NotNull.yearlyIncomeEuros")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("NotNull.maturityPeriodMonths")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("NotNull.loanPrincipal")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("NotNull.marketHomeValue")
                        )
                );

        //Checking the minimum values.
        requestBody = """
                    {
                        "yearlyIncomeEuros": 19999.99,
                        "maturityPeriodMonths": 119,
                        "loanPrincipal": 49999.99,
                        "marketHomeValue": 19999.99
                    }
                """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Min.yearlyIncomeEuros")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Min.maturityPeriodMonths")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Min.loanPrincipal")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Min.marketHomeValue")
                        )
                );

        //Checking the maximum values.
        requestBody = """
                    {
                        "yearlyIncomeEuros": 1000000000.01,
                        "maturityPeriodMonths": 481,
                        "loanPrincipal": 50000000.01,
                        "marketHomeValue": 2000000000.01
                    }
                """;

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/mortgages/enquiries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Max.yearlyIncomeEuros")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Max.maturityPeriodMonths")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Max.loanPrincipal")
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.message",
                                containsStringIgnoringCase("Max.marketHomeValue")
                        )
                );

    }

}
