package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class Product {
    private String categoryID;
    private String pId;
    private String pName;
    private String pTitle;
    private String pPrice;
    private String pDescription;
    private List<ProductDetails> pDetailsList;
    private List<String> pGalleryFileList;

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public List<ProductDetails> getpDetailsList() {
        return pDetailsList;
    }

    public void setpDetailsList(List<ProductDetails> pDetailsList) {
        this.pDetailsList = pDetailsList;
    }

    public List<String> getpGalleryFileList() {
        return pGalleryFileList;
    }

    public void setpGalleryFileList(List<String> pGalleryFileList) {
        this.pGalleryFileList = pGalleryFileList;
    }
}
