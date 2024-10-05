package com.grace.mall.service;

import com.grace.mall.dto.OrderRequest;
import com.grace.mall.dto.OrderRequestParam;
import com.grace.mall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, OrderRequest orderRequest);
    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderRequestParam orderRequestParam);
}
