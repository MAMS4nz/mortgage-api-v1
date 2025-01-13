package com.mortgage.api.v1.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "mortgage_rates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = { "created", "createdBy", "lastUpdated", "lastUpdatedBy" })
@ToString
public class MortgageRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "maturity_period_months", nullable = false, unique = true)
    private Integer maturityPeriodMonths;

    @Column(name = "yearly_interest_percentage_rate", precision = 10, scale = 4, nullable = false)
    private BigDecimal yearlyInterestPercentageRate;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "created_by", nullable = false, length = 60, updatable = false)
    private String createdBy;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "last_updated_by", nullable = false, length = 60)
    private String lastUpdatedBy;

}
