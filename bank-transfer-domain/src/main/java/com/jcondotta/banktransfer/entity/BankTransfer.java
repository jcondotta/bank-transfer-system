package com.jcondotta.banktransfer.entity;

import com.jcondotta.bank_account.valueobject.BankAccountId;
import com.jcondotta.banktransfer.enums.TransferType;
import com.jcondotta.banktransfer.valueobjects.BankTransferId;
import com.jcondotta.banktransfer.valueobjects.transfer_entry.InternalTransferEntry;
import com.jcondotta.banktransfer.valueobjects.transfer_entry.TransferEntry;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.List;

public record BankTransfer(BankTransferId bankTransferId, List<TransferEntry> transferEntries, TransferType transferType, String reference, ZonedDateTime createdAt){

    public static BankTransfer initiateInternalTransfer(BankAccountId senderAccountId, BankAccountId recipientAccountId, MonetaryAmount amount, String reference, Clock clock) {
        var entryDebit = InternalTransferEntry.ofDebit(senderAccountId, recipientAccountId, amount);
        var entryCredit = InternalTransferEntry.ofCredit(senderAccountId, recipientAccountId, amount);

        return new BankTransfer(BankTransferId.newId(), List.of(entryDebit, entryCredit), TransferType.INTERNAL, reference, ZonedDateTime.now(clock));
    }
}