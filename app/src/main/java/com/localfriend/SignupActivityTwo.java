package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.sql.Types.NULL;

public class SignupActivityTwo extends CustomActivity {
    private TextView tv_btn_signin;

    private EditText edt_phone_no;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_two);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("SIGN UP");
        actionBar.setTitle("");
        toolbar.setBackgroundResource(NULL);
        setupUiElement();
    }
    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signin);



        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);

        tv_btn_signin = (TextView) findViewById(R.id.tv_btn_signin);


    }
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            if (TextUtils.isEmpty(edt_phone_no.getText().toString())) {
                edt_phone_no.setError("Enter Your Mobile Number");
                return;
            }
            startActivity(new Intent(getContext(), SignupActivityThree.class));
        }

    }

    private Context getContext() {
        return SignupActivityTwo.this;
    }
}
