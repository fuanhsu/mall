package com.grace.mall.dao.impl;


import com.grace.mall.dao.ProductDao;
import com.grace.mall.dto.ProductRequest;
import com.grace.mall.dto.ProductRequestParam;
import com.grace.mall.dto.ProductUpdateRequest;
import com.grace.mall.model.Product;
import com.grace.mall.rowmapper.ProductRowMapper;
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

    @Override
    public List<Product> getProducts(ProductRequestParam productRequestParam) {
        String sql = "SELECT product_id, product_name, image_url, category, stock, price, description, created_date, last_modified_date FROM product " +
                "WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        sql = addFilterSql(sql, map, productRequestParam);

        if (productRequestParam.getOrderBy() != null){
            sql += "ORDER BY " + productRequestParam.getOrderBy();
            map.put("orderBy", productRequestParam.getOrderBy());
        }
        if (productRequestParam.getSort() != null ) {
            sql +=  " " + productRequestParam.getSort();
            map.put("sort", productRequestParam.getSort());
        }
        sql += " LIMIT :limit OFFSET :offset ";

        map.put("limit", productRequestParam.getLimit());
        map.put("offset", productRequestParam.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    private String addFilterSql(String sql, Map<String, Object> map, ProductRequestParam productRequestParam) {
        if (productRequestParam.getCategory() != null) {
            sql += "AND category = :category ";
            map.put("category", productRequestParam.getCategory().name());
        }
        if (productRequestParam.getSearch() != null) {
            sql += "AND product_name LIKE :search ";
            map.put("search", "%" + productRequestParam.getSearch() + "%");
        }
        return sql;
    }

    @Override
    public Integer countProduct(ProductRequestParam productRequestParam) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();
        sql = addFilterSql(sql, map, productRequestParam);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate) ";
        Date now = new Date();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("productName", productRequest.getProductName());
        mapSqlParameterSource.addValue("category", productRequest.getCategory().name());
        mapSqlParameterSource.addValue("imageUrl", productRequest.getImageUrl());
        mapSqlParameterSource.addValue("price", productRequest.getPrice());
        mapSqlParameterSource.addValue("stock", productRequest.getStock());
        mapSqlParameterSource.addValue("description", productRequest.getDescription());
        mapSqlParameterSource.addValue("createdDate", now);
        mapSqlParameterSource.addValue("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId ";
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void updateProduct(Integer productId, ProductUpdateRequest productUpdateRequest) {
        String sql = "UPDATE product SET ";
        Map<String, Object> map = new HashMap<>();
        if (productUpdateRequest.getCategory() != null) {
            sql += "category = :category ";
            map.put("category", productUpdateRequest.getCategory().name() + ", ");
        }
        if (productUpdateRequest.getProductName() != null) {
            sql += "product_name = :productName, ";
            map.put("productName", productUpdateRequest.getProductName() + ", ");
        }
        if (productUpdateRequest.getImageUrl() != null) {
            sql += "image_url = :imageUrl, ";
            map.put("imageUrl", productUpdateRequest.getImageUrl() + ", ");
        }
        if (productUpdateRequest.getPrice() != null) {
            sql += "price = :price, ";
            map.put("price", productUpdateRequest.getPrice() + ", ");
        }
        if (productUpdateRequest.getStock() != null) {
            sql += "stock = :stock ";
            map.put("stock", productUpdateRequest.getStock() + ", ");
        }
        if (productUpdateRequest.getDescription() != null) {
            sql += "description = :description ";
            map.put("description", productUpdateRequest.getDescription() + ", ");
        }
        sql += " last_modified_date = :lastModifiedDate ";
        map.put("lastModifiedDate", new Date());
        sql += " WHERE product_id = :productId ";
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

}
