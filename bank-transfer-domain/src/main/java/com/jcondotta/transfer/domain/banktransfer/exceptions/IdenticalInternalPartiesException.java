package com.jcondotta.transfer.domain.banktransfer.exceptions;

import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartyRecipient;
import com.jcondotta.transfer.domain.banktransfer.valueobjects.party.InternalPartySender;
import com.jcondotta.transfer.domain.shared.exceptions.BusinessRuleException;

import java.util.Map;

public class IdenticalInternalPartiesException extends BusinessRuleException {

    public static final String MESSAGE_TEMPLATE = "internal.transfer.identicalParties";
    public static final String ERROR_TYPE = "/errors/internal-transfer-identical-parties";

    private final InternalPartySender internalPartySender;
    private final InternalPartyRecipient internalPartyRecipient;

    public IdenticalInternalPartiesException(InternalPartySender internalPartySender, InternalPartyRecipient internalPartyRecipient) {
        super(MESSAGE_TEMPLATE);
        this.internalPartySender = internalPartySender;
        this.internalPartyRecipient = internalPartyRecipient;
    }

    @Override
    public String getType() {
        return ERROR_TYPE;
    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(
            "partySender", internalPartySender.toString(),
            "partyRecipient", internalPartyRecipient.toString()
        );
    }
}
