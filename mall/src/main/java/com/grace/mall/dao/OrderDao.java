package com.grace.mall.dao;

import com.grace.mall.dto.OrderRequest;
import com.grace.mall.model.Order;
import com.grace.mall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
    Order getOrderById(Integer orderId);
}
