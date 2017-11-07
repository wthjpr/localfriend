package com.localfriend;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.VegetableFragment;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;
import com.localfriend.utils.AppConstant;
import com.localfriend.utils.CircleAnimationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VegetableActivity extends CustomActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProductData productData;
    private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    private ImageView img_home, img_tiffin, img_cart, img_more;
    private List<ProductDetails> allProducts = new ArrayList<>();
    private RelativeLayout rl_main;
    private TextView txt_cart_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productData = SingleInstance.getInstance().getProductData();
        setContentView(R.layout.activity_vegetable);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        rl_main = findViewById(R.id.rl_main);
        mTitle.setText("Vegetable");
        String title = getIntent().getStringExtra(AppConstant.EXTRA_1);
        if (title != null) {
            mTitle.setText(title);
        }
        try {
            if (title.equals("Mithai")) {
                rl_main.setBackgroundResource(R.drawable.bg_sweets);
            } else if (title.equals("Fruits")) {
                rl_main.setBackgroundResource(R.drawable.bg_fruits);
            } else if (title.equals("Vegetable")) {
                rl_main.setBackgroundResource(R.drawable.bg_vegetables);
            } else if (title.equals("Morning Meals")) {
                rl_main.setBackgroundResource(R.drawable.morning_need_bg);
            } else {
                rl_main.setBackgroundResource(R.drawable.bg_cart);
            }
        } catch (Exception e) {
        }

        actionBar.setTitle("");

        viewPager = findViewById(R.id.viewpager);
        txt_cart_count = findViewById(R.id.txt_cart_count);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        if (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) > 0) {
            txt_cart_count.setVisibility(View.VISIBLE);
            txt_cart_count.setText("" + MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER));
        } else {
            txt_cart_count.setVisibility(View.GONE);
        }

        setupUiElements();
    }

    private void setupUiElements() {

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
        return VegetableActivity.this;
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

    private void setupViewPager(ViewPager viewPager) {

        for (int i = 0; i < productData.getProduct().size(); i++) {
//            for (int j = 0; j < productData.getProduct().get(i).getpDetailsList().size(); j++) {
            ProductDetails d = productData.getProduct().get(i).getpDetailsList().get(0);
            d.setpGalleryFileList(productData.getProduct().get(i).getpGalleryFileList());
            d.setName(productData.getProduct().get(i).getpTitle());
            d.setCategoryId(productData.getProduct().get(i).getCategoryID());
            List<ProductDetails> myList = new ArrayList<>();
            for (int j = 0; j < productData.getProduct().get(i).getpDetailsList().size(); j++) {
                myList.add(productData.getProduct().get(i).getpDetailsList().get(j));
            }
            d.setMyList(myList);
            allProducts.add(d);
//            }
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new VegetableFragment(allProducts), "All");
        if (productData.getCategory().size() > 0) {

            for (int i = 0; i < productData.getCategory().size(); i++) {
                List<ProductDetails> others = new ArrayList<>();
                for (int j = 0; j < allProducts.size(); j++) {
                    if (allProducts.get(j).getCategoryId().equals(productData.getCategory().get(i).getID())) {
                        others.add(allProducts.get(j));
                    }
                }
                adapter.addFrag(new VegetableFragment(others), productData.getCategory().get(i).getName());
            }

        }


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
