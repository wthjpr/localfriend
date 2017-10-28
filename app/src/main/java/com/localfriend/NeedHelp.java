package com.localfriend;

import android.content.Context;
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

public class NeedHelp extends CustomActivity implements CustomActivity.ResponseCallback {

    private TextView txt_contact_us, txt_leave_question;
    private Toolbar toolbar;
    private EditText edt_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_help);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Help");

        actionBar.setTitle("");
        setTouchNClick(R.id.txt_submit);
        setContentElements();
    }

    private void setContentElements() {
        txt_contact_us = findViewById(R.id.txt_contact_us);
        txt_leave_question = findViewById(R.id.txt_leave_question);
        edt_comment = findViewById(R.id.edt_comment);
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        txt_contact_us.getPaint().setShader(textShader);
        txt_leave_question.getPaint().setShader(textShader);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_submit) {
            if (edt_comment.getText().toString().isEmpty()) {
                edt_comment.setError("Please write a query.");
                return;
            }
            JSONObject o = new JSONObject();
            try {
                o.put("description", edt_comment.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showLoadingDialog("");
            postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "NeedHelp", o, 1);
//            MyApp.showMassage(NeedHelp.this, "Question sent successfully.");
//            finish();
        }
    }

    private Context getContext() {
        return NeedHelp.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        MyApp.popMessage("Local Friend", o.optString("message"), getContext());
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
    }
}

