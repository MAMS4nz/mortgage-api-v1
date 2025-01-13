package com.mortgage.api.v1.repositories;

import com.mortgage.api.v1.models.entities.MortgageRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MortgageRateRepository extends JpaRepository<MortgageRate, Integer> {
}
