package com.jcondotta.transfer.domain.banktransfer.exceptions;

import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.domain.bank_account.exceptions.BankAccountNotFoundException;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IdenticalInternalPartiesExceptionTest {

    private static final InternalPartySender INTERNAL_ACCOUNT_SENDER = InternalAccountSender.of(UUID.randomUUID());
    private static final InternalPartyRecipient INTERNAL_ACCOUNT_RECIPIENT = InternalAccountRecipient.of(UUID.randomUUID());

    @Test
    void shouldCreateException_whenPartiesAreValid() {
        var exception = new IdenticalInternalPartiesException(INTERNAL_ACCOUNT_SENDER, INTERNAL_ACCOUNT_RECIPIENT);
        var expectedExceptionProperties = Map.of(
            "partySender", INTERNAL_ACCOUNT_SENDER.toString(),
            "partyRecipient", INTERNAL_ACCOUNT_RECIPIENT.toString()
        );

        assertThat(exception)
            .isInstanceOf(IdenticalInternalPartiesException.class)
            .extracting(Throwable::getMessage)
            .isEqualTo(IdenticalInternalPartiesException.MESSAGE_TEMPLATE);

        assertThat(exception.getType()).isEqualTo("/errors/internal-transfer-identical-parties");
        assertThat(exception.getProperties())
            .hasSize(2)
            .isEqualTo(expectedExceptionProperties);
    }
}