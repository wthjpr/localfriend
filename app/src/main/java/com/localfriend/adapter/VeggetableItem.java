package com.localfriend.adapter;

/**
 * Created by DJ-PC on 5/6/2017.
 */

public class VeggetableItem {

    private String VegCost;

    public String getVegCost() {
        return VegCost;
    }

    public String getVegOffer() {
        return VegOffer;
    }

    public Integer getVegImage() {
        return VegImage;
    }

    public String getVegName() {
        return VegName;
    }

    private String VegOffer;
    private Integer VegImage;
    private String VegName;
    public VeggetableItem(String VegCost, String VegOffer, Integer VegImage, String VegName) {
        this.VegCost = VegCost;
        this.VegOffer = VegOffer;
        this.VegImage = VegImage;
        this.VegName = VegName;

    }






}
