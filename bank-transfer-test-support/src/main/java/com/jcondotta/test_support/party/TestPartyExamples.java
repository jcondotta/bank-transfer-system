package com.jcondotta.test_support.party;

import com.jcondotta.test_support.iban.TestIbanExamples;

public enum TestPartyExamples {

    JEFFERSON("Jefferson Condotta", TestIbanExamples.VALID_ITALY),
    PATRIZIO("Patrizio Condotta", TestIbanExamples.VALID_SPAIN),
    VIRGINIO("Virginio Condotta", TestIbanExamples.VALID_GERMANY);

    private final String recipientName;
    private final String recipientIban;

    TestPartyExamples(String name, String iban) {
        this.recipientName = name;
        this.recipientIban = iban;
    }

    public String getName() {
        return recipientName;
    }

    public String getIban() {
        return recipientIban;
    }
}