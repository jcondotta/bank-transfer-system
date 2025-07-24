package com.jcondotta.banktransfer.valueobjects.transfer_entry;

import com.jcondotta.banktransfer.valueobjects.InternalPartyRecipient;
import com.jcondotta.banktransfer.valueobjects.InternalPartySender;

public interface InternalTransferEntry extends TransferEntry {

    @Override
    InternalPartySender partySender();

    @Override
    InternalPartyRecipient partyRecipient();
}
