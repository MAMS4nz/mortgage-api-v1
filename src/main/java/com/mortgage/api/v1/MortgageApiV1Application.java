package com.mortgage.api.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class MortgageApiV1Application {

	public static void main(String[] args) {
		SpringApplication.run(MortgageApiV1Application.class, args);
	}

}
