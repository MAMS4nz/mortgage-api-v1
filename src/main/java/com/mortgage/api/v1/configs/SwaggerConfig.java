package com.mortgage.api.v1.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .components(new Components()) //Security concerns would come here.
                .info(new Info()
                        .title("Mortgage API V1 documentation [OpenAPI 3.0]")
                        .description("Swagger documentation via SpringDoc OpenAPI")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MMORCIL1")
                                .email("735865@cognizant.com")
                                .url("https://www.cognizant.com/es/es"))
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

}
