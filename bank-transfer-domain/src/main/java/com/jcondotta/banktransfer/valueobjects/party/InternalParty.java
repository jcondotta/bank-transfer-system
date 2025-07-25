package com.jcondotta.banktransfer.valueobjects.party;

import com.jcondotta.banktransfer.valueobjects.party.identifier.InternalPartyIdentifier;

public sealed interface InternalParty permits InternalPartySender, InternalPartyRecipient {

    InternalPartyIdentifier identifier();
}