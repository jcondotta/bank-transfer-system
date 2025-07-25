package com.jcondotta.banktransfer.events;

import com.jcondotta.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.shared.valueobjects.Currency;
import com.jcondotta.testsupport.clock.TestClockExamples;
import com.jcondotta.testsupport.iban.TestIbanExamples;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferRequestedEventTest {

    private static final InternalPartySender PARTY_SENDER = InternalAccountSender.of(UUID.randomUUID());
    private static final InternalPartyRecipient PARTY_RECIPIENT = InternalIbanRecipient.of(TestIbanExamples.VALID_ITALY);

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");
    private static final String REFERENCE = "Invoice #12345";

    private static final ZonedDateTime REQUESTED_AT = TestClockExamples.FIXED_DATE_TIME_UTC;

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldCreateEventCorrectly_whenUsingFactoryMethod(Currency currency) {
        var monetaryAmount = MonetaryAmount.of(AMOUNT_200, currency);

        var requestedEvent = InternalTransferRequestedEvent.of(
            PARTY_SENDER,
            PARTY_RECIPIENT,
            monetaryAmount,
            REFERENCE,
            REQUESTED_AT
        );

        assertThat(requestedEvent.internalPartySender()).isEqualTo(PARTY_SENDER);
        assertThat(requestedEvent.internalPartyRecipient()).isEqualTo(PARTY_RECIPIENT);
        assertThat(requestedEvent.monetaryAmount().amount()).isEqualTo(AMOUNT_200);
        assertThat(requestedEvent.monetaryAmount().currency()).isEqualTo(currency);
        assertThat(requestedEvent.reference()).isEqualTo(REFERENCE);
        assertThat(requestedEvent.requestedAt()).isEqualTo(REQUESTED_AT);
    }
}
