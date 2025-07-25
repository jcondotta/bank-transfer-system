package com.jcondotta.bank_account.valueobject;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Objects;

public record Iban(String value) {

    public static final String IBAN_NOT_NULL_MESSAGE = "IBAN must not be null.";
    public static final String IBAN_INVALID_MESSAGE = "IBAN format is invalid.";

    private static final int MIN_IBAN_LENGTH = 15;
    private static final int MAX_IBAN_LENGTH = 34;

    public Iban {
        Objects.requireNonNull(value, IBAN_NOT_NULL_MESSAGE);

        String cleaned = normalize(value);

        if (!isValidFormat(cleaned) || !hasValidChecksum(cleaned)) {
            throw new IllegalArgumentException(IBAN_INVALID_MESSAGE);
        }

        value = cleaned;
    }

    private static String normalize(String raw) {
        return raw.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }

    private static boolean isValidFormat(String iban) {
        if (iban.length() < MIN_IBAN_LENGTH || iban.length() > MAX_IBAN_LENGTH) {
            return false;
        }

        return iban.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]+$");
    }

    private static boolean hasValidChecksum(String iban) {
        String reformatted = iban.substring(4) + iban.substring(0, 4);

        StringBuilder numeric = new StringBuilder();
        for (char c : reformatted.toCharArray()) {
            if (Character.isDigit(c)) {
                numeric.append(c);
            } else if (Character.isLetter(c)) {
                numeric.append((int) c - 55); // A=10, ..., Z=35
            } else {
                return false;
            }
        }

        return mod97(numeric.toString()) == 1;
    }

    private static int mod97(String numericIban) {
        BigInteger ibanNumber = new BigInteger(numericIban);
        return ibanNumber.mod(BigInteger.valueOf(97)).intValue();
    }


    public static Iban of(String value) {
        return new Iban(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
