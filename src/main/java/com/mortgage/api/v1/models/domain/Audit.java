package com.mortgage.api.v1.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Audit {

    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

}
