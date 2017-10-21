package com.localfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.localfriend.adapter.BreakfastAdapter;
import com.localfriend.adapter.DummyBreakfastData;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.CustomFragment;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BreakFastActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private RecyclerView recy_breakfast;
    private BreakfastAdapter adapter;
    private ProductData productData = new ProductData();
    private List<ProductDetails> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        productData = SingleInstance.getInstance().getProductData();
        setContentView(R.layout.activity_break_fast);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Breakfast");
        actionBar.setTitle("");

        recy_breakfast = (RecyclerView) findViewById(R.id.recy_breakfast);
        recy_breakfast.setLayoutManager(new LinearLayoutManager(this));


        for (int i = 0; i < productData.getProduct().size(); i++) {
//            for (int j = 0; j < productData.getProduct().get(i).getpDetailsList().size(); j++) {
            ProductDetails d = productData.getProduct().get(i).getpDetailsList().get(0);
            d.setpGalleryFileList(productData.getProduct().get(i).getpGalleryFileList());
            d.setName(productData.getProduct().get(i).getpTitle());
            allProducts.add(d);
//            }
        }

        adapter = new BreakfastAdapter(allProducts, this);
        recy_breakfast.setAdapter(adapter);
    }

    public void addToCart(ProductDetails p) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 1);
            o.put("pDetailsId", p.getId());
            o.put("pQuantity", 1);
            showLoadingDialog("");
            postCall(getContext(), AppConstant.BASE_URL + "Cart", o, "", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Context getContext() {
        return BreakFastActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        MyApp.showMassage(getContext(), o.optString("message"));
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        MyApp.popMessage("Error", "Time-out error occurred. Please try again.", getContext());
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }
}
