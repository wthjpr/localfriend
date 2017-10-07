package com.localfriend;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends CustomActivity {
    private Toolbar toolbar;
    private TextView tv_start_shopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Orders");
        actionBar.setTitle("");
        tv_start_shopping=(TextView)findViewById(R.id.tv_start_shopping);
        tv_start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderActivity.this, AllActivity.class));

            }
        });

    }
}
