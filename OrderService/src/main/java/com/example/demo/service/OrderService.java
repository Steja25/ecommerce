package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dtos.PaymentRequest;
import com.example.demo.dtos.PaymentResponse;
import com.example.demo.dtos.Product;
import com.example.demo.dtos.User;
import com.example.demo.entity.Order;
import com.example.demo.feign.InventoryClient;
import com.example.demo.feign.PaymentClient;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.UserClient;
import com.example.demo.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {


    @Autowired
    private OrderRepository repo;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private PaymentClient paymentClient;


    
    public Order placeOrder(@RequestBody Order order) {

        // 1️⃣ Validate user
        User user = userClient.getUserById(order.getUserId());
        if (user == null) {
            order.setOrderStatus("FAILED");
            order.setPaymentStatus("FAILED");
            throw new RuntimeException("User not found!");
        }

        // 2️⃣ Validate product
        Product product = productClient.getProductById(order.getProductId());
        if (product == null) {
            order.setOrderStatus("FAILED");
            order.setPaymentStatus("FAILED");
            throw new RuntimeException("Product not found!");
        }

        // 3️⃣ Check stock
        String stockStatus = inventoryClient.checkStock(order.getProductId(), order.getQuantity());
        if (!"In Stock".equalsIgnoreCase(stockStatus)) {
            order.setOrderStatus("FAILED");
            order.setPaymentStatus("FAILED");
            return repo.save(order);
        }

        // 4️⃣ Save order first with PENDING statuses
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("PENDING");
        Order savedOrder = repo.save(order);

        Double totalAmount = product.getPrice() * savedOrder.getQuantity();

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setUserId(savedOrder.getUserId());
        paymentRequest.setAmount(totalAmount);

        PaymentResponse paymentResponse = paymentClient.createPayment(paymentRequest);

        // ✅ Check for SUCCESS instead of PENDING
//        if (paymentResponse == null || !"SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())) {
//            savedOrder.setOrderStatus("FAILED");
//            savedOrder.setPaymentStatus("FAILED");
//            return repo.save(savedOrder);
//        }
        if (paymentResponse == null || !"SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())) {
            savedOrder.setOrderStatus("FAILED");
            savedOrder.setPaymentStatus("FAILED");
            return repo.save(savedOrder);
        }


        // 6️⃣ Update order with payment info
        savedOrder.setPaymentId(paymentResponse.getRazorpayPaymentId());
        savedOrder.setPaymentStatus(paymentResponse.getStatus());

        // 7️⃣ Reduce stock
        inventoryClient.reduceStock(savedOrder.getProductId(), savedOrder.getQuantity());

        // 8️⃣ Confirm order
        savedOrder.setOrderStatus("CONFIRMED");

        // 9️⃣ Save and return final order
        return repo.save(savedOrder);

    }



}
