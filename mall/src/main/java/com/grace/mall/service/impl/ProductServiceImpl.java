package com.grace.mall.service.impl;

import com.grace.mall.dao.ProductDao;
import com.grace.mall.dto.ProductRequest;
import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.ProductUpdateRequest;
import com.grace.mall.model.Product;
import com.grace.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    public List<Product> getProducts(ProductRequestParam productRequestParam){
        return productDao.getProducts(productRequestParam);
    }

    @Override
    public Product getProductById(Integer productId) {
        if (productDao.getProductById(productId) == null) {
            log.warn("商品 {} 不存在" , productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    public Integer countProduct(ProductRequestParam productRequestParam){
        return productDao.countProduct(productRequestParam);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = productDao.getProductById(productId);
        if (product == null) {
            log.warn("商品 {} 不存在" , productId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else{
            productDao.deleteProduct(productId);
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productDao.getProductById(productId);
        if (product == null) {
            log.warn("商品 {} 不存在", productId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else{
            productDao.updateProduct(productId, productUpdateRequest);
            return productDao.getProductById(productId);
        }

    }
}
