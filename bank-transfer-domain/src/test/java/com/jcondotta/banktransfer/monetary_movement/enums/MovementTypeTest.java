package com.jcondotta.banktransfer.monetary_movement.enums;

import com.jcondotta.monetary_movement.enums.MovementType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MovementTypeTest {

    @Test
    void shouldAssertInfoCorrectly_whenMovementTypeIsDebit() {
        assertThat(MovementType.DEBIT)
            .satisfies(movementType -> {
                assertThat(movementType.isDebit()).isTrue();
                assertThat(movementType.isCredit()).isFalse();
            });
    }

    @Test
    void shouldAssertInfoCorrectly_whenMovementTypeIsCredit() {
        assertThat(MovementType.CREDIT)
            .satisfies(movementType -> {
                assertThat(movementType.isCredit()).isTrue();
                assertThat(movementType.isDebit()).isFalse();
            });
    }
}