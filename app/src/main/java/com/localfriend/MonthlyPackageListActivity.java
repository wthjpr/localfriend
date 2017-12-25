package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.adapter.MonthlyPkgAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Address;
import com.localfriend.model.Product;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MonthlyPackageListActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private RecyclerView rv_addresses;
    private MonthlyPkgAdapter adapter;
    private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    private ImageView img_home, img_tiffin, img_cart, img_more;
    private TextView txt_cart_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_monthly_package);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        if (getIntent().getBooleanExtra(AppConstant.EXTRA_1, false)) {
            mTitle.setText("Weekly Subscription");
        } else {
            mTitle.setText("Monthly Subscription");
        }

        actionBar.setTitle("");
        setupUiElements();
        getCallWithHeader(AppConstant.BASE_URL + "Address", 1);
        txt_cart_count = findViewById(R.id.txt_cart_count);
        if (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) > 0) {
            txt_cart_count.setVisibility(View.VISIBLE);
            txt_cart_count.setText("" + MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER));
        } else {
            txt_cart_count.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupUiElements() {
        rv_addresses = findViewById(R.id.rv_addresses);
        rv_addresses.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Product> productList = SingleInstance.getInstance().getMonthlyPkgList();
        adapter = new MonthlyPkgAdapter(productList, getContext(), getIntent().getBooleanExtra(AppConstant.EXTRA_1, false));
        rv_addresses.setAdapter(adapter);

        img_home = findViewById(R.id.img_home);
        img_tiffin = findViewById(R.id.img_tiffin);
        img_cart = findViewById(R.id.img_cart);
        img_more = findViewById(R.id.img_more);


        tv_home = findViewById(R.id.tv_home);
        tv_tiffin = findViewById(R.id.tv_tiffin);
        tv_cart = findViewById(R.id.tv_cart);
        tv_more = findViewById(R.id.tv_more);

        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);
    }

    private Context getContext() {
        return MonthlyPackageListActivity.this;
    }


    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {

            img_home.setSelected(true);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(true);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#275B89"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home_active);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);
            SingleInstance.getInstance().setTabClicked(1);
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 1));
            finishAffinity();

        } else if (v.getId() == R.id.rl_tab_2) {
            img_home.setSelected(false);
            img_tiffin.setSelected(true);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(true);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#275B89"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tiffin_active);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);
            SingleInstance.getInstance().setTabClicked(2);
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 2));
            finishAffinity();

        } else if (v.getId() == R.id.rl_tab_3) {
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(true);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(true);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#275B89"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart_active);
            img_more.setImageResource(R.drawable.ic_more);
            SingleInstance.getInstance().setTabClicked(3);
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 3));
            finishAffinity();
        } else if (v.getId() == R.id.rl_tab_4) {

            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(true);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(true);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#275B89"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more_active);
            SingleInstance.getInstance().setTabClicked(4);
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 4));
            finishAffinity();
        }
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error", "Time-out error occurred, please try again.", getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }


    public void clickMenu(Product product) {

    }

    public void clickBuyNow(Product product) {
        SingleInstance.getInstance().setSubscriptionProduct(product);
        startActivity(new Intent(getContext(), SubscriptionCheckOutActivity.class).putExtra(AppConstant.EXTRA_1,
                product.getpTitle()).putExtra(AppConstant.EXTRA_2, getIntent().getBooleanExtra(AppConstant.EXTRA_1, false)));
    }
}
