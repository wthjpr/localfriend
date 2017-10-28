package com.localfriend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    private EditText new_password;
    private EditText confirm_password;
    private EditText old_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_change_password);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Change Password");

        actionBar.setTitle("");
        setTouchNClick(R.id.txt_change_password);
        setContentElements();
    }

    private void setContentElements() {
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        old_password = findViewById(R.id.old_password);

        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
//        txt_contact_us.getPaint().setShader(textShader);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_change_password) {
            if (old_password.getText().toString().isEmpty()) {
                old_password.setError("Enter your old password");
                return;
            }
            if (new_password.getText().toString().isEmpty()) {
                new_password.setError("Enter new password");
                return;
            }
            if (confirm_password.getText().toString().isEmpty()) {
                confirm_password.setError("Enter confirm password");
                return;
            }
            if (!new_password.getText().toString().equals(confirm_password.getText().toString())) {
                MyApp.popMessage("Alert", "Password does not match", ChangePasswordActivity.this);
                return;
            }
            JSONObject o = new JSONObject();
            try {
                o.put("OldPassword", old_password.getText().toString());
                o.put("NewPassword", new_password.getText().toString());
                o.put("ConfirmPassword", confirm_password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showLoadingDialog("");
            postCallJsonWithAuthorization(ChangePasswordActivity.this, AppConstant.BASE_URL + "Account/ChangePassword", o, 1);
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (o.optString("status").equals("Success")) {
            MyApp.showMassage(ChangePasswordActivity.this, o.optString("message") + "\nLogin again.");
            startActivity(new Intent(ChangePasswordActivity.this, SigninActivityOne.class));
            finishAffinity();
        } else {
            MyApp.popMessage("Local Friend", o.optString("message"), ChangePasswordActivity.this);
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.showMassage(ChangePasswordActivity.this, "Time-out error");
    }

    @Override
    public void onErrorReceived(String error) {
        MyApp.showMassage(ChangePasswordActivity.this, error);
        dismissDialog();
    }
}

