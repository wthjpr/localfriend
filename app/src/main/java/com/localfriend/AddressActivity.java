package com.localfriend;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddressActivity extends CustomActivity {
    private Toolbar toolbar;
    private TextView tv_add_address;
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
        tv_add_address=(TextView)findViewById(R.id.tv_add_address);
        tv_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AddressActivity.this, " Add new Address", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
