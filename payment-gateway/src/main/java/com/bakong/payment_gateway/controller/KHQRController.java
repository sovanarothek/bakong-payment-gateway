package com.bakong.payment_gateway.controller;

import com.bakong.payment_gateway.request.KHQRGenerateRequest;
import com.bakong.payment_gateway.response.ApiResponse;
import com.bakong.payment_gateway.scheduling.BakongTransactionStatusCron;
import com.bakong.payment_gateway.service.KHQRGenerateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "KHQR Management", description = "KHQR management APIs")
@RestController
@RequestMapping("khqr")
public class KHQRController {

    @Resource
    private KHQRGenerateService khqrGenerateService;

    @Resource
    private BakongTransactionStatusCron bakongTransactionStatusCron;

    @PostMapping("generate")
    public ResponseEntity<ApiResponse> generateKHQR(@RequestBody KHQRGenerateRequest request) {

        ApiResponse response = khqrGenerateService.generateKHQR(request);

        return ResponseEntity.status(HttpStatus.OK).
                body(response);
    }

    @GetMapping("get-list")
    public ResponseEntity<ApiResponse> getListTransaction(@RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(defaultValue = "0") Integer pageNumber) {
        ApiResponse response = khqrGenerateService.findAllKHQRGenerateTransaction(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).
                body(response);
    }

    @GetMapping("check-transaction/{md5}")
    public ResponseEntity<ApiResponse> checkTransaction(@PathVariable String md5 ) throws IOException {
        bakongTransactionStatusCron.checkBakongTransactionByMD5(md5);
        return ResponseEntity.status(HttpStatus.OK).
                body(ApiResponse.success());
    }
}
