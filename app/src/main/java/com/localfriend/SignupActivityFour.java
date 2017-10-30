package com.localfriend;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.localfriend.application.MyApp;
import com.localfriend.model.User;
import com.localfriend.utils.AppConstant;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.sql.Types.NULL;

public class SignupActivityFour extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_btn_signup;
    private EditText signup_edt_password;
    private Toolbar toolbar;
    private String userName;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_four);
        setResponseListener(this);
        toolbar =  findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle =  toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("SIGN UP");
        actionBar.setTitle("");
        toolbar.setBackgroundResource(NULL);

        userName = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");
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

        setTouchNClick(R.id.tv_btn_signup);

        signup_edt_password =  findViewById(R.id.signup_edt_password);

        tv_btn_signup =  findViewById(R.id.tv_btn_signup);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv_btn_signup.getPaint().setShader(textShader);

    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signup) {
            if (TextUtils.isEmpty(signup_edt_password.getText().toString())) {
                signup_edt_password.setError("Enter Your Password");
                return;
            }
            registerUser();
            // startActivity(new Intent(getContext(), LoginSignupActivity.class));
        }

    }


    private void registerUser() {
        JSONObject o = new JSONObject();

        try {
            o.put("FullName", userName);
            o.put("Email", "");
            o.put("MobileNumber", phoneNumber);
            o.put("Password", signup_edt_password.getText().toString());
            o.put("ConfirmPassword", signup_edt_password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showLoadingDialog("");
        postCall(getContext(), AppConstant.BASE_URL + "Account/Register", o, "", 1);
    }


    private void phVerification() {

//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.signup_dialog);
//
//
//        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);
//
//        tv_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishAffinity();
//                startActivity(new Intent(getContext(), LoginSignupActivity.class));
//            }
//        });
//
//
//        dialog.show();

    }


    private Context getContext() {
        return SignupActivityFour.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            dismissDialog();
            if (o.optString("status").equals("success")) {
                try {
//                    phVerification();
                    userLogin();
                } catch (JsonSyntaxException ee) {

                }
            } else {
                MyApp.popFinishableMessage("Alert!", o.optString("message"), SignupActivityFour.this);
                return;
            }

        } else if(callNumber == 2){
            if (o.optString("status").equals("Success")) {
                try {
                    MyApp.setStatus(AppConstant.IS_LOGIN, true);
                    User u = new Gson().fromJson(o.toString(), User.class);
                    MyApp.getApplication().writeUser(u);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } catch (JsonSyntaxException ee) {

                }
            } else {
                MyApp.popFinishableMessage("Alert!", o.optString("message"), SignupActivityFour.this);
                return;
            }
            /*Intent intent = new Intent(PhoneVerificationActivity.this, SucessfullLoginActivity.class);
            intent.putExtra("ezy", value);
            startActivity(intent);*/
        }

    }

    private void userLogin() {
        JSONObject o = new JSONObject();
        try {
            o.put("MobileNumber", phoneNumber);
            o.put("Password", signup_edt_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showLoadingDialog("");
        postCall(getContext(), AppConstant.BASE_URL + "Account/Login", o, "", 2);

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error", "Time-out error occurred. Please try again.", getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
