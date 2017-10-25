package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.adapter.AddressAdapter;
import com.localfriend.adapter.DummyCartData;
import com.localfriend.adapter.DummyCartItem;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Address;
import com.localfriend.model.CategoryDetails;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private RecyclerView rv_addresses;
    private AddressAdapter adapter;
    private TextView txt_add_new;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_list_activity);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Address");
        actionBar.setTitle("");
        setupUiElements();
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Address", 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SingleInstance.getInstance().isUpdateDone()) {
            showLoadingDialog("");
            SingleInstance.getInstance().setUpdateDone(false);
            getCallWithHeader(AppConstant.BASE_URL + "Address", 1);
        }
    }

    private void setupUiElements() {
        rv_addresses = findViewById(R.id.rv_addresses);
        txt_add_new = findViewById(R.id.txt_add_new);
        rv_addresses.setLayoutManager(new LinearLayoutManager(getContext()));

        setTouchNClick(R.id.txt_add_new);
    }

    private Context getContext() {
        return AddressListActivity.this;
    }


    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_add_new) {
            startActivity(new Intent(getContext(), AddressActivity.class).putExtra(AppConstant.EXTRA_1, false));
        }
    }

    public void setEditClick(Address address) {
        SingleInstance.getInstance().setUpdatingAddress(address);
        startActivity(new Intent(getContext(), AddressActivity.class).putExtra(AppConstant.EXTRA_1, true));
    }

    public void setDeleteClick(Address address) {
        showLoadingDialog("");
        deleteCall(getContext(), AppConstant.BASE_URL + "Address/" + address.getAddID(), 2);
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 2) {
            MyApp.showMassage(getContext(), o.optString("message"));
        }
        Type listType = new TypeToken<ArrayList<Address>>() {
        }.getType();
        try {
            List<Address> addressList =
                    new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
            if (addressList.size() == 0) {
                MyApp.popMessage("Local Friend", "No address found, please add a new address.\nThank you", getContext());
            } else {
                adapter = new AddressAdapter(addressList, getContext());
                rv_addresses.setAdapter(adapter);
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
        MyApp.popMessage("Error", "Time-out error occurred, please try again.", getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }


    public void setItemClicked(Address address) {
        SingleInstance.getInstance().setSelectedAddress(address);
        if (getIntent().getBooleanExtra(AppConstant.EXTRA_1, false))
            finish();
    }
}
