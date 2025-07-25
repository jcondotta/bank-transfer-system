package com.jcondotta.infrastructure.adapters.output.repository.mapper;

import com.jcondotta.banktransfer.entity.BankTransfer;
import com.jcondotta.banktransfer.valueobjects.transfer_entry.InternalTransferEntry;
import com.jcondotta.infrastructure.adapters.output.repository.entity.BankTransferEntity;
import com.jcondotta.infrastructure.adapters.output.repository.entity.CreatedAtEmbeddable;
import com.jcondotta.infrastructure.adapters.output.repository.entity.MonetaryAmountEmbeddable;
import com.jcondotta.infrastructure.adapters.output.repository.enums.TransferStatusEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BankTransferMapper {

    public List<BankTransferEntity> toEntities(BankTransfer bankTransfer) {
        return bankTransfer.transferEntries().stream()
            .map(entry -> {
                var entity = new BankTransferEntity();

                entity.setBankTransferId(UUID.randomUUID());
                entity.setBankTransferGroupId(bankTransfer.bankTransferId().value());
                entity.setStatus(TransferStatusEntity.COMPLETED);

                entity.setTransferType(TransferTypeMapper.toEntity(bankTransfer.transferType()));
                entity.setMonetaryAmount(MonetaryAmountEmbeddable.of(entry.amount(), CurrencyMapper.toEntity(entry.currency())));
                entity.setMovementType(MovementTypeMapper.toEntity(entry.movementType()));
                entity.setReference(bankTransfer.reference());
                entity.setCreatedAt(CreatedAtEmbeddable.of(bankTransfer.createdAt()));

                if (entry instanceof InternalTransferEntry internalEntry) {
                    entity.setSenderAccountId(internalEntry.partySender().bankAccountId().value());
                    entity.setRecipientAccountId(internalEntry.partyRecipient().bankAccountId().value());
                }
//
//                if (entry instanceof SepaIncomingExternalTransferEntry incomingEntry) {
//                    entity.setSenderAccountId(null); // talvez venha de fora, ou deixe nulo
////                    entity.setTargetAccountId(incomingEntry.recipientIdentifier().bankAccountId().value());
////                    entity.setReference(incomingEntry.partySender().senderIban().toString());
//                }
//
//                if (entry instanceof SepaOutgoingExternalTransferEntry outgoingEntry) {
//                    entity.setSenderAccountId(outgoingEntry.partySender().bankAccountId().value());
//                    entity.setRecipientAccountId(null); // externo
////                    entity.setReference(outgoingEntry.recipientIdentifier().recipientIban().value());
//                }

                return entity;
            })
            .toList();
    }
}
