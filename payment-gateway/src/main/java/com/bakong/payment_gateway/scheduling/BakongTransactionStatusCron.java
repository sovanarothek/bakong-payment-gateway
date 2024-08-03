package com.bakong.payment_gateway.scheduling;

import com.bakong.payment_gateway.dto.entity.KHQRGenerateEntity;
import com.bakong.payment_gateway.dto.repository.KHQRGenerateRepository;
import com.bakong.payment_gateway.enums.TransactionStatus;
import com.bakong.payment_gateway.request.BakongCheckMD5Request;
import com.bakong.payment_gateway.response.BakongCheckMD5Response;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BakongTransactionStatusCron {

    @Value("${my-config.bakong.api}")
    private String bakongApiBase;

    @Value("${my-config.bakong.token}")
    private String bakongToken;

    @Value("${my-config.bakong.origin}")
    private String bakongOrigin;

    @Resource
    private KHQRGenerateRepository khqrGenerateRepository;

    boolean flag = true;

    @Scheduled(cron = "* * * * * *")
    private void checkingAllTransactionStatus() throws IOException {
        List<KHQRGenerateEntity> listPendingTransaction = khqrGenerateRepository.findAllByTransactionStatus(TransactionStatus.PENDING);


        if (flag) {
            flag = false;
            for (KHQRGenerateEntity data : listPendingTransaction) {
                BakongCheckMD5Response response = checkBakongTransactionByMD5(data.getMd5());
                if (response != null) {
                    if (response.getResponseCode() == 0 && response.getResponseMessage().equalsIgnoreCase("success")) {
                        data.setTransactionStatus(TransactionStatus.SUCCESS);
                        data.setUpdatedAt(new Date());
                        khqrGenerateRepository.save(data);
                    }
                }
            }

        }
    }

    public BakongCheckMD5Response checkBakongTransactionByMD5(String md5) throws IOException {
        String apiUrl = bakongApiBase + "/check_transaction_by_md5";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = MediaType.parse("application/json");
        BakongCheckMD5Request requestBody = BakongCheckMD5Request.builder()
                .md5(md5)
                .build();

        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(requestBody));
        Request request = new Request.Builder()
                .url(apiUrl)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + bakongToken)
                .addHeader("Content-type", "application/json")
                .addHeader("Origin", bakongOrigin)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            if (response.body() != null) {
                BakongCheckMD5Response res = new Gson().fromJson(response.body().string(), BakongCheckMD5Response.class);
                log.info("BakongCheckMD5Response : {}", res);
                return res;
            }
        }
        return null;
    }
}