package com.example.demo.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private String status; // PENDING, SUCCESS, FAILED
    private String razorpayPaymentId;
    private Double amount;
}
