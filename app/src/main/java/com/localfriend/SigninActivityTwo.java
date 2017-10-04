package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SigninActivityTwo extends CustomActivity {
    private TextView tv_btn_signin;
    private ImageButton img_btn_back;
    private EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_two);
        setupUiElement();

    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signin);
        setTouchNClick(R.id.img_btn_back);


        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_btn_signin = (TextView) findViewById(R.id.tv_btn_signin);

        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter the Password");
                return;
            }
            startActivity(new Intent(getContext(), MainActivity.class));
        } else if (v.getId() == R.id.tv_btn_next) {
            startActivity(new Intent(getContext(), SigninActivityOne.class));
        }

    }

    private Context getContext() {
        return SigninActivityTwo.this;
    }

}
