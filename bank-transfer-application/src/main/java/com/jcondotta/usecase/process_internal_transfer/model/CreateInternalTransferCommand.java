package com.jcondotta.usecase.process_internal_transfer.model;

import com.jcondotta.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.usecase.shared.CreateTransferCommand;

public interface CreateInternalTransferCommand extends CreateTransferCommand {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}