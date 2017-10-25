package com.localfriend.fragments;


import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.localfriend.CheckOutActivity;
import com.localfriend.MainActivity;
import com.localfriend.R;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.adapter.WishListAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.model.Cart;
import com.localfriend.model.Cartlist;
import com.localfriend.utils.AppConstant;
import com.localfriend.utils.CircleAnimationUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends CustomFragment implements CustomFragment.ResponseCallback {
    private RecyclerView recy_cart;
    private WishListAdapter adapter;

    public WishListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_wishlist, container, false);

        Shader textShader = new LinearGradient(0, 0, 0, 20,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);


        recy_cart = myView.findViewById(R.id.recy_cart);
        recy_cart.setLayoutManager(new LinearLayoutManager(getContext()));

        if (Build.VERSION.SDK_INT >= 21) {
            RelativeLayout v = myView.findViewById(R.id.rl_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 1 * MyApp.getApplication().getStatusBarHeight(), 0, 0);
        }

//        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "WishList", 1);
        return myView;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
//        {"status":"success","data":{"totalprice":0,"sellingprice":0,"saveprice":0,"totalitem":0,"cartlist":[]}}
        if (o.optString("status").equals("success") && callNumber == 1) {
            try {
                Cart c = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (c.getWishListlist().size() > 0) {
                    adapter = new WishListAdapter(c.getWishListlist(), WishListFragment.this);
                    recy_cart.setAdapter(adapter);

                } else {
                    MyApp.showMassage(getContext(), "No data available");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            MyApp.showMassage(getContext(), o.optString("message"));
            showLoadingDialog("");
            getCallWithHeader(AppConstant.BASE_URL + "WishList", 1);
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
    }

    public void deleteItem(Cartlist item) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 3);
            o.put("pDetailsId", item.getProductid());
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "WishList", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void moveToCart(Cartlist p, ImageView view) {
        makeFlyAnimation(view, p.getProductid());
        JSONObject o = new JSONObject();
        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 4);
            o.put("pDetailsId", p.getId());
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "WishList", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void makeFlyAnimation(ImageView targetView, String key) {
        HashMap<String, String> map = MyApp.getApplication().readType();
        if (!map.containsKey(key)) {
            map.put(key, key);
            MyApp.getApplication().writeType(map);
            int counter = MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER);
            counter = counter + 1;
            MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, counter);
            ((MainActivity) getContext()).txt_cart_count.setText("" + counter);
        }
        ((MainActivity) getContext()).txt_cart_count.setVisibility(View.VISIBLE);
        new CircleAnimationUtil().attachActivity(getActivity()).setTargetView(targetView).setMoveDuration(700)
                .setDestView(((MainActivity) getContext()).txt_cart_count).setAnimationListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {

            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        }).startAnimation();
    }
}
