package com.grace.mall.dao.impl;

import com.grace.mall.dao.OrderDao;
import com.grace.mall.dto.OrderRequest;
import com.grace.mall.model.Order;
import com.grace.mall.model.OrderItem;
import com.grace.mall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate) ";
        Date now = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
     String sql = "INSERT INTO order_item(order_id, order_item_id, product_id, quantity, amount) VALUES (:orderId, :orderItemId, :productId, :quantity, :amount) ";
     MapSqlParameterSource[] mapSqlParameterSource = new MapSqlParameterSource[orderItemList.size()];
     for(int i = 0; i < orderItemList.size(); i++){
         mapSqlParameterSource[i] = new MapSqlParameterSource();
         mapSqlParameterSource[i].addValue("orderId", orderId);
         mapSqlParameterSource[i].addValue("orderItemId", orderItemList.get(i).getOrderItemId());
         mapSqlParameterSource[i].addValue("productId", orderItemList.get(i).getProductId());
         mapSqlParameterSource[i].addValue("quantity", orderItemList.get(i).getQuantity());
         mapSqlParameterSource[i].addValue("amount", orderItemList.get(i).getAmount());
     }
       namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSource);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE order_id = :orderId";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new OrderRowMapper() );

        return orderList.size() > 0 ? orderList.get(0) : null;
    }
}
