package com.grace.mall.dao;

import com.grace.mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
    void updateStock(Integer productId, Integer quantity);
}
