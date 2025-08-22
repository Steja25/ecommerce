package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orderservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
    private Integer quantity;

    private String orderStatus;   // CONFIRMED / FAILED
    private String paymentStatus; // PENDING / SUCCESS / FAILED
    private String paymentId;     // Razorpay orderId
}
