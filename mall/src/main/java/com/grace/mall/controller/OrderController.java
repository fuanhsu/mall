package com.grace.mall.controller;

import com.grace.mall.dto.OrderRequest;
import com.grace.mall.model.Order;
import com.grace.mall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/order")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId, @RequestBody @Valid OrderRequest orderRequest) {
       Integer orderId = orderService.createOrder(userId, orderRequest);
       Order order = orderService.getOrderById(orderId);
       return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
