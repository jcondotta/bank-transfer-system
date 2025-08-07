package com.jcondotta.transfer.processing.infrastructure.adapters.input.messaging.mapper;

import com.jcondotta.test_support.clock.TestClockExamples;
import com.jcondotta.test_support.iban.TestIbanExamples;
import com.jcondotta.transfer.domain.bank_account.valueobject.BankAccountId;
import com.jcondotta.transfer.domain.bank_account.valueobject.Iban;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalAccountSender;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalIbanRecipient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class InternalTransferRequestedEventMapperTest {

    private static final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    private static final BankAccountId SENDER_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final BankAccountId RECIPIENT_ACCOUNT_ID = BankAccountId.of(UUID.randomUUID());
    private static final Iban RECIPIENT_IBAN = Iban.of(TestIbanExamples.VALID_ITALY);

    private static final InternalAccountSender INTERNAL_ACCOUNT_SENDER = InternalAccountSender.of(SENDER_ACCOUNT_ID);
    private static final InternalAccountRecipient INTERNAL_ACCOUNT_RECIPIENT = InternalAccountRecipient.of(RECIPIENT_ACCOUNT_ID);
    private static final InternalIbanRecipient INTERNAL_IBAN_RECIPIENT = InternalIbanRecipient.of(RECIPIENT_IBAN);

    private static final String TRANSFER_REFERENCE = "transfer reference";

    private static final ZonedDateTime REQUESTED_AT = TestClockExamples.FIXED_DATE_TIME_UTC;

    private final InternalTransferRequestedEventMapper mapper = new InternalTransferRequestedEventMapperImpl();

//    @ParameterizedTest
//    @EnumSource(Currency.class)
//    void shouldMapToEventDomain_whenRecipientIsAccountId(Currency currency) {
//        var dto = new InternalTransferRequestedEventDTO(
//            INTERNAL_ACCOUNT_SENDER.identifier().type().toString(),
//            INTERNAL_ACCOUNT_SENDER.identifier().value(),
//            INTERNAL_ACCOUNT_RECIPIENT.identifier().type().toString(),
//            INTERNAL_ACCOUNT_RECIPIENT.identifier().value(),
//            AMOUNT_200,
//            currency.name(),
//            TRANSFER_REFERENCE,
//            REQUESTED_AT
//        );
//
//        assertThat(mapper.toDomain(dto))
//            .satisfies(domainEvent -> {
//                assertThat(domainEvent.internalPartySender()).isInstanceOf(InternalAccountSender.class);
//                assertThat(domainEvent.internalPartySender().identifier()).isEqualTo(INTERNAL_ACCOUNT_SENDER.identifier());
//                assertThat(domainEvent.internalPartyRecipient()).isInstanceOf(InternalAccountRecipient.class);
//                assertThat(domainEvent.internalPartyRecipient().identifier()).isEqualTo(INTERNAL_ACCOUNT_RECIPIENT.identifier());
//                assertThat(domainEvent.monetaryAmount().amount()).isEqualByComparingTo(AMOUNT_200);
//                assertThat(domainEvent.monetaryAmount().currency()).isEqualTo(currency);
//                assertThat(domainEvent.reference()).isEqualTo(TRANSFER_REFERENCE);
//                assertThat(domainEvent.requestedAt()).isEqualTo(REQUESTED_AT);
//            });
//    }
//
//    @ParameterizedTest
//    @EnumSource(Currency.class)
//    void shouldMapToEventDomain_whenRecipientIsIban(Currency currency) {
//        var dto = new InternalTransferRequestedEventDTO(
//            INTERNAL_ACCOUNT_SENDER.identifier().type().toString(),
//            INTERNAL_ACCOUNT_SENDER.identifier().value(),
//            INTERNAL_IBAN_RECIPIENT.identifier().type().toString(),
//            INTERNAL_IBAN_RECIPIENT.identifier().value(),
//            AMOUNT_200,
//            currency.name(),
//            TRANSFER_REFERENCE,
//            REQUESTED_AT
//        );
//
//        assertThat(mapper.toDomain(dto))
//            .satisfies(domainEvent -> {
//                assertThat(domainEvent.internalPartySender()).isInstanceOf(InternalAccountSender.class);
//                assertThat(domainEvent.internalPartySender().identifier()).isEqualTo(INTERNAL_ACCOUNT_SENDER.identifier());
//                assertThat(domainEvent.internalPartyRecipient()).isInstanceOf(InternalIbanRecipient.class);
//                assertThat(domainEvent.internalPartyRecipient().identifier()).isEqualTo(INTERNAL_IBAN_RECIPIENT.identifier());
//                assertThat(domainEvent.monetaryAmount().amount()).isEqualByComparingTo(AMOUNT_200);
//                assertThat(domainEvent.monetaryAmount().currency()).isEqualTo(currency);
//                assertThat(domainEvent.reference()).isEqualTo(TRANSFER_REFERENCE);
//                assertThat(domainEvent.requestedAt()).isEqualTo(REQUESTED_AT);
//            });
//    }
//
//    @ParameterizedTest
//    @EnumSource(Currency.class)
//    void shouldThrowException_whenSenderIsIban(Currency currency) {
//        var requestedEventDTO = new InternalTransferRequestedEventDTO(
//            InternalPartyIdentifierType.IBAN.toString(),
//            TestIbanExamples.VALID_ITALY,
//            InternalPartyIdentifierType.ACCOUNT_ID.toString(),
//            RECIPIENT_ACCOUNT_ID.toString(),
//            AMOUNT_200,
//            currency.name(),
//            TRANSFER_REFERENCE,
//            REQUESTED_AT
//        );
//
//        assertThatThrownBy(() -> mapper.toDomain(requestedEventDTO))
//            .isInstanceOf(UnsupportedOperationException.class)
//            .hasMessage("Sender IBAN is not yet implemented");
//    }
}
