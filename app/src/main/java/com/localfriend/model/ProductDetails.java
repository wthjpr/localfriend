package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class ProductDetails {
    private String id;
    private String unit;
    private String uType;
    private String uDescription;
    private String price;
    private String sellingPrice;
    private String dealsPrice;
    private String maxOrder;
    private String availableUnit;
    private String vProductFileList;
    private String name;
    private List<String> pGalleryFileList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getpGalleryFileList() {
        return pGalleryFileList;
    }

    public void setpGalleryFileList(List<String> pGalleryFileList) {
        this.pGalleryFileList = pGalleryFileList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuDescription() {
        return uDescription;
    }

    public void setuDescription(String uDescription) {
        this.uDescription = uDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDealsPrice() {
        return dealsPrice;
    }

    public void setDealsPrice(String dealsPrice) {
        this.dealsPrice = dealsPrice;
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

    public String getvProductFileList() {
        return vProductFileList;
    }

    public void setvProductFileList(String vProductFileList) {
        this.vProductFileList = vProductFileList;
    }
}
