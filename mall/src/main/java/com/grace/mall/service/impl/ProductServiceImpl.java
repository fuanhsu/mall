package com.grace.mall.service.impl;

import com.grace.mall.dao.ProductDao;
import com.grace.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
}
