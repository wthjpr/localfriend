package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.AllActivity;
import com.localfriend.FoodActivity;
import com.localfriend.ItemDetailActivity;
import com.localfriend.R;
import com.localfriend.VegetableActivity;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.CategoryDetails;
import com.localfriend.model.ProductData;
import com.localfriend.model.Slider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private BannerSlider bannerSlider;
    private LinearLayout lrn_fruit, lrn_vegetable, lrn_tiffin, lrn_food, lrn_mithaiwala, lrn_discount;
    private GifImageView gif_loader;
//    private AVLoadingIndicatorView loading;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        bannerSlider = (BannerSlider) myView.findViewById(R.id.banner_slider1);
        lrn_fruit = myView.findViewById(R.id.lrn_fruit);
        lrn_vegetable = myView.findViewById(R.id.lrn_vegetable);
        lrn_tiffin = myView.findViewById(R.id.lrn_tiffin);
        lrn_food = myView.findViewById(R.id.lrn_food);
        lrn_mithaiwala = myView.findViewById(R.id.lrn_mithaiwala);
        lrn_discount = myView.findViewById(R.id.lrn_discount);
        gif_loader = myView.findViewById(R.id.gif_loader);
        setResponseListener(this);

        setTouchNClick(lrn_fruit);
        setTouchNClick(lrn_vegetable);
        setTouchNClick(lrn_tiffin);
        setTouchNClick(lrn_food);
        setTouchNClick(lrn_mithaiwala);
        setTouchNClick(lrn_discount);

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

        return myView;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == lrn_fruit) {
            showLoadingDialog("");
            getCall(AppConstants.BASE_URL + "product?categoryid=" + 4 + "&storeid=90390cdd-f991-4539-b666-488858d60a94", "", 3);
            // startActivity(new Intent(getActivity(),ItemDetailActivity.class));
//            loadCategory(4);
        } else if (v == lrn_vegetable) {
//            loadCategory(1);
            startActivity(new Intent(getContext(), VegetableActivity.class));
        } else if (v == lrn_tiffin) {
            loadCategory(3);
        } else if (v == lrn_food) {
            loadCategory(2);
        } else if (v == lrn_mithaiwala) {
            loadCategory(5);
        } else if (v == lrn_discount) {
            MyApp.popMessage("LocalFriend", "No Deals available for now.\nThank you", getContext());
        }
    }

    private void loadCategory(int i) {
        showLoadingDialog("");
        getCall(AppConstants.BASE_URL + "Category/" + i, "", 2);
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
        } else if (callNumber == 2) {
            dismissDialog();
            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                    MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    SingleInstance.getInstance().setCatList(catList);
                    startActivity(new Intent(getContext(), FoodActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 3) {
            dismissDialog();
            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    startActivity(new Intent(getContext(), VegetableActivity.class));
                } else {
                    MyApp.popMessage("Local Friend", "We are not able to find any product related to selected category & store," +
                            " Please come back later.\nThank you.", getContext());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        if (callNumber == 2) {
            dismissDialog();
            MyApp.popMessage("Error!", "Time-out error occurred, please try again.", getContext());
        }
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error!", error, getContext());
    }
}
