package com.localfriend;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.application.AppConstants;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

public class ItemDetailActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private BannerSlider bannerSlider;
    private Toolbar toolbar;
    private TextView tv_add_cart,tv_description,tv_cost,tv_five_kg,tv_four_kg,tv_three_kg,tv_two_kg,tv_one_kg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        setResponseListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Apple");
        actionBar.setTitle("");

        bannerSlider = (BannerSlider)findViewById(R.id.banner_slider1);
        List<Banner> banners = new ArrayList<>();
        List<Slider> sliderList = SingleInstance.getInstance().getSliderList();
        if (sliderList.size() == 0) {
            getCall(AppConstants.BASE_URL + AppConstants.SLIDER, "", 1);
        }

        for (int i = 0; i < sliderList.size(); i++) {
            String url = sliderList.get(i).getImageURL();
            banners.add(new RemoteBanner(url));
        }
        bannerSlider.setBanners(banners);
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
//                startActivity(new Intent(getContext(), AllActivity.class));
            }
        });

        setupUiElement();
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

        tv_one_kg = findViewById(R.id.tv_one_kg);
        tv_two_kg = findViewById(R.id.tv_two_kg);
        tv_three_kg = findViewById(R.id.tv_three_kg);
        tv_four_kg = findViewById(R.id.tv_four_kg);
        tv_five_kg = findViewById(R.id.tv_five_kg);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_add_cart) {
            Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.tv_one_kg) {
            Toast.makeText(this, "1 kg Added", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.tv_two_kg) {
            Toast.makeText(this, "2 kg Added", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.tv_three_kg) {
            Toast.makeText(this, "3 kg Added", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.tv_four_kg) {
            Toast.makeText(this, "4 kg Added", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.tv_five_kg) {
            Toast.makeText(this, "5 kg Added", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("success")) {
                List<Slider> sliderList = null;
                try {
                    Type listType = new TypeToken<ArrayList<Slider>>() {
                    }.getType();
                    sliderList = new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                    SingleInstance.getInstance().setSliderList(sliderList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<Banner> banners = new ArrayList<>();
                for (Slider s : sliderList) {
                    banners.add(new RemoteBanner(s.getImageURL()));
                }


                bannerSlider.setBanners(banners);
                bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onClick(int position) {
//                        startActivity(new Intent(getContext(), AllActivity.class));
                    }
                });
            }
        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
