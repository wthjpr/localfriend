package com.localfriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class LoginSignupActivity extends CustomActivity {
private TextView tv_already_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        tv_already_account=(TextView)findViewById(R.id.tv_already_account);
        tv_already_account.setText(Html.fromHtml("<u>Already have an account ? </u>"));
        tv_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignupActivity.this, SigninActivityOne.class));
            }
        });
    }
}
