package com.localfriend;

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
import android.widget.TextView;

import com.localfriend.fragments.CurrentSubscriptionFragment;
import com.localfriend.fragments.HistorySubscriptionFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderSubscriptionsActivity extends CustomActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Subscriptions");
        actionBar.setTitle("");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
    }

    private void createTabIcons() {

        final TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Current");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        final TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("History");
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabOne.setTextColor(Color.parseColor("#0891CF"));
        tabTwo.setTextColor(Color.parseColor("#000000"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabOne.setTextColor(Color.parseColor("#0891CF"));
                    tabTwo.setTextColor(Color.parseColor("#000000"));
                } else if (tab.getPosition() == 1) {
                    tabOne.setTextColor(Color.parseColor("#000000"));
                    tabTwo.setTextColor(Color.parseColor("#0891CF"));
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

//    private CurrentFragment currentFragment;
//    private CurrentFragment HistoryFragment;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CurrentSubscriptionFragment(), "Current");
        adapter.addFrag(new HistorySubscriptionFragment(), "History");
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


}
