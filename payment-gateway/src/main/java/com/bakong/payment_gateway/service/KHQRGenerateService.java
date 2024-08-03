package com.bakong.payment_gateway.service;

import com.bakong.payment_gateway.dto.entity.KHQRGenerateEntity;
import com.bakong.payment_gateway.dto.repository.KHQRGenerateRepository;
import com.bakong.payment_gateway.enums.TransactionStatus;
import com.bakong.payment_gateway.request.KHQRGenerateRequest;
import com.bakong.payment_gateway.response.ApiResponse;
import jakarta.annotation.Resource;
import kh.org.nbc.bakong_khqr.BakongKHQR;
import kh.org.nbc.bakong_khqr.model.CRCValidation;
import kh.org.nbc.bakong_khqr.model.IndividualInfo;
import kh.org.nbc.bakong_khqr.model.KHQRData;
import kh.org.nbc.bakong_khqr.model.KHQRResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KHQRGenerateService {

    @Resource
    private KHQRGenerateRepository khqrGenerateRepository;


    public ApiResponse generateKHQR(KHQRGenerateRequest request) {

        Double finalAmount = request.getAmount() > 0 ? request.getAmount() : null;

        IndividualInfo individualInfo = new IndividualInfo();
        individualInfo.setBakongAccountId(request.getBank().getValue());
        individualInfo.setAccountInformation(request.getBankAccountNumber());
        individualInfo.setAcquiringBank("ABA Bank");
        individualInfo.setAmount(finalAmount);
        individualInfo.setCurrency(request.getCurrencyCode());
        individualInfo.setMerchantName(request.getBankAccountName());
        individualInfo.setMerchantCity("Phnom Penh");


        KHQRResponse<KHQRData> response = BakongKHQR.generateIndividual(individualInfo);

        if (response.getKHQRStatus().getCode() == 0) {
            if (validateValidKHQR(response.getData().getQr())) {
                KHQRGenerateEntity newKHQRGenData = KHQRGenerateEntity.builder()
                        .accountName(request.getBankAccountName())
                        .accountNumber(request.getBankAccountNumber())
                        .bank(request.getBank().name())
                        .md5(response.getData().getMd5())
                        .encodeQr(response.getData().getQr())
                        .amount(finalAmount)
                        .currency(request.getCurrencyCode())
                        .transactionStatus(TransactionStatus.PENDING)
                        .createdAt(new Date())
                        .build();

                khqrGenerateRepository.save(newKHQRGenData);

                return ApiResponse.success(response.getData().getQr());
            }

        }
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"error khqr generated");
    }

    public ApiResponse findAllKHQRGenerateTransaction(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<KHQRGenerateEntity> data = khqrGenerateRepository.findAll(pageable);
        return ApiResponse.successPage(data.getContent(), data.getTotalElements());
    }

    private boolean validateValidKHQR(String encodeQR) {
        KHQRResponse<CRCValidation> response = BakongKHQR.verify(encodeQR);
        return response.getData().isValid();
    }
}