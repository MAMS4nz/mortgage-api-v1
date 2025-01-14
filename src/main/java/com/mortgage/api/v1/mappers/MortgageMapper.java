package com.mortgage.api.v1.mappers;

import com.mortgage.api.v1.models.domain.MortgageRate;
import com.mortgage.api.v1.models.dtos.MortgageRateDto;
import com.mortgage.api.v1.models.entities.MortgageRateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MortgageMapper {

    private final AuditMapper auditMapper;

    @Autowired
    public MortgageMapper(
            AuditMapper auditMapper
    ) {
        this.auditMapper = auditMapper;
    }

    public MortgageRate entityToDomain(MortgageRateEntity entity) {
        return MortgageRate.builder()
                .id(entity.getId())
                .maturityPeriodMonths(entity.getMaturityPeriodMonths())
                .yearlyInterestPercentageRate(entity.getYearlyInterestPercentageRate())
                .audit(
                        auditMapper.entityToDomain(entity.getAudit())
                )
                .build();
    }

    public List<MortgageRate> entityToDomain(List<MortgageRateEntity> entities) {
        return entities.stream()
                .map(this::entityToDomain)
                .toList();
    }

    public MortgageRateDto domainToDto(MortgageRate domain) {
        return MortgageRateDto.builder()
                .id(domain.getId())
                .maturityPeriodMonths(domain.getMaturityPeriodMonths())
                .yearlyInterestPercentageRate(domain.getYearlyInterestPercentageRate())
                .build();
    }

    public List<MortgageRateDto> domainToDto(List<MortgageRate> domainElements) {
        return domainElements.stream()
                .map(this::domainToDto)
                .toList();
    }

}
