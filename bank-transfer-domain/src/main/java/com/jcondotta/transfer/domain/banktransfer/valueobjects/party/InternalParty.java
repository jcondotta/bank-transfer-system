package com.jcondotta.transfer.domain.banktransfer.valueobjects.party;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.identifier.InternalPartyIdentifier;

public sealed interface InternalParty permits InternalPartySender, InternalPartyRecipient {

    InternalPartyIdentifier identifier();
}