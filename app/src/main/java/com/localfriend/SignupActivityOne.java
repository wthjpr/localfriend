package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.application.MyApp;

import static java.sql.Types.NULL;

public class SignupActivityOne extends CustomActivity {
    private EditText edt_cust_name;
    private TextView tv_btn_next;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        toolbar =  findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("SIGN UP");
        actionBar.setTitle("");
        toolbar.setBackgroundResource(NULL);
        RelativeLayout v = (RelativeLayout) findViewById(R.id.toolbar);
        // transparent statusbar for marshmallow and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (v != null) {
                v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_next);



        edt_cust_name = findViewById(R.id.edt_cust_name);

        tv_btn_next = findViewById(R.id.tv_btn_next);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv_btn_next.getPaint().setShader(textShader);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_next) {
            if (TextUtils.isEmpty(edt_cust_name.getText().toString())) {
                edt_cust_name.setError("Enter Your Name");
                return;
            }

            Intent intent = new Intent(getContext(), SignupActivityTwo.class);
            intent.putExtra("name", edt_cust_name.getText().toString());
            startActivity(intent);
        }

    }

    private Context getContext() {
        return SignupActivityOne.this;
    }
}
