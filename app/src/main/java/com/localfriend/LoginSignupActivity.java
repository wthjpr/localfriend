package com.localfriend;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginSignupActivity extends CustomActivity {
    private TextView tv_already_account, tv_lets_get_started, tv_hello_label, tv_help;
    private static final int SPLASH_DURATION_MS = 1000;
    private static final int SPLASH_DURATION_two = 2000;
    private static final int SPLASH_DURATION_three = 3000;
    private static final int SPLASH_DURATION_four = 4000;
    private static final int SPLASH_DURATION_five = 5000;
    private Handler mHandler = new Handler();
    private ImageView img_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        img_logo = (ImageView) findViewById(R.id.img_logo);
        img_logo.setVisibility(View.GONE);
        tv_already_account = (TextView) findViewById(R.id.tv_already_account);
        tv_already_account.setVisibility(View.GONE);
        tv_already_account.setText(Html.fromHtml("<u>Already have an account ? </u>"));
        tv_lets_get_started = (TextView) findViewById(R.id.tv_lets_get_started);
        tv_lets_get_started.setVisibility(View.GONE);
        tv_hello_label = (TextView) findViewById(R.id.tv_hello_label);
        tv_hello_label.setVisibility(View.GONE);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_help.setVisibility(View.GONE);
        tv_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignupActivity.this, SigninActivityOne.class));
            }
        });
        tv_lets_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginSignupActivity.this, SignupActivityOne.class));
            }
        });

        mHandler.postDelayed(new Runnable() {


            @Override
            public void run() {

                img_logo.setVisibility(View.VISIBLE);

            }
        }, SPLASH_DURATION_MS);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_hello_label.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_two);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_help.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_three);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_lets_get_started.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_four);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_already_account.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_five);


    }
}
