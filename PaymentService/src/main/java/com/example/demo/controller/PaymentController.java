package com.example.demo.controller;

import com.example.demo.dto.PaymentRequest;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        try {
            return paymentService.processPayment(request);
        } catch (Exception e) {
            PaymentResponse response = new PaymentResponse();
            response.setStatus("FAILED");
            response.setRazorpayPaymentId(null);
            response.setAmount(request.getAmount());
            return response;
        }
    }
}
