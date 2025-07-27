package com.jcondotta.transfer.domain.monetary_movement.exceptions;

public class InvalidMonetaryAmountException extends RuntimeException {

    public static final String AMOUNT_NOT_NEGATIVE_TEMPLATE_MESSAGE = "monetaryAmount.amount.notNegative";

    public InvalidMonetaryAmountException(String message) {
        super(message);
    }

    public static InvalidMonetaryAmountException negativeAmount() {
        return new InvalidMonetaryAmountException(AMOUNT_NOT_NEGATIVE_TEMPLATE_MESSAGE);
    }
}
