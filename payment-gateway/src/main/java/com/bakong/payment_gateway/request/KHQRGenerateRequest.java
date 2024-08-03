package com.bakong.payment_gateway.request;

import com.bakong.payment_gateway.enums.BankBakongAccount;
import kh.org.nbc.bakong_khqr.model.KHQRCurrency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class KHQRGenerateRequest {
    private String bankAccountNumber;
    private String bankAccountName;
    private Double amount;
    private KHQRCurrency currencyCode;
    private BankBakongAccount bank;
}
