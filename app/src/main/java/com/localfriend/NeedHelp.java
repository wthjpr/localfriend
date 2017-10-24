package com.localfriend;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

public class NeedHelp extends CustomActivity {

    private TextView txt_contact_us, txt_leave_question;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("HELP");

        actionBar.setTitle("");
        setTouchNClick(R.id.txt_submit);
        setContentElements();
    }

    private void setContentElements() {
        txt_contact_us = findViewById(R.id.txt_contact_us);
        txt_leave_question = findViewById(R.id.txt_leave_question);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        txt_contact_us.getPaint().setShader(textShader);
        txt_leave_question.getPaint().setShader(textShader);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_submit) {
            MyApp.showMassage(NeedHelp.this,"Question sent successfully.");
            finish();
        }
    }
}

