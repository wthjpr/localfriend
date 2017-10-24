package com.localfriend;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.fragments.AddressFragment;
import com.localfriend.fragments.AllFragment;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.HomeFragment;
import com.localfriend.fragments.PaymentFragment;
import com.localfriend.fragments.ScheduleFragment;
import com.localfriend.fragments.TiffinFragment;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class CheckOutActivity extends CustomActivity {
    private Toolbar toolbar;
    private TextView tv_address, tv_payment, tv_review;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Checkout");
        actionBar.setTitle("");
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.replace(R.id.service_container, new ScheduleFragment()).commit();

//        setupUiElements();


    }

    private void createTabIcons() {

        final TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Schedule");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_schdule, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        final TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Payment");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_payment_blue, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        final TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Review");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_done_blue, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        tabOne.setTextColor(Color.parseColor("#0891CF"));
        tabTwo.setTextColor(Color.parseColor("#000000"));
        tabThree.setTextColor(Color.parseColor("#000000"));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_schdule, 0);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_payment_gray, 0);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_done_gray, 0);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabOne.setTextColor(Color.parseColor("#0891CF"));
                    tabTwo.setTextColor(Color.parseColor("#000000"));
                    tabThree.setTextColor(Color.parseColor("#000000"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_schdule, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_payment_gray, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_done_gray, 0);
                } else if (tab.getPosition() == 1) {
                    tabOne.setTextColor(Color.parseColor("#000000"));
                    tabTwo.setTextColor(Color.parseColor("#0891CF"));
                    tabThree.setTextColor(Color.parseColor("#000000"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_schedule_gray, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_payment_blue, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_done_gray, 0);
                } else {
                    tabOne.setTextColor(Color.parseColor("#000000"));
                    tabTwo.setTextColor(Color.parseColor("#000000"));
                    tabThree.setTextColor(Color.parseColor("#0891CF"));
                    tabOne.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_schedule_gray, 0);
                    tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_payment_gray, 0);
                    tabThree.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkout_done_blue, 0);
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
        adapter.addFrag(new ScheduleFragment(), "Review");
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

//    private void setupUiElements() {
//
//
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        tv_payment = (TextView) findViewById(R.id.tv_payment);
//        tv_review = (TextView) findViewById(R.id.tv_review);
//
//      //  tv_make_payment = (TextView) findViewById(R.id.tv_make_payment);
//
//        setClick(R.id.rl_tab_1);
//        setClick(R.id.rl_tab_2);
//        setClick(R.id.rl_tab_3);
//        //setClick(R.id.tv_make_payment);
//
//
//        tv_address.setSelected(true);
//        tv_address.setTextColor(Color.parseColor("#19598C"));
//
//    }

//    public void onClick(View v) {
//        super.onClick(v);
//        if (v.getId() == R.id.rl_tab_1) {
//
//            tv_address.setSelected(true);
//            tv_payment.setSelected(false);
//            tv_review.setSelected(false);
//
//
//
//
//
//
//      /*      tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_active, 0, 0);
//            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
//            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
//            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/
//
//
//            tv_address.setTextColor(Color.parseColor("#19598C"));
//            tv_payment.setTextColor(Color.parseColor("#40B799"));
//            tv_review.setTextColor(Color.parseColor("#40B799"));
//            mFragmentManager = getSupportFragmentManager();
//            mFragmentTransaction = mFragmentManager.beginTransaction();
//            mFragmentTransaction.replace(R.id.service_container, new ScheduleFragment()).commit();
//
//        } else if (v.getId() == R.id.rl_tab_2) {
//            tv_address.setSelected(true);
//            tv_payment.setSelected(false);
//            tv_review.setSelected(false);
//
//          /*  mFragmentManager = getSupportFragmentManager();
//            mFragmentTransaction = mFragmentManager.beginTransaction();
//            mFragmentTransaction.replace(R.id.service_container, new TiffinFragment()).commit();*/
//          /*  tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
//            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
//            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_active, 0, 0);
//            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/
//
//            tv_address.setTextColor(Color.parseColor("#40B799"));
//            tv_payment.setTextColor(Color.parseColor("#19598C"));
//            tv_review.setTextColor(Color.parseColor("#40B799"));
//
//
//            mFragmentManager = getSupportFragmentManager();
//            mFragmentTransaction = mFragmentManager.beginTransaction();
//            mFragmentTransaction.replace(R.id.service_container, new PaymentFragment()).commit();
//
//        } else if (v.getId() == R.id.rl_tab_3) {
//            tv_address.setSelected(true);
//            tv_payment.setSelected(false);
//            tv_review.setSelected(false);
//
//
//
//           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
//            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_active, 0, 0);
//            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
//            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/
//
//
//            tv_address.setTextColor(Color.parseColor("#40B799"));
//            tv_payment.setTextColor(Color.parseColor("#40B799"));
//            tv_review.setTextColor(Color.parseColor("#19598C"));
//
//
//        } /*else if (v.getId() == R.id.tv_make_payment) {
//
//           startActivity(new Intent(CheckOutActivity.this, FoodActivity.class));
//
//
//        }*/
//    }


}
