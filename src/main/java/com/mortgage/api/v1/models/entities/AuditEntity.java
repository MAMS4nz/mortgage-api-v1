package com.mortgage.api.v1.models.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditEntity {

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "created_by", nullable = false, length = 60, updatable = false)
    private String createdBy;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @Column(name = "last_updated_by", nullable = false, length = 60)
    private String lastUpdatedBy;

}
