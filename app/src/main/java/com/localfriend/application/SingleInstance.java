package com.localfriend.application;

import com.localfriend.model.CategoryDetails;
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
    private List<CategoryDetails> catList = new ArrayList<>();
    private ProductData productData = new ProductData();
    private ProductDetails selectedProduct;

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
