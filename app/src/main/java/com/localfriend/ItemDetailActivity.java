package com.localfriend;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;
import com.localfriend.model.Slider;
import com.localfriend.utils.AppConstant;
import com.localfriend.utils.CircleAnimationUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;

public class ItemDetailActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    //    private BannerSlider bannerSlider;
    private Toolbar toolbar;
    private TextView tv_add_cart, tv_description, tv_cost, tv_five_kg, tv_four_kg;
    private RadioButton tv_three_kg, tv_two_kg, tv_one_kg;
    private ProductDetails productDetails;
    private ImageView img_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productDetails = SingleInstance.getInstance().getSelectedProduct();
        setContentView(R.layout.activity_item_detail);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        setResponseListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText(productDetails.getName());
        actionBar.setTitle("");
        productId = productDetails.getId();
        img_product = findViewById(R.id.img_product);
        try {
            Glide.with(getContext()).load(getIntent().getStringExtra(AppConstant.EXTRA_1)).placeholder(R.drawable.place_holder)
                    .into(img_product);
        } catch (Exception e) {
        }


        setupUiElement();
    }

    private Context getContext() {
        return ItemDetailActivity.this;
    }


    private void setupUiElement() {

        img_home = findViewById(R.id.img_home);
        img_tiffin = findViewById(R.id.img_tiffin);
        img_cart = findViewById(R.id.img_cart);
        img_more = findViewById(R.id.img_more);


        tv_home = findViewById(R.id.tv_home);
        tv_tiffin = findViewById(R.id.tv_tiffin);
        tv_cart = findViewById(R.id.tv_cart);
        tv_more = findViewById(R.id.tv_more);
        txt_cart_count = findViewById(R.id.txt_cart_count);
        if (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) > 0) {
            txt_cart_count.setVisibility(View.VISIBLE);
            txt_cart_count.setText("" + MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER));
        } else {
            txt_cart_count.setVisibility(View.GONE);
        }
        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);
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
        tv_cost.setText(MyApp.getRupeeCurrency() + productDetails.getSellingPrice());
        tv_one_kg = findViewById(R.id.tv_one_kg);
        tv_two_kg = findViewById(R.id.tv_two_kg);
        tv_three_kg = findViewById(R.id.tv_three_kg);
        tv_four_kg = findViewById(R.id.tv_four_kg);
        tv_five_kg = findViewById(R.id.tv_five_kg);

        tv_one_kg.setText(productDetails.getUnit() + " " + productDetails.getuType());

        if (productDetails.getMyList().size() > 1) {
            tv_two_kg.setVisibility(View.VISIBLE);
            tv_two_kg.setText(productDetails.getMyList().get(1).getUnit() + " " + productDetails.getMyList().get(1).getuType());
        }
        if (productDetails.getMyList().size() > 2) {
            tv_two_kg.setVisibility(View.VISIBLE);
            tv_two_kg.setText(productDetails.getMyList().get(1).getUnit() + " " + productDetails.getMyList().get(1).getuType());
            tv_three_kg.setVisibility(View.VISIBLE);
            tv_three_kg.setText(productDetails.getMyList().get(2).getUnit() + " " + productDetails.getMyList().get(2).getuType());
        }
    }

    private String productId;

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_add_cart) {

            JSONObject o = new JSONObject();
            makeFlyAnimation(img_product, productDetails.getId());
            try {
                o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
                o.put("oprationid", 1);
                o.put("pDetailsId", productId);
                o.put("pQuantity", 1);
//                showLoadingDialog("");
                postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Cart", o, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (v.getId() == R.id.tv_one_kg) {
            tv_description.setText(productDetails.getuDescription());
            tv_cost.setText(MyApp.getRupeeCurrency() + productDetails.getSellingPrice());
            productId = productDetails.getId();
            tv_one_kg.setChecked(true);
            tv_two_kg.setChecked(false);
            tv_three_kg.setChecked(false);
        } else if (v.getId() == R.id.tv_two_kg) {
            tv_description.setText(productDetails.getMyList().get(1).getuDescription());
            tv_cost.setText(MyApp.getRupeeCurrency() + productDetails.getMyList().get(1).getSellingPrice());
            productId = productDetails.getMyList().get(1).getId();
            tv_one_kg.setChecked(false);
            tv_two_kg.setChecked(true);
            tv_three_kg.setChecked(false);
        } else if (v.getId() == R.id.tv_three_kg) {
            tv_description.setText(productDetails.getMyList().get(2).getuDescription());
            tv_cost.setText(MyApp.getRupeeCurrency() + productDetails.getMyList().get(2).getSellingPrice());
            productId = productDetails.getMyList().get(2).getId();
            tv_one_kg.setChecked(false);
            tv_two_kg.setChecked(false);
            tv_three_kg.setChecked(true);
        } else if (v.getId() == R.id.tv_four_kg) {
            Toast.makeText(this, "4 kg Added", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.tv_five_kg) {
            Toast.makeText(this, "5 kg Added", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.rl_tab_1) {

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
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 4));
            finishAffinity();
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            dismissDialog();
//            MyApp.showMassage(getContext(), o.optString("message"));
        }

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private ImageView img_home, img_tiffin, img_cart, img_more;
    private TextView tv_home, tv_tiffin, tv_cart, tv_more, txt_cart_count;

    public void makeFlyAnimation(ImageView targetView, String key) {
        HashMap<String, String> map = MyApp.getApplication().readType();
        if (!map.containsKey(key)) {
            map.put(key, key);
            MyApp.getApplication().writeType(map);
            int counter = MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER);
            counter = counter + 1;
            MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, counter);
            txt_cart_count.setText("" + counter);
        }
        txt_cart_count.setVisibility(View.VISIBLE);
        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(500)
                .setDestView(txt_cart_count).setAnimationListener(new Animator.AnimatorListener() {
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
