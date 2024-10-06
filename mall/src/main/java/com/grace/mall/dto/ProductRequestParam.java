package com.grace.mall.dto;

import com.grace.mall.constant.ProductCategory;

public class ProductRequestParam {

    private ProductCategory category;
    private String search;
    private Integer limit;
    private Integer offset;
    private String sort;
    private String orderBy;

    public String getSearch() {
        return search;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String getSort() {
        return sort;
    }



    public void setSearch(String search) {
        this.search = search;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
