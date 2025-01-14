package com.mortgage.api.v1.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "mortgage_rates")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = { "audit" })
@ToString
public class MortgageRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "maturity_period_months", nullable = false, unique = true)
    private Integer maturityPeriodMonths;

    @Column(name = "yearly_interest_percentage_rate", precision = 7, scale = 4, nullable = false)
    private BigDecimal yearlyInterestPercentageRate;

    @Embedded
    private AuditEntity audit;

}
