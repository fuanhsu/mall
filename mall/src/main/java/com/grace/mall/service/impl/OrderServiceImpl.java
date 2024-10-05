package com.grace.mall.service.impl;

import com.grace.mall.dao.OrderDao;
import com.grace.mall.dao.ProductDao;
import com.grace.mall.dao.UserDao;
import com.grace.mall.dto.BuyItem;
import com.grace.mall.dto.OrderRequest;
import com.grace.mall.dto.OrderRequestParam;
import com.grace.mall.model.Order;
import com.grace.mall.model.OrderItem;
import com.grace.mall.model.Product;
import com.grace.mall.model.User;
import com.grace.mall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {
       User user = userDao.getUserById(userId);
       if (user == null) {
           log.warn("使用者 {} 不存在", userId);
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }

        List<OrderItem> orderItemList = new ArrayList<>();
        int totalAmount = 0;
        for(BuyItem buyItem : orderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());
            if (product == null){
                log.warn("該商品 {} 不存在" , buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getProductId());
            if (product.getStock() < buyItem.getQuantity()){
                log.warn("該商品 {} 庫存不足 {} ， 購買數量 {} ", product.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            orderItem.setQuantity(buyItem.getQuantity());

            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());
            int amount = buyItem.getQuantity() * product.getPrice();
            orderItem.setAmount(amount);
            totalAmount += amount;


            orderItemList.add(orderItem);
        }
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItem(orderId, orderItemList);
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Override
    public List<Order> getOrders(OrderRequestParam orderRequestParam) {
        if (orderRequestParam.getUserId() != null) {
            User user = userDao.getUserById(orderRequestParam.getUserId());
            if (user == null) {
                log.warn("使用者 {} 不存在", orderRequestParam.getUserId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        List<Order> orderList = orderDao.getOrders(orderRequestParam);
        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }
}
