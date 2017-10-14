package com.localfriend;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.localfriend.application.MyApp;
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

        userName = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");
        setupUiElement();
    }
    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signup);

        signup_edt_password = (EditText) findViewById(R.id.signup_edt_password);

        tv_btn_signup = (TextView) findViewById(R.id.tv_btn_signup);


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
        postCall(getContext(), AppConstant.BASE_URL + "Account/Register", o, "Registering...", 1);
    }


    private void phVerification() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.signup_dialog);


        TextView tv_ok = (TextView) dialog.findViewById(R.id.tv_ok);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginSignupActivity.class));
            }
        });


        dialog.show();

    }


    private Context getContext() {
        return SignupActivityFour.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("success")) {
                try {
                    phVerification();
                } catch (JsonSyntaxException ee) {

                }
            } else {
                MyApp.popFinishableMessage("Alert!", o.optString("message"), SignupActivityFour.this);
                return;
            }

        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
