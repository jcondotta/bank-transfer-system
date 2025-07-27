package com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.entity;

import com.jcondotta.transfer.processing.infrastructure.adapters.output.repository.enums.CurrencyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class MonetaryAmountEmbeddable {

    protected MonetaryAmountEmbeddable() {}

    public MonetaryAmountEmbeddable(BigDecimal amount, CurrencyEntity currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Column(name = "amount", nullable = false, precision = 38, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3)
    private CurrencyEntity currency;

    public static MonetaryAmountEmbeddable of(BigDecimal amount, CurrencyEntity currency) {
        return new MonetaryAmountEmbeddable(amount, currency);
    }
}