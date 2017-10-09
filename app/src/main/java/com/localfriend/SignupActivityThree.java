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
import android.widget.TextView;

import static java.sql.Types.NULL;

public class SignupActivityThree extends CustomActivity {
    private TextView tv_btn_next;
    private EditText edt_code;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_three);
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

        setTouchNClick(R.id.tv_btn_next);

        edt_code = (EditText) findViewById(R.id.edt_code);

        tv_btn_next = (TextView) findViewById(R.id.tv_btn_next);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_next) {
            if (TextUtils.isEmpty(edt_code.getText().toString())) {
                edt_code.setError("Enter the verification code");
                return;
            }
            startActivity(new Intent(getContext(), SignupActivityFour.class));
        }

    }

    private Context getContext() {
        return SignupActivityThree.this;
    }

}
