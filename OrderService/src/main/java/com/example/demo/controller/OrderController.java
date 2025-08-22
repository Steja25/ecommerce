package com.example.demo.controller;

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
import com.example.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

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
@Autowired
private OrderService service;

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
              return   service.placeOrder(order);
    	
       

    }



    @GetMapping
    public java.util.List<Order> getAllOrders() {
        return repo.findAll();
    }
}
