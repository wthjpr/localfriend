package com.localfriend.model;

import java.util.List;

/**
 * Created by SONI on 10/15/2017.
 */

public class ProductData {

    private List<CategoryDetails> category;
    private List<Product> product;

    public List<CategoryDetails> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryDetails> category) {
        this.category = category;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
