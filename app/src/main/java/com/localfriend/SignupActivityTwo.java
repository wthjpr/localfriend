package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static java.sql.Types.NULL;

public class SignupActivityTwo extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_btn_signin;
    private TextView tv_welcome;
    private EditText edt_phone_no;
    private Toolbar toolbar;
    private String UserName;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_two);
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
        setResponseListener(this);

        // UserName= getIntent().getStringExtra("name");
        Bundle extras = getIntent().getExtras();
        UserName = extras.getString("name").toString();
        setupUiElement();
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_signin);


        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);

        tv_btn_signin = (TextView) findViewById(R.id.tv_btn_signin);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome);
        tv_welcome.setText("Welcome "+UserName+"\nEnter your Mobile Number");


    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_signin) {
            if (TextUtils.isEmpty(edt_phone_no.getText().toString())) {
                edt_phone_no.setError("Enter Your Mobile Number");
                return;
            }

            phoneVerification();
        }

    }

    private void phoneVerification() {
        JSONObject o = new JSONObject();

        try {
            o.put("Id", "");
            o.put("username", edt_phone_no.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postCall(getContext(), AppConstant.BASE_URL + "Account/mobilenumberverify", o, "Verifying your Number", 1);
    }


    private Context getContext() {
        return SignupActivityTwo.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            if (o.optString("status").equals("success")) {
                try {
                    Intent intent = new Intent(getContext(), SignupActivityThree.class);
                    intent.putExtra("name", UserName);
                    intent.putExtra("phone", edt_phone_no.getText().toString());
                    startActivity(intent);
                } catch (JsonSyntaxException ee) {

                }
            } else {
                MyApp.popFinishableMessage("Alert!", o.optString("message"), SignupActivityTwo.this);
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

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
