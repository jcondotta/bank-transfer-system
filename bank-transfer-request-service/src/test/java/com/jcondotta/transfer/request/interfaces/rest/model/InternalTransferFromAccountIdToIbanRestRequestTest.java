package com.jcondotta.transfer.request.interfaces.rest.model;

import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.request.interfaces.rest.validation.ValidationErrorMessages;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.test_support.iban.TestIbanExamples;
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

class InternalTransferFromAccountIdToIbanRestRequestTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban RECIPIENT_IBAN = Iban.of(TestIbanExamples.VALID_SPAIN);

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final String TRANSFER_REFERENCE = "Invoice 437263";

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectViolation_whenRequestIsValid(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), AMOUNT_200, currency.name(), TRANSFER_REFERENCE);
        assertDoesNotThrow(() -> VALIDATOR.validate(request));

        assertThat(request.senderAccountId()).isEqualTo(SENDER_ACCOUNT_ID.value());
        assertThat(request.recipientIban()).isEqualTo(RECIPIENT_IBAN.value());
        assertThat(request.amount()).isEqualTo(AMOUNT_200);
        assertThat(request.currency()).isEqualTo(currency.name());
        assertThat(request.reference()).isEqualTo(TRANSFER_REFERENCE);
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenBankAccountIdIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(null, RECIPIENT_IBAN.value(), AMOUNT_200, currency.name(), TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("senderAccountId");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.SENDER_ACCOUNT_ID_NOT_NULL);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenRecipientIbanIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), null, AMOUNT_200, currency.name(), TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("recipientIban");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.RECIPIENT_IBAN_NOT_NULL);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldDetectConstraintViolation_whenAmountIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), null, currency.name(), TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("amount");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.MONETARY_AMOUNT_NOT_NULL);
            });
    }

    @ParameterizedTest
    @CsvSource({"-0.01, EUR", "-1.00, USD", "-0.17, USD"})
    void shouldDetectConstraintViolation_whenAmountIsNegative(BigDecimal amount, Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), amount, currency.name(), TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("amount");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.MONETARY_AMOUNT_ZERO_OR_POSITIVE);
            });
    }

    @ParameterizedTest
    @CsvSource({"0.010, EUR", "1234567890123.123, USD", "1234567890123.17, USD"})
    void shouldDetectConstraintViolation_whenAmountHasInvalidPrecision(BigDecimal amount, Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), amount, currency.name(), TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("amount");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.MONETARY_AMOUNT_INVALID_PRECISION);
            });
    }


    @Test
    void shouldDetectConstraintViolation_whenCurrencyIsNull() {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), AMOUNT_200, null, TRANSFER_REFERENCE);

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("currency");
                assertThat(violation.getMessage()).hasToString(ValidationErrorMessages.MONETARY_CURRENCY_NOT_NULL);
            });
    }

    @ParameterizedTest
    @ValueSource(strings = {"eur", "EURO", "   "})
    void shouldDetectConstraintViolation_whenCurrencyFormatIsInvalid(String invalidCurrency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(
            SENDER_ACCOUNT_ID.value(),
            RECIPIENT_IBAN.value(),
            AMOUNT_200,
            invalidCurrency,
            TRANSFER_REFERENCE
        );

        assertThat(VALIDATOR.validate(request))
            .hasSize(1)
            .first()
            .satisfies(violation -> {
                assertThat(violation.getPropertyPath()).hasToString("currency");
                assertThat(violation.getMessage()).isEqualTo(ValidationErrorMessages.MONETARY_CURRENCY_INVALID_FORMAT);
            });
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectConstraintViolation_whenReferenceIsNull(Currency currency) {
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), AMOUNT_200, currency.name(), null);

        assertThat(VALIDATOR.validate(request)).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldNotDetectConstraintViolation_whenReferenceIsEmpty(Currency currency) {
        var blankReference = "   ";
        var request = new InternalTransferFromAccountIdToIbanRestRequest(SENDER_ACCOUNT_ID.value(), RECIPIENT_IBAN.value(), AMOUNT_200, currency.name(), blankReference);

        assertThat(VALIDATOR.validate(request)).isEmpty();
    }
}