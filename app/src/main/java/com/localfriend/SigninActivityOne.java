package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SigninActivityOne extends CustomActivity {
    private ImageButton img_btn_back;
    private EditText edt_cust_mobile;
    private TextView tv_btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_one);
        setupUiElement();
    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_next);
        setTouchNClick(R.id.img_btn_back);


        edt_cust_mobile = (EditText) findViewById(R.id.edt_cust_mobile);

        tv_btn_next = (TextView) findViewById(R.id.tv_btn_next);

        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_next) {
            if (TextUtils.isEmpty(edt_cust_mobile.getText().toString())) {
                edt_cust_mobile.setError("Enter Mobile Number");
                return;
            }
            startActivity(new Intent(getContext(), SigninActivityTwo.class));
        }else if(v.getId()== R.id.tv_btn_next){
            startActivity(new Intent(getContext(), LoginSignupActivity.class));
        }

    }

    private Context getContext() {
        return SigninActivityOne.this;
    }
}
