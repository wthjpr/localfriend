package com.localfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;
import com.localfriend.model.Slider;
import com.localfriend.utils.AppConstant;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;

public class ItemDetailActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    //    private BannerSlider bannerSlider;
    private Toolbar toolbar;
    private TextView tv_add_cart, tv_description, tv_cost, tv_five_kg, tv_four_kg, tv_three_kg, tv_two_kg, tv_one_kg;
    private ProductDetails productDetails;
    private ImageView img_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        productDetails = SingleInstance.getInstance().getSelectedProduct();
        setContentView(R.layout.activity_item_detail);
        toolbar =  findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        setResponseListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText(productDetails.getName());
        actionBar.setTitle("");

        img_product = findViewById(R.id.img_product);
        try {
            Picasso.with(getContext()).load(getIntent().getStringExtra(AppConstant.EXTRA_1))
                    .into(img_product);
        } catch (Exception e) {
        }


        setupUiElement();
    }

    private Context getContext() {
        return ItemDetailActivity.this;
    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_add_cart);

        setTouchNClick(R.id.tv_one_kg);
        setTouchNClick(R.id.tv_two_kg);
        setTouchNClick(R.id.tv_three_kg);
        setTouchNClick(R.id.tv_four_kg);
        setTouchNClick(R.id.tv_five_kg);

        tv_add_cart = findViewById(R.id.tv_add_cart);

        tv_cost = findViewById(R.id.tv_cost);

        tv_description = findViewById(R.id.tv_description);
        tv_description.setText(productDetails.getuDescription());
        tv_cost.setText("Rs. "+productDetails.getSellingPrice());
        tv_one_kg = findViewById(R.id.tv_one_kg);
        tv_two_kg = findViewById(R.id.tv_two_kg);
        tv_three_kg = findViewById(R.id.tv_three_kg);
        tv_four_kg = findViewById(R.id.tv_four_kg);
        tv_five_kg = findViewById(R.id.tv_five_kg);

        tv_one_kg.setText(productDetails.getUnit() + " " + productDetails.getuType());

        if (productDetails.getMyList().size() > 1) {
            tv_two_kg.setVisibility(View.VISIBLE);
            tv_two_kg.setText(productDetails.getMyList().get(1).getUnit()+" "+productDetails.getMyList().get(1).getuType());
        }

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_add_cart) {
            JSONObject o = new JSONObject();

            try {
                o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
                o.put("oprationid", 1);
                o.put("pDetailsId", productDetails.getId());
                o.put("pQuantity", 1);
                showLoadingDialog("");
                postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Cart", o, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.tv_one_kg) {
            tv_description.setText(productDetails.getuDescription());
            tv_cost.setText("Rs. "+productDetails.getSellingPrice());
        } else if (v.getId() == R.id.tv_two_kg) {
            tv_description.setText(productDetails.getMyList().get(1).getuDescription());
            tv_cost.setText("Rs. "+productDetails.getMyList().get(1).getSellingPrice());
        } else if (v.getId() == R.id.tv_three_kg) {
            Toast.makeText(this, "3 kg Added", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_four_kg) {
            Toast.makeText(this, "4 kg Added", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_five_kg) {
            Toast.makeText(this, "5 kg Added", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            dismissDialog();
            MyApp.showMassage(getContext(), o.optString("message"));
        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error","Time-out error occurred, please try again.",getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error",error,getContext());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
