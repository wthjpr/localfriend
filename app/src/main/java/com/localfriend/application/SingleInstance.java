package com.localfriend.application;

import com.localfriend.model.Address;
import com.localfriend.model.CategoryDetails;
import com.localfriend.model.Checkout;
import com.localfriend.model.Product;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;
import com.localfriend.model.Slider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class SingleInstance {
    private static final SingleInstance ourInstance = new SingleInstance();

    public static SingleInstance getInstance() {
        return ourInstance;
    }

    private SingleInstance() {
    }

    private List<Slider> sliderList = new ArrayList();
    private List<Product> monthlyPkgList = new ArrayList();

    public List<Product> getMonthlyPkgList() {
        return monthlyPkgList;
    }

    public void setMonthlyPkgList(List<Product> monthlyPkgList) {
        this.monthlyPkgList = monthlyPkgList;
    }

    private List<CategoryDetails> catList = new ArrayList<>();
    private List<CategoryDetails> tiffinCatList = new ArrayList<>();

    public List<CategoryDetails> getTiffinCatList() {
        return tiffinCatList;
    }

    public void setTiffinCatList(List<CategoryDetails> tiffinCatList) {
        this.tiffinCatList = tiffinCatList;
    }

    private ProductData productData = new ProductData();
    private ProductDetails selectedProduct;
    private boolean isUpdateDone;
    private Address updatingAddress;
    private Checkout checkoutData;
    private Address selectedAddress;
    private String ShippingID;
    private String payAmount;
    private Product subscriptionProduct;

    public Product getSubscriptionProduct() {
        return subscriptionProduct;
    }

    public void setSubscriptionProduct(Product subscriptionProduct) {
        this.subscriptionProduct = subscriptionProduct;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getShippingID() {
        return ShippingID;
    }

    public void setShippingID(String shippingID) {
        ShippingID = shippingID;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Checkout getCheckoutData() {
        return checkoutData;
    }

    public void setCheckoutData(Checkout checkoutData) {
        this.checkoutData = checkoutData;
    }

    public Address getUpdatingAddress() {
        return updatingAddress;
    }

    public void setUpdatingAddress(Address updatingAddress) {
        this.updatingAddress = updatingAddress;
    }

    public boolean isUpdateDone() {
        return isUpdateDone;
    }

    public void setUpdateDone(boolean updateDone) {
        isUpdateDone = updateDone;
    }

    public ProductDetails getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductDetails selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public ProductData getProductData() {
        return productData;
    }

    public void setProductData(ProductData productData) {
        this.productData = productData;
    }

    public List<CategoryDetails> getCatList() {
        return catList;
    }

    public void setCatList(List<CategoryDetails> catList) {
        this.catList = catList;
    }

    public List<Slider> getSliderList() {
        return sliderList;
    }

    public void setSliderList(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }
}
