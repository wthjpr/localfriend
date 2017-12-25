package com.localfriend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONI on 10/17/2017.
 */

public class Cart implements Serializable{
    private final static long serialVersionUID = 814468376534L;
    private String totalprice;
    private String sellingprice;
    private String saveprice;
    private int totalitem;
    private List<Cartlist> cartlist = new ArrayList<>();
    private List<Cartlist> wishListlist = new ArrayList<>();

    public List<Cartlist> getWishListlist() {
        return wishListlist;
    }

    public void setWishListlist(List<Cartlist> wishListlist) {
        this.wishListlist = wishListlist;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getSaveprice() {
        return saveprice;
    }

    public void setSaveprice(String saveprice) {
        this.saveprice = saveprice;
    }

    public int getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(int totalitem) {
        this.totalitem = totalitem;
    }

    public List<Cartlist> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<Cartlist> cartlist) {
        this.cartlist = cartlist;
    }
}
