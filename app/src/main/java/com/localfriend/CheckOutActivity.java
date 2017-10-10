package com.localfriend;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.fragments.AddressFragment;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.HomeFragment;
import com.localfriend.fragments.ScheduleFragment;
import com.localfriend.fragments.TiffinFragment;

import static java.sql.Types.NULL;

public class CheckOutActivity extends CustomActivity {
    private Toolbar toolbar;
    private TextView tv_address, tv_payment, tv_review;
    //private TextView tv_make_payment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
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

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.service_container, new ScheduleFragment()).commit();

        setupUiElements();


    }


    private void setupUiElements() {


        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_payment = (TextView) findViewById(R.id.tv_payment);
        tv_review = (TextView) findViewById(R.id.tv_review);

      //  tv_make_payment = (TextView) findViewById(R.id.tv_make_payment);

        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        //setClick(R.id.tv_make_payment);


        tv_address.setSelected(true);
        tv_address.setTextColor(Color.parseColor("#19598C"));

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {

            tv_address.setSelected(true);
            tv_payment.setSelected(false);
            tv_review.setSelected(false);






      /*      tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_active, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


            tv_address.setTextColor(Color.parseColor("#19598C"));
            tv_payment.setTextColor(Color.parseColor("#40B799"));
            tv_review.setTextColor(Color.parseColor("#40B799"));
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new ScheduleFragment()).commit();

        } else if (v.getId() == R.id.rl_tab_2) {
            tv_address.setSelected(true);
            tv_payment.setSelected(false);
            tv_review.setSelected(false);






          /*  mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new TiffinFragment()).commit();*/
          /*  tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_active, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/

            tv_address.setTextColor(Color.parseColor("#40B799"));
            tv_payment.setTextColor(Color.parseColor("#19598C"));
            tv_review.setTextColor(Color.parseColor("#40B799"));


            //startActivity(new Intent(MainActivity.this, AllServicesActivity.class));

        } else if (v.getId() == R.id.rl_tab_3) {
            tv_address.setSelected(true);
            tv_payment.setSelected(false);
            tv_review.setSelected(false);



           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_active, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


            tv_address.setTextColor(Color.parseColor("#40B799"));
            tv_payment.setTextColor(Color.parseColor("#40B799"));
            tv_review.setTextColor(Color.parseColor("#19598C"));


        } /*else if (v.getId() == R.id.tv_make_payment) {

           startActivity(new Intent(CheckOutActivity.this, FoodActivity.class));


        }*/
    }

}
