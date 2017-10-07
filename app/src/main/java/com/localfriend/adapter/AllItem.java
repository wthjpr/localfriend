package com.localfriend.adapter;

/**
 * Created by DJ-PC on 5/6/2017.
 */

public class AllItem {
    private String ItemCost;
    private String ItemName;
    private Integer ItemImage;
    public AllItem( String ItemCost, Integer ItemImage, String ItemName) {
        this.ItemCost = ItemCost;
        this.ItemImage = ItemImage;
        this.ItemName = ItemName;

    }

    public String getItemCost() {
        return ItemCost;
    }


    public String getItemName() {
        return ItemName;
    }



    public Integer getItemImage() {
        return ItemImage;
    }





}
