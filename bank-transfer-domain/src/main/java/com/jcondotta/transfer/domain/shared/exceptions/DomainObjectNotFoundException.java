package com.jcondotta.transfer.domain.shared.exceptions;

import java.io.Serializable;

public class DomainObjectNotFoundException extends RuntimeException {

    private final Serializable[] identifiers;

    public DomainObjectNotFoundException(String message, Serializable... identifiers) {
        super(message);
        this.identifiers = identifiers;
    }

    public DomainObjectNotFoundException(String message, Throwable cause, Serializable... identifiers) {
        super(message, cause);
        this.identifiers = identifiers;
    }

    public Serializable[] getIdentifiers() {
        return identifiers;
    }
}