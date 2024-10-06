package com.grace.mall.service;

import com.grace.mall.dto.ProductRequest;
import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.ProductUpdateRequest;
import com.grace.mall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductRequestParam productRequestParam);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    Integer countProduct(ProductRequestParam productRequestParam);
    void deleteProduct(Integer productId);
    Product updateProduct(Integer productId, ProductUpdateRequest productRequest);

}
