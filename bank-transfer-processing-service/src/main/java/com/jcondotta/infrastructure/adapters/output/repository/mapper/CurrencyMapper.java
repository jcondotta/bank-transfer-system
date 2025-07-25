package com.jcondotta.infrastructure.adapters.output.repository.mapper;

import com.jcondotta.infrastructure.adapters.output.repository.enums.CurrencyEntity;
import com.jcondotta.shared.valueobjects.Currency;

public class CurrencyMapper {

    public static Currency toDomain(CurrencyEntity entity) {
        return Currency.valueOf(entity.name());
    }

    public static CurrencyEntity toEntity(Currency domain) {
        return CurrencyEntity.valueOf(domain.name());
    }
}
