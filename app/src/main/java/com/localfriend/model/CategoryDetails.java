package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class CategoryDetails {
    private String ID;
    private String Name;
    private String Description;
    private String Image;
    private String ThumbImage;
    private String ParentID;
    private String ParentCategory;
    private String IsActive;
    private String Creation;
    private String Modified;
    private List<StoreList> storelist;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getThumbImage() {
        return ThumbImage;
    }

    public void setThumbImage(String thumbImage) {
        ThumbImage = thumbImage;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    public String getParentCategory() {
        return ParentCategory;
    }

    public void setParentCategory(String parentCategory) {
        ParentCategory = parentCategory;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getCreation() {
        return Creation;
    }

    public void setCreation(String creation) {
        Creation = creation;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public List<StoreList> getStorelist() {
        return storelist;
    }

    public void setStorelist(List<StoreList> storelist) {
        this.storelist = storelist;
    }
}
