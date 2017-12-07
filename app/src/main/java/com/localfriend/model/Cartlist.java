package com.localfriend.model;

import java.io.Serializable;

/**
 * Created by SONI on 10/17/2017.
 */

public class Cartlist implements Serializable {
    private final static long serialVersionUID = 8144686534L;
    private String id;
    private String productid;
    private String productname;
    private String productimage;
    private String price;
    private String sellingprice;
    private String dealsprice;
    private int quantiy;
    private String maxOrder;
    private String availableUnit;
    private String varient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getDealsprice() {
        return dealsprice;
    }

    public void setDealsprice(String dealsprice) {
        this.dealsprice = dealsprice;
    }

    public int getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(int quantiy) {
        this.quantiy = quantiy;
    }

    public String getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(String maxOrder) {
        this.maxOrder = maxOrder;
    }

    public String getAvailableUnit() {
        return availableUnit;
    }

    public void setAvailableUnit(String availableUnit) {
        this.availableUnit = availableUnit;
    }

    public String getVarient() {
        return varient;
    }

    public void setVarient(String varient) {
        this.varient = varient;
    }
}
