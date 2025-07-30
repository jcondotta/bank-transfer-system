package com.jcondotta.transfer.request.interfaces.messaging.producer;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.banktransfer.events.InternalTransferRequestedEvent;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalIbanRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalPartyIdentifierType;
import com.jcondotta.transfer.domain.monetary_movement.value_objects.MonetaryAmount;
import com.jcondotta.transfer.domain.shared.valueobjects.Currency;
import com.jcondotta.transfer.request.interfaces.messaging.model.InternalTransferRequestedEventDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InternalTransferRequestedEventMapperTest {

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban RECIPIENT_IBAN = Iban.of(TestIbanExamples.VALID_ITALY);
    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");
    private static final String TRANSFER_REFERENCE = "Invoice #437263";

    private static final ZonedDateTime REQUESTED_AT = TestClockExamples.FIXED_DATE_TIME_MADRID;

    private final InternalTransferRequestedEventMapper mapper = new InternalTransferRequestedEventMapperImpl();

    @ParameterizedTest
    @EnumSource(Currency.class)
    void shouldMapDomainEventToEventDTO_whenEventIsValid(Currency currency) {
        var event = InternalTransferRequestedEvent.of(
            InternalAccountSender.of(SENDER_ACCOUNT_ID),
            InternalIbanRecipient.of(RECIPIENT_IBAN),
            MonetaryAmount.of(AMOUNT_200, currency),
            TRANSFER_REFERENCE,
            REQUESTED_AT
        );

        assertThat(mapper.toEventDTO(event))
            .isInstanceOf(InternalTransferRequestedEventDTO.class)
            .satisfies(dto -> {
                assertThat(dto.senderIdentifierType()).isEqualTo(InternalPartyIdentifierType.ACCOUNT_ID.toString());
                assertThat(dto.senderIdentifierValue()).isEqualTo(SENDER_ACCOUNT_ID.toString());
                assertThat(dto.recipientIdentifierType()).isEqualTo(InternalPartyIdentifierType.IBAN.toString());
                assertThat(dto.recipientIdentifierValue()).isEqualTo(RECIPIENT_IBAN.value());
                assertThat(dto.amount()).isEqualTo(AMOUNT_200);
                assertThat(dto.currency()).isEqualTo(currency.name());
                assertThat(dto.reference()).isEqualTo(TRANSFER_REFERENCE);
                assertThat(dto.requestedAt()).isEqualTo(REQUESTED_AT);
            });
    }
}