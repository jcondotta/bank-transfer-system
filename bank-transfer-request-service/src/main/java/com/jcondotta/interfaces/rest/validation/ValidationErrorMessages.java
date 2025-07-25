package com.jcondotta.interfaces.rest.validation;

public final class ValidationErrorMessages {

    private ValidationErrorMessages() {}

    public static final String SENDER_ACCOUNT_ID_NOT_NULL = "Sender account ID must not be null";
    public static final String RECIPIENT_IBAN_NOT_NULL = "Recipient IBAN must not be null";

    public static final String MONETARY_AMOUNT_NOT_NULL = "Amount must not be null";
    public static final String MONETARY_AMOUNT_ZERO_OR_POSITIVE = "Amount must be zero or greater";
    public static final String MONETARY_AMOUNT_INVALID_PRECISION = "Amount must have up to 12 digits and 2 decimal places";

    public static final String MONETARY_CURRENCY_NOT_NULL = "Currency must not be null";
    public static final String MONETARY_CURRENCY_INVALID_FORMAT = "Currency must be a 3-letter ISO code (e.g. EUR)";

    public static final String REFERENCE_TOO_LONG = "Reference must be at most 50 characters";
}
