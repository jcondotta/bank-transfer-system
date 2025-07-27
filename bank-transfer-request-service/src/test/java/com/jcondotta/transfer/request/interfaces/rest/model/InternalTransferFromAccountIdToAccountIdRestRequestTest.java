package com.jcondotta.transfer.request.interfaces.rest.model;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.request.interfaces.rest.validation.ValidationErrorMessages;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InternalTransferFromAccountIdToAccountIdRestRequestTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");
    private static final String TRANSFER_REFERENCE = "Invoice 437263";

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectViolation_whenRequestIsValid(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, currency.name(), TRANSFER_REFERENCE
        );

        assertDoesNotThrow(() -> VALIDATOR.validate(request));
        assertThat(request.senderAccountId()).isEqualTo(SENDER_ACCOUNT_ID.value());
        assertThat(request.recipientAccountId()).isEqualTo(RECIPIENT_ACCOUNT_ID.value());
        assertThat(request.amount()).isEqualTo(AMOUNT_200);
        assertThat(request.currency()).isEqualTo(currency.name());
        assertThat(request.reference()).isEqualTo(TRANSFER_REFERENCE);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenSenderAccountIdIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            null, RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, currency.name(), TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("senderAccountId");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.SENDER_ACCOUNT_ID_NOT_NULL);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenRecipientAccountIdIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), null, AMOUNT_200, currency.name(), TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("recipientAccountId");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.RECIPIENT_ACCOUNT_ID_NOT_NULL);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenAmountIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), null, currency.name(), TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("amount");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_AMOUNT_NOT_NULL);
            });
    }

    @ParameterizedTest
    @CsvSource({"-0.01, EUR", "-1.00, USD"})
    void shouldDetectConstraintViolation_whenAmountIsNegative(BigDecimal amount, Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), amount, currency.name(), TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("amount");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_AMOUNT_ZERO_OR_POSITIVE);
            });
    }

    @ParameterizedTest
    @CsvSource({"0.010, EUR", "1234567890123.123, USD"})
    void shouldDetectConstraintViolation_whenAmountHasInvalidPrecision(BigDecimal amount, Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), amount, currency.name(), TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("amount");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_AMOUNT_INVALID_PRECISION);
            });
    }

    @Test
    void shouldDetectConstraintViolation_whenCurrencyIsNull() {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, null, TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("currency");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_CURRENCY_NOT_NULL);
            });
    }

    @ParameterizedTest
    @ValueSource(strings = {"usd", "EURO", "12A"})
    void shouldDetectConstraintViolation_whenCurrencyIsInvalid(String currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, currency, TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(v -> {
                assertThat(v.getPropertyPath()).hasToString("currency");
                assertThat(v.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_CURRENCY_INVALID_FORMAT);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectViolation_whenReferenceIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, currency.name(), null
        );

        assertThat(VALIDATOR.validate(request)).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectViolation_whenReferenceIsEmpty(Currency currency) {
        var request = new InternalTransferFromAccountIdToAccountIdRestRequest(
            SENDER_ACCOUNT_ID.value(), RECIPIENT_ACCOUNT_ID.value(), AMOUNT_200, currency.name(), "  "
        );

        assertThat(VALIDATOR.validate(request)).isEmpty();
    }
}