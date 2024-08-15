package com.bakong.payment_gateway.enums;

public enum BankBakongAccount {
    ABA("@abaa"),
    ACLEDA("@aclb"),
    WING("@wing"),
    CANADIA("@cadi"),
    APD("@adeo"),
    AMK("@amkb"),
    AMRET("@amrt"),
    TRUEMONEY("@trmc"),
    VATTANAC("@vbl"),
    UNION("@ucbp"),
    LYHOUR("@lypp");

    private final String value;

    BankBakongAccount(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
