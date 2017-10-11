package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.localfriend.adapter.AddressAdapter;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.adapter.DummyCartData;
import com.localfriend.adapter.DummyCartItem;
import com.localfriend.fragments.AddressFragment;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends CustomActivity {
    private Toolbar toolbar;
    private RecyclerView rv_addresses;
    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Address");
        actionBar.setTitle("");
        setupUiElements();
    }

    private void setupUiElements() {
        rv_addresses = (RecyclerView) findViewById(R.id.rv_addresses);
        List<DummyCartItem> listdata = (ArrayList) DummyCartData.getListData();
        rv_addresses.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AddressAdapter(listdata, getContext());
        rv_addresses.setAdapter(adapter);
    }

    private Context getContext() {
        return AddressListActivity.this;
    }


    public void onClick(View v) {
        super.onClick(v);
    }

    public void setEditClick(int layoutPosition) {
        startActivity(new Intent(getContext(), AddressActivity.class));
    }
}
