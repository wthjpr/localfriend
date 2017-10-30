package com.localfriend;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ProfileActivity extends CustomActivity implements CustomActivity.ResponseCallback, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView txt_date;
    private TextView txt_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
//        mTitle.setText("Profile");
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Profile");
        collapsingToolbarLayout.setCollapsedTitleGravity(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);
        collapsingToolbarLayout.setScrimsShown(true, true);
        actionBar.setTitle("");
        setContentElements();
        toolbarTextAppearance();

    }

    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    private void setContentElements() {
        txt_date = findViewById(R.id.txt_date);
        txt_date = findViewById(R.id.txt_logout);
        setTouchNClick(R.id.txt_date);
        setTouchNClick(R.id.txt_logout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_date) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), this, year, month, day);

        } else if(v==txt_logout){
            MyApp.setStatus(AppConstant.IS_LOGIN, false);
            startActivity(new Intent(getContext(), LoginSignupActivity.class));
            finishAffinity();
        }
    }

    private Context getContext() {
        return ProfileActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        MyApp.popMessage("Local Friend", o.optString("message"), getContext());
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txt_date.setText(dayOfMonth + "-" + month + "-" + year);
    }
}

