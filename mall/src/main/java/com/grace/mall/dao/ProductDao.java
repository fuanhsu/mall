package com.grace.mall.dao;

import com.grace.mall.dto.ProductRequest;
import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.ProductUpdateRequest;
import com.grace.mall.model.Product;

import java.util.List;

public interface ProductDao {

    Product getProductById(Integer productId);
    void updateStock(Integer productId, Integer quantity);
    List<Product> getProducts(ProductRequestParam productRequestParam);
    Integer countProduct(ProductRequestParam productRequestParam);
    Integer createProduct(ProductRequest productRequest);
    void deleteProduct(Integer productId);
    void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest);
}
