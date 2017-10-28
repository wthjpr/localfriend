package com.localfriend.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localfriend.ChangePasswordActivity;
import com.localfriend.FeedbackActivity;
import com.localfriend.MoreActivity;
import com.localfriend.ProfileActivity;
import com.localfriend.R;
import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;


public class MoreFragment extends CustomFragment implements View.OnClickListener, CustomFragment.ResponseCallback {

    private TextView txt_feedback, txt_change_password, txt_tc, txt_profile, txt_about_us;

    public MoreFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_more, container, false);
        if (Build.VERSION.SDK_INT >= 21) {
            LinearLayout v = myView.findViewById(R.id.ll_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 3 * MyApp.getApplication().getStatusBarHeight(), 0, 0);
        }
        txt_feedback = myView.findViewById(R.id.txt_feedback);
        txt_change_password = myView.findViewById(R.id.txt_change_password);
        txt_tc = myView.findViewById(R.id.txt_tc);
        txt_profile = myView.findViewById(R.id.txt_profile);
        txt_about_us = myView.findViewById(R.id.txt_about_us);

        setTouchNClick(txt_feedback);
        setTouchNClick(txt_change_password);
        setTouchNClick(txt_tc);
        setTouchNClick(txt_profile);
        setTouchNClick(txt_about_us);
        return myView;
    }


    @Override
    public void onClick(View v) {
        if (v == txt_about_us) {
            startActivity(new Intent(getContext(), MoreActivity.class).putExtra(AppConstant.EXTRA_1, 1));
        } else if (v == txt_profile) {
            startActivity(new Intent(getContext(), ProfileActivity.class));
        } else if (v == txt_tc) {
            startActivity(new Intent(getContext(), MoreActivity.class).putExtra(AppConstant.EXTRA_1, 2));
        } else if (v == txt_change_password) {
            startActivity(new Intent(getContext(), ChangePasswordActivity.class).putExtra(AppConstant.EXTRA_1, 2));
        } else if (v == txt_feedback) {
            startActivity(new Intent(getActivity(), FeedbackActivity.class));
        }
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        MyApp.popMessage("Error", "Time-out error occurred. Please try again.", getContext());
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }

}
