package com.bakong.payment_gateway.response;

import kh.org.nbc.bakong_khqr.model.KHQRCurrency;
import lombok.Data;

@Data
public class BakongCheckMD5Response {
    private Integer responseCode;
    private String responseMessage;
    private String errorCode;
    private BakongCheckMD5Data data;


    @Data
    private static class BakongCheckMD5Data {
        private String hash;
        private String fromAccountId;
        private String toAccountId;
        private KHQRCurrency currency;
        private Double amount;
        private String description;
        private String trackingStatus;
        private String receiverBank;
        private String receiverBankAccount;
    }
}
