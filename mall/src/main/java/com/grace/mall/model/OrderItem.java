package com.grace.mall.model;

public class OrderItem {
    private Integer orderItemId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;
    private Integer orderId;

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
}
