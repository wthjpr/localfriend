package com.localfriend.model;

/**
 * Created by SONI on 10/26/2017.
 */

public class HistoryList {

    private String orderDetailId;
    private String orderId;
    private String productId;
    private String productName;
    private String productImage;
    private String orderUnitPrice;
    private String orderTotalPrice;
    private String productQuantity;
    private String varient;
    private String DetailsDescription;
    private String ExtraNote;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getOrderUnitPrice() {
        return orderUnitPrice;
    }

    public void setOrderUnitPrice(String orderUnitPrice) {
        this.orderUnitPrice = orderUnitPrice;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getVarient() {
        return varient;
    }

    public void setVarient(String varient) {
        this.varient = varient;
    }

    public String getDetailsDescription() {
        return DetailsDescription;
    }

    public void setDetailsDescription(String detailsDescription) {
        DetailsDescription = detailsDescription;
    }

    public String getExtraNote() {
        return ExtraNote;
    }

    public void setExtraNote(String extraNote) {
        ExtraNote = extraNote;
    }
}
