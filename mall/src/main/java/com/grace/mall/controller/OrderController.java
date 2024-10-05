package com.grace.mall.controller;

import com.grace.mall.dto.OrderRequest;
import com.grace.mall.dto.OrderRequestParam;
import com.grace.mall.model.Order;
import com.grace.mall.service.OrderService;
import com.grace.mall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 //分頁
                                                 @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        OrderRequestParam orderRequestParam = new OrderRequestParam();
        orderRequestParam.setUserId(userId);
        orderRequestParam.setLimit(limit);
        orderRequestParam.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderRequestParam);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(orderList.size());
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}
