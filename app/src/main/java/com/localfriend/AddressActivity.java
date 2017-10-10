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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.fragments.AddressFragment;
import com.localfriend.fragments.ScheduleFragment;

public class AddressActivity extends CustomActivity {
    private Toolbar toolbar;
    private RadioButton rd_btn_home, rd_btn_office, rd_btn_other;
    //private TextView tv_make_payment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Address");
        actionBar.setTitle("");
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.service_container, new AddressFragment()).commit();
        setupUiElements();
    }

    private void setupUiElements() {


        rd_btn_home = (RadioButton) findViewById(R.id.rd_btn_home);
        rd_btn_office = (RadioButton) findViewById(R.id.rd_btn_office);
        rd_btn_other = (RadioButton) findViewById(R.id.rd_btn_other);

        //  tv_make_payment = (TextView) findViewById(R.id.tv_make_payment);

        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);

        rd_btn_home.setSelected(true);
        rd_btn_home.setChecked(true);
        rd_btn_home.setTextColor(getResources().getColor(R.color.blue_text));
    }


    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {

            rd_btn_home.setChecked(true);
            rd_btn_home.setTextColor(getResources().getColor(R.color.blue_text));
            rd_btn_office.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_other.setChecked(false);
            rd_btn_other.setTextColor(getResources().getColor(R.color.black));


            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new AddressFragment()).commit();

        } else if (v.getId() == R.id.rl_tab_2) {
            rd_btn_home.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_office.setChecked(true);
            rd_btn_home.setTextColor(getResources().getColor(R.color.blue_text));
            rd_btn_other.setChecked(false);
            rd_btn_other.setTextColor(getResources().getColor(R.color.black));


            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new AddressFragment()).commit();

        } else if (v.getId() == R.id.rl_tab_3) {
            rd_btn_home.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_office.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_other.setChecked(true);
            rd_btn_other.setTextColor(getResources().getColor(R.color.blue_text));


            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new AddressFragment()).commit();


        } /*else if (v.getId() == R.id.tv_make_payment) {

           startActivity(new Intent(CheckOutActivity.this, FoodActivity.class));


        }*/
    }

}
