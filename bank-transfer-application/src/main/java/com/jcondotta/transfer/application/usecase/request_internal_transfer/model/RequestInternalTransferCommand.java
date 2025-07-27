package com.jcondotta.transfer.application.usecase.request_internal_transfer.model;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.transfer.application.usecase.shared.RequestTransferCommand;

public sealed interface RequestInternalTransferCommand
    extends RequestTransferCommand
    permits RequestInternalTransferFromAccountIdToAccountIdCommand, RequestInternalTransferFromAccountIdToIbanCommand {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}