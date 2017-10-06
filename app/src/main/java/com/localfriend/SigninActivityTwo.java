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

public class SigninActivityTwo extends CustomActivity {
    private TextView tv_btn_signin;
    private EditText edt_password;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_two);
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

        setTouchNClick(R.id.tv_btn_signin);



        edt_password = (EditText) findViewById(R.id.edt_password);

        tv_btn_signin = (TextView) findViewById(R.id.tv_btn_signin);


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            if (TextUtils.isEmpty(edt_password.getText().toString())) {
                edt_password.setError("Enter the Password");
                return;
            }
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    }

    private Context getContext() {
        return SigninActivityTwo.this;
    }

}
