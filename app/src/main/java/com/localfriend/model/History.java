package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/25/2017.
 */

public class History {
    private String Order_Id;
    private String Order_TotalProduct;
    private String Order_SubTotal;
    private String Order_ShippingPrice;
    private String Order_TotalTaxPrice;
    private String Order_Discount;
    private String Order_TotalPrice;
    private String Order_date;
    private String Order_ShippingID;
    private String Order_Status;
    private List<HistoryList> orderItemlist;

    public List<HistoryList> getOrderItemlist() {
        return orderItemlist;
    }

    public void setOrderItemlist(List<HistoryList> orderItemlist) {
        this.orderItemlist = orderItemlist;
    }

    public String getOrder_Id() {
        return Order_Id;
    }

    public void setOrder_Id(String order_Id) {
        Order_Id = order_Id;
    }

    public String getOrder_TotalProduct() {
        return Order_TotalProduct;
    }

    public void setOrder_TotalProduct(String order_TotalProduct) {
        Order_TotalProduct = order_TotalProduct;
    }

    public String getOrder_SubTotal() {
        return Order_SubTotal;
    }

    public void setOrder_SubTotal(String order_SubTotal) {
        Order_SubTotal = order_SubTotal;
    }

    public String getOrder_ShippingPrice() {
        return Order_ShippingPrice;
    }

    public void setOrder_ShippingPrice(String order_ShippingPrice) {
        Order_ShippingPrice = order_ShippingPrice;
    }

    public String getOrder_TotalTaxPrice() {
        return Order_TotalTaxPrice;
    }

    public void setOrder_TotalTaxPrice(String order_TotalTaxPrice) {
        Order_TotalTaxPrice = order_TotalTaxPrice;
    }

    public String getOrder_Discount() {
        return Order_Discount;
    }

    public void setOrder_Discount(String order_Discount) {
        Order_Discount = order_Discount;
    }

    public String getOrder_TotalPrice() {
        return Order_TotalPrice;
    }

    public void setOrder_TotalPrice(String order_TotalPrice) {
        Order_TotalPrice = order_TotalPrice;
    }

    public String getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(String order_date) {
        Order_date = order_date;
    }

    public String getOrder_ShippingID() {
        return Order_ShippingID;
    }

    public void setOrder_ShippingID(String order_ShippingID) {
        Order_ShippingID = order_ShippingID;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String order_Status) {
        Order_Status = order_Status;
    }
}
