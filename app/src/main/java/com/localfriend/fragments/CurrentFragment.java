package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.CheckOutActivity;
import com.localfriend.CustomActivity;
import com.localfriend.R;
import com.localfriend.adapter.CurrentAdapter;
import com.localfriend.model.Address;
import com.localfriend.model.History;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private RelativeLayout rl_empty;
    private RecyclerView rv_history;
    private TextView tv_start_shopping;
    private CurrentAdapter adapter;

    public CurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current, container, false);
        rl_empty = v.findViewById(R.id.rl_empty);
        rv_history = v.findViewById(R.id.rv_history);
        tv_start_shopping = v.findViewById(R.id.tv_start_shopping);

        setTouchNClick(tv_start_shopping);

        setResponseListener(this);
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Order/Current", 1);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tv_start_shopping) {
            getActivity().finish();
        }
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        Type listType = new TypeToken<ArrayList<History>>() {
        }.getType();
        try {
            List<History> historyList =
                    new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
            if (historyList.size() > 0) {
                rl_empty.setVisibility(View.GONE);
                rv_history.setVisibility(View.VISIBLE);
                rv_history.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new CurrentAdapter(historyList, getActivity());
                rv_history.setAdapter(adapter);
            } else {
                rl_empty.setVisibility(View.VISIBLE);
                rv_history.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
