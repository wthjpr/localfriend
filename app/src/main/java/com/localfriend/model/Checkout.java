package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/24/2017.
 */

public class Checkout {
    private String totalprice;
    private String sellingprice;
    private String saveprice;
    private String totalitem;
    private List<CheckoutListData> checkoutlist;

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

    public List<CheckoutListData> getCheckoutlist() {
        return checkoutlist;
    }

    public void setCheckoutlist(List<CheckoutListData> checkoutlist) {
        this.checkoutlist = checkoutlist;
    }

    public class CheckoutListData {
        private String categoryid;
        private String categoryname;
        private String totalproduct;
        private String shippingPrice;
        private String totalTax;
        private String totalprice;
        private String descount;
        private String payamount;
        private List<ChecklistItem> item;
        private List<TimeStamp> timestemp;
        private int selection = 0;

        public int getSelection() {
            return selection;
        }

        public void setSelection(int selection) {
            this.selection = selection;
        }

        public List<ChecklistItem> getItem() {
            return item;
        }

        public void setItem(List<ChecklistItem> item) {
            this.item = item;
        }

        public List<TimeStamp> getTimestemp() {
            return timestemp;
        }

        public void setTimestemp(List<TimeStamp> timestemp) {
            this.timestemp = timestemp;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getTotalproduct() {
            return totalproduct;
        }

        public void setTotalproduct(String totalproduct) {
            this.totalproduct = totalproduct;
        }

        public String getShippingPrice() {
            return shippingPrice;
        }

        public void setShippingPrice(String shippingPrice) {
            this.shippingPrice = shippingPrice;
        }

        public String getTotalTax() {
            return totalTax;
        }

        public void setTotalTax(String totalTax) {
            this.totalTax = totalTax;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getDescount() {
            return descount;
        }

        public void setDescount(String descount) {
            this.descount = descount;
        }

        public String getPayamount() {
            return payamount;
        }

        public void setPayamount(String payamount) {
            this.payamount = payamount;
        }
    }

    public class ChecklistItem {
        private String id;
        private String productid;
        private String productname;
        private String productimage;
        private String price;
        private String sellingprice;
        private String dealsprice;
        private String quantiy;
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

        public String getQuantiy() {
            return quantiy;
        }

        public void setQuantiy(String quantiy) {
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
}
