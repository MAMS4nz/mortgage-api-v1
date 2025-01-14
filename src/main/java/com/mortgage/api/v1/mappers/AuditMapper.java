package com.mortgage.api.v1.mappers;

import com.mortgage.api.v1.models.domain.Audit;
import com.mortgage.api.v1.models.entities.AuditEntity;
import org.springframework.stereotype.Service;


@Service
public class AuditMapper {

    Audit entityToDomain(AuditEntity entity) {
        return Audit.builder()
                .created(entity.getCreated())
                .createdBy(entity.getCreatedBy())
                .lastUpdated(entity.getLastUpdated())
                .lastUpdatedBy(entity.getLastUpdatedBy())
                .build();
    }

}
