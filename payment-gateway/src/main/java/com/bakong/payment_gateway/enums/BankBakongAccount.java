package com.bakong.payment_gateway.enums;

public enum BankBakongAccount {
    ABA("abaakhppxxx@abaa"),
    ACLEDA("aclbkhppxxx@aclb");

    private final String value;

    BankBakongAccount(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
