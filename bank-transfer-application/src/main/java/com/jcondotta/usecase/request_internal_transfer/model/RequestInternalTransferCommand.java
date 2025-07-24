package com.jcondotta.usecase.request_internal_transfer.model;

import com.jcondotta.banktransfer.valueobjects.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.InternalPartySender;
import com.jcondotta.usecase.shared.RequestTransferCommand;

public interface RequestInternalTransferCommand extends RequestTransferCommand {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}