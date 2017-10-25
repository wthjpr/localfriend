package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.localfriend.application.MyApp;

public class OrderPlacedActivity extends CustomActivity {

    private TextView txt_go_shopping, txt_track_order;
//    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
//        toolbar = findViewById(R.id.toolbar_common);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
//        mTitle.setText("HELP");
//
//        actionBar.setTitle("");
        setTouchNClick(R.id.txt_go_shopping);
        setTouchNClick(R.id.txt_track_order);
        setContentElements();
    }

    private void setContentElements() {
        txt_track_order = findViewById(R.id.txt_track_order);
        txt_go_shopping = findViewById(R.id.txt_go_shopping);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        txt_go_shopping.getPaint().setShader(textShader);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_track_order) {
            startActivity(new Intent(getContext(), MainActivity.class));
            finishAffinity();
        } else if (v == txt_go_shopping) {
            startActivity(new Intent(getContext(), MainActivity.class));
            finishAffinity();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getContext(), MainActivity.class));
        finishAffinity();
    }

    private Context getContext() {
        return OrderPlacedActivity.this;
    }
}

