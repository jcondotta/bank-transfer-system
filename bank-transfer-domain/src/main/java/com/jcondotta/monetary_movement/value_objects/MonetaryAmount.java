package com.jcondotta.monetary_movement.value_objects;

import com.jcondotta.monetary_movement.exceptions.InvalidMonetaryAmountException;
import com.jcondotta.shared.valueobjects.Currency;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public record MonetaryAmount(BigDecimal amount, Currency currency) {

    public static final String AMOUNT_NOT_NULL_MESSAGE = "amount must not be null.";
    public static final String CURRENCY_NOT_NULL_MESSAGE = "currency must not be null.";

    public MonetaryAmount {
        requireNonNull(amount, AMOUNT_NOT_NULL_MESSAGE);
        requireNonNull(currency, CURRENCY_NOT_NULL_MESSAGE);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidMonetaryAmountException.negativeAmount();
        }
    }

    public static MonetaryAmount of(BigDecimal amount, Currency currency) {
        return new MonetaryAmount(amount, currency);
    }
}