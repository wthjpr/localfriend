package com.localfriend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.PaymentFragment;
import com.localfriend.fragments.ReviewFragment;
import com.localfriend.fragments.ScheduleFragment;
import com.localfriend.model.Checkout;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_check_out);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Checkout");
        actionBar.setTitle("");
        viewPager = findViewById(R.id.viewpager);


        tabLayout = findViewById(R.id.tabs);

        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "CheckOut", 1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }


    private void createTabIcons() {

        final TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Schedule");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_schdule, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        final TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Payment");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_payment_blue, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        final TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Review");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_done_blue, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        tabOne.setTextColor(Color.parseColor("#166DB6"));
        tabTwo.setTextColor(Color.parseColor("#aaaaaa"));
        tabThree.setTextColor(Color.parseColor("#aaaaaa"));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_schdule, 0, 0, 0);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_payment_gray, 0, 0, 0);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_done_gray, 0, 0, 0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabOne.setTextColor(Color.parseColor("#166DB6"));
                    tabTwo.setTextColor(Color.parseColor("#aaaaaa"));
                    tabThree.setTextColor(Color.parseColor("#aaaaaa"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_schdule, 0, 0, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_payment_gray, 0, 0, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_done_gray, 0, 0, 0);
                } else if (tab.getPosition() == 1) {
                    tabOne.setTextColor(Color.parseColor("#aaaaaa"));
                    tabTwo.setTextColor(Color.parseColor("#166DB6"));
                    tabThree.setTextColor(Color.parseColor("#aaaaaa"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_schedule_gray, 0, 0, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_payment_blue, 0, 0, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_done_gray, 0, 0, 0);
                } else {
                    tabOne.setTextColor(Color.parseColor("#aaaaaa"));
                    tabTwo.setTextColor(Color.parseColor("#aaaaaa"));
                    tabThree.setTextColor(Color.parseColor("#166DB6"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_schedule_gray, 0, 0, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_payment_gray, 0, 0, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkout_done_blue, 0, 0, 0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ScheduleFragment(), "Schedule");
        adapter.addFrag(new PaymentFragment(), "Payment");
        adapter.addFrag(new ReviewFragment(), "Review");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 1) {
            try {
                Checkout checkoutData = new Gson().fromJson(o.getJSONObject("data").toString(), Checkout.class);
                SingleInstance.getInstance().setCheckoutData(checkoutData);
                setupViewPager(viewPager);

                tabLayout.setupWithViewPager(viewPager);
                createTabIcons();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

     public void changeFragmentPosition(int position){
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        tab.select();
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        MyApp.popMessage("Error", "Time-out error occurred, please try again.", getContext());
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        MyApp.popMessage("Error", error, getContext());
        dismissDialog();
    }

    private Context getContext() {
        return CheckOutActivity.this;
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

}
