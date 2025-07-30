package com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.mapper;

import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.enums.CurrencyEntity;

public class CurrencyMapper {

    public static Currency toDomain(CurrencyEntity entity) {
        return Currency.valueOf(entity.name());
    }

    public static CurrencyEntity toEntity(Currency domain) {
        return CurrencyEntity.valueOf(domain.name());
    }
}
