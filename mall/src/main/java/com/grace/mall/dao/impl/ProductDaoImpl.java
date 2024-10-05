package com.grace.mall.dao.impl;


import com.grace.mall.dao.ProductDao;
import com.grace.mall.model.Product;
import com.grace.mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, stock, price, category,description, image_url, created_date, last_modified_date FROM product WHERE product_id = :productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList.size() > 0 ? productList.get(0) : null;
    }

    @Override
    public void updateStock(Integer productId, Integer quantity) {
        String sql ="UPDATE product SET stock = :stock WHERE product_id = :productId ";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("productId", productId);
        mapSqlParameterSource.addValue("stock", quantity);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }

}
