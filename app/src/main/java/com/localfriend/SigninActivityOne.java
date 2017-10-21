package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.sql.Types.NULL;

public class SigninActivityOne extends CustomActivity {
    private EditText edt_cust_mobile;
    private TextView tv_btn_next;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_one);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("SIGN IN");
        actionBar.setTitle("");
        toolbar.setBackgroundResource(NULL);
        setupUiElement();
    }


    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_next);


        edt_cust_mobile = findViewById(R.id.edt_cust_mobile);

        tv_btn_next = findViewById(R.id.tv_btn_next);
        Shader textShader=new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"),Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv_btn_next.getPaint().setShader(textShader);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_next) {
            if (TextUtils.isEmpty(edt_cust_mobile.getText().toString())) {
                edt_cust_mobile.setError("Enter Mobile Number");
                return;
            }
            if (edt_cust_mobile.getText().toString().length() < 10) {
                edt_cust_mobile.setError("Please enter a valid mobile number");
                return;
            }
            Intent intent = new Intent(getContext(), SigninActivityTwo.class);
            intent.putExtra("mobile", edt_cust_mobile.getText().toString());
            startActivity(intent);
        }

    }

    private Context getContext() {
        return SigninActivityOne.this;
    }
}
