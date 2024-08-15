package com.bakong.payment_gateway.request;

import com.bakong.payment_gateway.enums.BankBakongAccount;
import kh.org.nbc.bakong_khqr.model.KHQRCurrency;
import lombok.Data;

@Data
public class KHQRGenerateRequest {
    private String bakongAccountId;
    private String bakongAccountName;
    private Double amount;
    private KHQRCurrency currencyCode;
    private BankBakongAccount bank;
}
