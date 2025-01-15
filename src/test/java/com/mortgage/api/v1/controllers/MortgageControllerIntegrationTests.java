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
    public void testRatesPresentStatus200() throws Exception {
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

    /*
    @Test
    void evaluateMortgageLoan_shouldReturnFeasibility() throws Exception {
        // JSON de entrada
        String requestBody = """
            {
                "loanAmount": 200000,
                "maturityPeriodMonths": 240,
                "yearlyIncome": 50000,
                "existingDebts": 10000
            }
        """;

        // Ejecutar la petición POST
        mvc.perform(post("/api/mortgages/evaluate")
                        .contentType(MediaType.APPLICATION_JSON) // Tipo de contenido
                        .content(requestBody))                  // Cuerpo de la petición
                .andExpect(status().isOk())             // Validar que responde 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Validar el tipo de contenido
                .andExpect(jsonPath("$.feasible").value(true)) // Validar que la respuesta indica factibilidad
                .andExpect(jsonPath("$.interestRate").value(4.5)) // Validar el interés calculado
                .andExpect(jsonPath("$.monthlyPayment").value(1012.5)); // Validar el pago mensual
    }
    */
}
