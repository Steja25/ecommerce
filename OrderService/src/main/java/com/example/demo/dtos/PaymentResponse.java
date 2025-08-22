package com.example.demo.dtos;

import lombok.Data;
@Data
public class PaymentResponse {
    private Long id;                  // ✅ Add this to match PaymentService
    private String status;            // PENDING, SUCCESS, FAILED
    private String razorpayPaymentId;
    private Double amount;            // ✅ Use Double, not int
}
