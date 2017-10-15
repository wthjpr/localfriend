package com.localfriend;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.AllFragment;
import com.localfriend.fragments.CartFragment;
import com.localfriend.model.Product;
import com.localfriend.model.ProductData;
import com.localfriend.model.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class AllActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProductData productData;
    private List<ProductDetails> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productData = SingleInstance.getInstance().getProductData();
        setContentView(R.layout.activity_all);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("All");
        actionBar.setTitle("");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        for (int i = 0; i < productData.getProduct().size(); i++) {
            for (int j = 0; j < productData.getProduct().get(i).getpDetailsList().size(); j++) {
                ProductDetails d = productData.getProduct().get(i).getpDetailsList().get(j);
                d.setpGalleryFileList(productData.getProduct().get(i).getpGalleryFileList());
                d.setName(productData.getProduct().get(i).getpName());
                allProducts.add(d);
            }
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllFragment(allProducts), "All");
        if (productData.getCategory().size() > 0) {
            for (int i = 0; i < productData.getCategory().size(); i++) {
                adapter.addFrag(new AllFragment(allProducts), productData.getCategory().get(i).getName());
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

}
