package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/17/2017.
 */

public class Cart {

    private String totalprice;
    private String sellingprice;
    private String saveprice;
    private String totalitem;
    private List<Cartlist> cartlist;

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

    public String getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(String totalitem) {
        this.totalitem = totalitem;
    }

    public List<Cartlist> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<Cartlist> cartlist) {
        this.cartlist = cartlist;
    }
}
