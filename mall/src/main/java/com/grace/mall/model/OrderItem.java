package com.grace.mall.model;

public class OrderItem {
    private Integer orderItemId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
    private Integer orderId;

    private String productName;
    private String imageUrl;

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
