package com.localfriend.fragments;


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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.localfriend.CheckOutActivity;
import com.localfriend.MainActivity;
import com.localfriend.R;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.model.Cart;
import com.localfriend.model.Cartlist;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends CustomFragment implements CustomFragment.ResponseCallback {
    private TextView tv_checkout;
    private TextView tv_start_shopping;
    private TextView txt_subtotal_title;
    private TextView txt_total_title;
    private TextView txt_total;
    private TextView txt_subtotal;
    private RecyclerView recy_cart;
    private CartAdapter adapter;
    private ImageView img_empty_cart;
    private CardView card_price;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_cart, container, false);
        tv_checkout = myView.findViewById(R.id.tv_checkout);

        Shader textShader = new LinearGradient(0, 0, 0, 20,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);

        card_price = myView.findViewById(R.id.card_price);
        txt_subtotal_title = myView.findViewById(R.id.txt_subtotal_title);
        txt_total_title = myView.findViewById(R.id.txt_total_title);
        txt_subtotal_title.getPaint().setShader(textShader);
        txt_total_title.getPaint().setShader(textShader);

        recy_cart = (RecyclerView) myView.findViewById(R.id.recy_cart);
        recy_cart.setLayoutManager(new LinearLayoutManager(getContext()));

        tv_checkout = myView.findViewById(R.id.tv_checkout);
        txt_total = myView.findViewById(R.id.txt_total);
        txt_subtotal = myView.findViewById(R.id.txt_subtotal);

        img_empty_cart = myView.findViewById(R.id.img_empty_cart);
        tv_start_shopping = myView.findViewById(R.id.tv_start_shopping);
        tv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CheckOutActivity.class));
            }
        });
        tv_start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        showLoadingDialog("");
        if (Build.VERSION.SDK_INT >= 21) {
            RelativeLayout v = myView.findViewById(R.id.rl_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 1 * MyApp.getApplication().getStatusBarHeight(), 0, 0);
        }

//        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        getCallWithHeader(AppConstant.BASE_URL + "Cart", 1);
        return myView;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
//        {"status":"success","data":{"totalprice":0,"sellingprice":0,"saveprice":0,"totalitem":0,"cartlist":[]}}
        if (o.optString("status").equals("success") && callNumber == 1) {
            try {
                Cart c = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (c.getCartlist().size() > 0) {
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, c.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setText("" + c.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.VISIBLE);
                    adapter = new CartAdapter(c.getCartlist(), CartFragment.this);
                    recy_cart.setAdapter(adapter);
                    tv_checkout.setVisibility(View.VISIBLE);
                    recy_cart.setVisibility(View.VISIBLE);
                    card_price.setVisibility(View.VISIBLE);
                    String string = "\u20B9";
                    byte[] utf8 = null;
                    try {
                        utf8 = string.getBytes("UTF-8");
                        string = new String(utf8, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    txt_total.setText(string + " " + c.getSellingprice());
                    txt_subtotal.setText(string + " " + c.getTotalprice());
                } else {
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, 0);
                    ((MainActivity) getActivity()).txt_cart_count.setText("0" + c.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.GONE);
                    img_empty_cart.setVisibility(View.VISIBLE);
                    tv_start_shopping.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            MyApp.showMassage(getContext(), o.optString("message"));
            showLoadingDialog("");
            getCallWithHeader(AppConstant.BASE_URL, 1);
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

    public void increaseCount(Cartlist item) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 1);
            o.put("pDetailsId", item.getProductid());
            o.put("pQuantity", 1);
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void decreaseCount(Cartlist item) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 2);
            o.put("pDetailsId", item.getProductid());
            o.put("pQuantity", 1);
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(Cartlist item) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 3);
            o.put("pDetailsId", item.getProductid());
            o.put("pQuantity", 1);
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
