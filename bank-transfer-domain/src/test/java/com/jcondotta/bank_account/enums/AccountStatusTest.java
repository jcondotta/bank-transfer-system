package com.jcondotta.bank_account.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStatusTest {

    @Test
    void shouldAssertInfoCorrectly_whenAccountStatusIsActive() {
        assertThat(AccountStatus.ACTIVE)
            .satisfies(accountStatus -> {
                assertThat(accountStatus.isActive()).isTrue();
                assertThat(accountStatus.isCancelled()).isFalse();
                assertThat(accountStatus.isPending()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenAccountStatusIsCancelled() {
        assertThat(AccountStatus.CANCELLED)
            .satisfies(accountStatus -> {
                assertThat(accountStatus.isCancelled()).isTrue();
                assertThat(accountStatus.isActive()).isFalse();
                assertThat(accountStatus.isPending()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenAccountStatusIsPending() {
        assertThat(AccountStatus.PENDING)
            .satisfies(accountStatus -> {
                assertThat(accountStatus.isPending()).isTrue();
                assertThat(accountStatus.isCancelled()).isFalse();
                assertThat(accountStatus.isActive()).isFalse();
            });
    }
}