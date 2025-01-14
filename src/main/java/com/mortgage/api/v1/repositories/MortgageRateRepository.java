package com.mortgage.api.v1.repositories;

import com.mortgage.api.v1.models.entities.MortgageRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MortgageRateRepository extends JpaRepository<MortgageRateEntity, Integer> {

    Optional<MortgageRateEntity> findByMaturityPeriodMonths(Integer maturityPeriodMonths);

}
