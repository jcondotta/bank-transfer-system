package com.jcondotta.transfer.domain.bank_account.enums;

import com.jcondotta.transfer.domain.bank_account.enums.AccountType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTypeTest {

    @Test
    void shouldAssertInfoCorrectly_whenAccountTypeIsChecking() {
        assertThat(AccountType.CHECKING)
            .satisfies(accountType -> {
                assertThat(accountType.isChecking()).isTrue();
                assertThat(accountType.isSavings()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenAccountTypeIsSavings() {
        assertThat(AccountType.SAVINGS)
            .satisfies(accountType -> {
                assertThat(accountType.isSavings()).isTrue();
                assertThat(accountType.isChecking()).isFalse();
            });
    }
}