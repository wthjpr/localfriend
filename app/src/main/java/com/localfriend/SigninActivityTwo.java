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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.localfriend.application.MyApp;
import com.localfriend.model.User;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.sql.Types.NULL;

public class SigninActivityTwo extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_btn_signin;
    private EditText edt_password;
    private Toolbar toolbar;
    private String Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_two);
        setResponseListener(this);
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
        Bundle extras = getIntent().getExtras();
        Mobile = extras.getString("mobile").toString();
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
            userLogin();
        }

    }

    private void userLogin() {
        JSONObject o = new JSONObject();
        try {
            o.put("MobileNumber", Mobile);
            o.put("Password", edt_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showLoadingDialog("");
        postCall(getContext(), AppConstant.BASE_URL + "Account/Login", o, "", 1);

    }


    private Context getContext() {
        return SigninActivityTwo.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 1) {
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
                MyApp.popFinishableMessage("Alert!", o.optString("message"), SigninActivityTwo.this);
                return;
            }
            /*Intent intent = new Intent(PhoneVerificationActivity.this, SucessfullLoginActivity.class);
            intent.putExtra("ezy", value);
            startActivity(intent);*/
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error", "Time-out error occurred.\nPlease try again.", getContext());
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
