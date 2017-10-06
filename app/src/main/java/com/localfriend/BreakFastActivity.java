package com.localfriend;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.localfriend.adapter.BreakfastAdapter;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.adapter.DummyBreakfastData;
import com.localfriend.adapter.DummyCartData;

import java.util.ArrayList;

public class BreakFastActivity extends CustomActivity {
    private Toolbar toolbar;
    private RecyclerView recy_breakfast;
    private BreakfastAdapter adapter;
    private ArrayList listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_fast);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Breakfast");
        actionBar.setTitle("");

        recy_breakfast = (RecyclerView) findViewById(R.id.recy_breakfast);
        listdata = (ArrayList) DummyBreakfastData.getListData();
        recy_breakfast.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BreakfastAdapter(listdata, this);
        recy_breakfast.setAdapter(adapter);
    }
}
