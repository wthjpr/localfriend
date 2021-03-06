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
import java.util.HashMap;
import java.util.List;

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
    private RelativeLayout rl_main;
    private List<Cartlist> cartData = new ArrayList<>();
    private boolean isNotConnectedCall = false;
    private Cart myCart;

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
        rl_main = myView.findViewById(R.id.rl_main);
        txt_subtotal_title = myView.findViewById(R.id.txt_subtotal_title);
        txt_total_title = myView.findViewById(R.id.txt_total_title);
        txt_subtotal_title.getPaint().setShader(textShader);
        txt_total_title.getPaint().setShader(textShader);

        recy_cart = myView.findViewById(R.id.recy_cart);
        recy_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(cartData, CartFragment.this);
        recy_cart.setAdapter(adapter);
        tv_checkout = myView.findViewById(R.id.tv_checkout);
        txt_total = myView.findViewById(R.id.txt_total);
        txt_subtotal = myView.findViewById(R.id.txt_subtotal);

        img_empty_cart = myView.findViewById(R.id.img_empty_cart);
        tv_start_shopping = myView.findViewById(R.id.tv_start_shopping);
        tv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyApp.isConnectingToInternet(getActivity())) {
                    isNotConnectedCall = true;
                    MyApp.popMessage("Local Friend", "Please connect to a working internet connection" +
                            " to place your order.\nThank you", getActivity());
                } else {
                    if (Integer.parseInt(myCart.getTotalprice()) < 49 && !MyApp.getStatus("BREAKFAST_ADDED")) {
                        MyApp.popMessage("Message", "Cart total amount should equal or greater then " +
                                "Rs. 49 to proceed for checkout." +
                                "\nThank you", getContext());
                    } else if (isNotConnectedCall) {
                        MyApp.showMassage(getContext(), "Updating online data");
                        isNotConnectedCall = false;
                        showLoadingDialog("");
                        getCallWithHeader(AppConstant.BASE_URL + "Cart", 1);
                    } else {
                        startActivity(new Intent(getActivity(), CheckOutActivity.class));
                        MyApp.setStatus("BREAKFAST_ADDED", false);
                    }

                }

            }
        });
        tv_start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            RelativeLayout v = myView.findViewById(R.id.rl_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 1 * MyApp.getApplication().getStatusBarHeight(), 0, 0);
        }
        myCart = MyApp.getApplication().readCart();
        if (myCart.getCartlist().size() > 0) {
            rl_main.setBackgroundResource(R.drawable.bg_cart);
            recy_cart.setVisibility(View.VISIBLE);
            MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, myCart.getCartlist().size());
            ((MainActivity) getActivity()).txt_cart_count.setText("" + myCart.getCartlist().size());
            ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.VISIBLE);
            cartData.removeAll(cartData);
            cartData.addAll(myCart.getCartlist());
            adapter.notifyDataSetChanged();
            tv_checkout.setVisibility(View.VISIBLE);
            recy_cart.setVisibility(View.VISIBLE);
            card_price.setVisibility(View.VISIBLE);
            String string = "Rs. ";
            txt_total.setText(string + " " + myCart.getSellingprice());
            txt_subtotal.setText(string + " " + myCart.getTotalprice());

            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < myCart.getCartlist().size(); i++) {
                map.put(myCart.getCartlist().get(i).getId(), myCart.getCartlist().get(i).getId());
            }
            MyApp.getApplication().writeType(map);
        } else
            showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Cart", 1);
        return myView;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

//        {"status":"success","data":{"totalprice":0,"sellingprice":0,"saveprice":0,"totalitem":0,"cartlist":[]}}
        if (o.optString("status").equals("success") && callNumber == 1) {
            dismissDialog();

            try {
                myCart = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (myCart.getCartlist().size() > 0) {
                    MyApp.getApplication().writeCart(myCart);
                    rl_main.setBackgroundResource(R.drawable.bg_cart);
                    recy_cart.setVisibility(View.VISIBLE);
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, myCart.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setText("" + myCart.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.VISIBLE);
                    cartData.removeAll(cartData);
                    cartData.addAll(myCart.getCartlist());
                    adapter.notifyDataSetChanged();
                    tv_checkout.setVisibility(View.VISIBLE);
                    recy_cart.setVisibility(View.VISIBLE);
                    card_price.setVisibility(View.VISIBLE);
                    String string = "Rs. ";
                    txt_total.setText(string + " " + myCart.getSellingprice());
                    txt_subtotal.setText(string + " " + myCart.getTotalprice());

                    HashMap<String, String> map = new HashMap<>();
                    for (int i = 0; i < myCart.getCartlist().size(); i++) {
                        map.put(myCart.getCartlist().get(i).getId(), myCart.getCartlist().get(i).getId());
                    }

                    MyApp.getApplication().writeType(map);

                } else {
                    MyApp.setStatus("BREAKFAST_ADDED", false);
                    rl_main.setBackgroundResource(0);
                    rl_main.setBackgroundColor(Color.WHITE);
                    recy_cart.setVisibility(View.GONE);
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, 0);
                    ((MainActivity) getActivity()).txt_cart_count.setText("0" + myCart.getCartlist().size());
                    ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.GONE);
                    img_empty_cart.setVisibility(View.VISIBLE);
                    tv_start_shopping.setVisibility(View.VISIBLE);
                    card_price.setVisibility(View.GONE);
                    MyApp.getApplication().writeCart(new Cart());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
//            dismissDialog();
//            MyApp.showMassage(getContext(), o.optString("message"));
//            showLoadingDialog("");
//            getCallWithHeader(AppConstant.BASE_URL + "Cart", 1);
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
            myCart.setTotalprice("" + (Integer.parseInt(myCart.getTotalprice()) + Integer.parseInt(item.getPrice())));
            myCart.setSellingprice("" + (Integer.parseInt(myCart.getSellingprice()) + Integer.parseInt(item.getSellingprice())));
            adapter.notifyDataSetChanged();
            txt_total.setText("Rs. " + myCart.getSellingprice());
            txt_subtotal.setText("Rs. " + myCart.getTotalprice());
//            showLoadingShadowDialog("");
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
//            showLoadingShadowDialog("");
            myCart.setTotalprice("" + (Integer.parseInt(myCart.getTotalprice()) - Integer.parseInt(item.getPrice())));
            myCart.setSellingprice("" + (Integer.parseInt(myCart.getSellingprice()) - Integer.parseInt(item.getSellingprice())));
            adapter.notifyDataSetChanged();
            txt_total.setText("Rs. " + myCart.getSellingprice());
            txt_subtotal.setText("Rs. " + myCart.getTotalprice());
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
            myCart.setTotalprice("" + (Integer.parseInt(myCart.getTotalprice()) - (Integer.parseInt(item.getPrice()) *
                    item.getQuantiy())));
            myCart.setSellingprice("" + (Integer.parseInt(myCart.getSellingprice()) - (Integer.parseInt(item.getSellingprice()) *
                    item.getQuantiy())));
            adapter.notifyDataSetChanged();
            txt_total.setText("Rs. " + myCart.getSellingprice());
            txt_subtotal.setText("Rs. " + myCart.getTotalprice());
//            showLoadingShadowDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) - 1));
        ((MainActivity) getActivity()).txt_cart_count.setText("" + MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER));
        if (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) <= 0) {
            rl_main.setBackgroundResource(0);
            rl_main.setBackgroundColor(Color.WHITE);
            recy_cart.setVisibility(View.GONE);
            MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, 0);
            ((MainActivity) getActivity()).txt_cart_count.setText("0" + myCart.getCartlist().size());
            ((MainActivity) getActivity()).txt_cart_count.setVisibility(View.GONE);
            img_empty_cart.setVisibility(View.VISIBLE);
            tv_start_shopping.setVisibility(View.VISIBLE);
            card_price.setVisibility(View.GONE);
            MyApp.getApplication().writeCart(new Cart());
        }

    }
}
