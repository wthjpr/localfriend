package com.localfriend.adapter;

/**
 * Created by DJ-PC on 5/6/2017.
 */

public class DummyBreakfastItem {
    public Integer getBreakFastImage() {
        return BreakFastImage;
    }

    public void setBreakFastImage(Integer breakFastImage) {
        BreakFastImage = breakFastImage;
    }

    public String getBreakFastCost() {
        return BreakFastCost;
    }

    public void setBreakFastCost(String breakFastCost) {
        BreakFastCost = breakFastCost;
    }

    public String getBreakFastName() {
        return BreakFastName;
    }

    public void setBreakFastName(String breakFastName) {
        BreakFastName = breakFastName;
    }

    private Integer BreakFastImage;
    private String BreakFastCost;
    private String BreakFastName;
}
