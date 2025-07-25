package com.jcondotta.usecase.request_internal_transfer.model;

import com.jcondotta.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.usecase.shared.RequestTransferCommand;

public sealed interface RequestInternalTransferCommand
    extends RequestTransferCommand
    permits RequestInternalTransferFromAccountIdToAccountIdCommand, RequestInternalTransferFromAccountIdToIbanCommand {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}