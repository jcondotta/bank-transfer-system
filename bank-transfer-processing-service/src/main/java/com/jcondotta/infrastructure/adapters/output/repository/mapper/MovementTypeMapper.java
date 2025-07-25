package com.jcondotta.infrastructure.adapters.output.repository.mapper;

import com.jcondotta.infrastructure.adapters.output.repository.enums.MovementTypeEntity;
import com.jcondotta.monetary_movement.enums.MovementType;

public final class MovementTypeMapper {

    private MovementTypeMapper() {}

    public static MovementTypeEntity toEntity(MovementType domain) {
        return switch (domain) {
            case DEBIT -> MovementTypeEntity.DEBIT;
            case CREDIT -> MovementTypeEntity.CREDIT;
        };
    }

    public static MovementType toDomain(MovementTypeEntity entity) {
        return switch (entity) {
            case DEBIT -> MovementType.DEBIT;
            case CREDIT -> MovementType.CREDIT;
        };
    }
}
