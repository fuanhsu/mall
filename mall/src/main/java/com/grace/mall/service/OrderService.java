package com.grace.mall.service;

import com.grace.mall.dto.OrderRequest;
import com.grace.mall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, OrderRequest orderRequest);
    Order getOrderById(Integer orderId);
}
