package com.jcondotta.transfer.application.usecase.process_internal_transfer.model;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.transfer.application.usecase.shared.CreateTransferCommand;

public interface CreateInternalTransferCommand extends CreateTransferCommand {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}