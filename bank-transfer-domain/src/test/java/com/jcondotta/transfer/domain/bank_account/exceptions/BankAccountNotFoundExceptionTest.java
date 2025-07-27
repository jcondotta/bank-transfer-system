package com.jcondotta.transfer.domain.bank_account.exceptions;

import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BankAccountNotFoundExceptionTest {

    private static final BankAccountId BANK_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban VALID_SPANISH_IBAN = Iban.of(TestIbanExamples.VALID_SPAIN);

    @Test
    void shouldUseAccountIdTemplateAndIdentifier_whenCreatedWithAccountId() {
        var exception = new BankAccountNotFoundException(BANK_ACCOUNT_ID);

        assertThat(exception)
            .isInstanceOf(BankAccountNotFoundException.class)
            .extracting(Throwable::getMessage)
            .isEqualTo(BankAccountNotFoundException.BANK_ACCOUNT_BY_ACCOUNT_ID_NOT_FOUND_TEMPLATE);

        assertThat(exception.getIdentifiers())
            .containsExactly(BANK_ACCOUNT_ID.value());
    }

    @Test
    void shouldUseIbanTemplateAndIdentifier_whenCreatedWithIban() {
        var exception = new BankAccountNotFoundException(VALID_SPANISH_IBAN);

        assertThat(exception)
            .isInstanceOf(BankAccountNotFoundException.class)
            .extracting(Throwable::getMessage)
            .isEqualTo(BankAccountNotFoundException.BANK_ACCOUNT_BY_IBAN_NOT_FOUND_TEMPLATE);

        assertThat(exception.getIdentifiers())
            .containsExactly(VALID_SPANISH_IBAN.value());
    }
}
