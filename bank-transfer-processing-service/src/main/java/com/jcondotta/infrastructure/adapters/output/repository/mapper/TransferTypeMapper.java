package com.jcondotta.infrastructure.adapters.output.repository.mapper;

import com.jcondotta.banktransfer.enums.TransferType;
import com.jcondotta.infrastructure.adapters.output.repository.enums.TransferTypeEntity;

public final class TransferTypeMapper {

    private TransferTypeMapper() {}

    public static TransferTypeEntity toEntity(TransferType domain) {
        return switch (domain) {
            case INTERNAL -> TransferTypeEntity.INTERNAL;
            case OUTGOING_EXTERNAL -> TransferTypeEntity.OUTGOING_EXTERNAL;
            case INCOMING_EXTERNAL -> TransferTypeEntity.INCOMING_EXTERNAL;
        };
    }

    public static TransferType toDomain(TransferTypeEntity entity) {
        return switch (entity) {
            case INTERNAL -> TransferType.INTERNAL;
            case OUTGOING_EXTERNAL -> TransferType.OUTGOING_EXTERNAL;
            case INCOMING_EXTERNAL -> TransferType.INCOMING_EXTERNAL;
        };
    }
}
