package com.example.demo.feign;

import com.example.demo.dtos.PaymentRequest;
import com.example.demo.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentservice")
public interface PaymentClient {

    @PostMapping("/payments")  // Match the controller path in Payment Service
    PaymentResponse createPayment(@RequestBody PaymentRequest request);
}
