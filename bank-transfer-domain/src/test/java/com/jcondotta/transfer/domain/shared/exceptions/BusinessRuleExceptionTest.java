package com.jcondotta.transfer.domain.shared.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRuleExceptionTest {

    @Test
    void shouldReturnMessageAndDefaultValues_whenSubclassDoesNotOverrideProperties() {
        var exception = new DummyBusinessRuleException("some.business.rule");

        assertThat(exception.getMessage()).isEqualTo("some.business.rule");
        assertThat(exception.getType()).isEqualTo("/errors/dummy");
        assertThat(exception.getProperties()).isEmpty();
    }

    // Dummy subclass to cover abstract BusinessRuleException base behavior
    static class DummyBusinessRuleException extends BusinessRuleException {
        public DummyBusinessRuleException(String message) {
            super(message);
        }

        @Override
        public String getType() {
            return "/errors/dummy";
        }
    }
}
