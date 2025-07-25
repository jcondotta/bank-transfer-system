package com.jcondotta.usecase.shared;


import com.jcondotta.banktransfer.valueobjects.party.PartyRecipient;
import com.jcondotta.banktransfer.valueobjects.party.PartySender;
import com.jcondotta.monetary_movement.value_objects.MonetaryAmount;

import java.time.ZonedDateTime;

public interface CreateTransferCommand {
    PartySender partySender();
    PartyRecipient partyRecipient();
    MonetaryAmount monetaryAmount();
    String reference();
    ZonedDateTime createdAt();
}