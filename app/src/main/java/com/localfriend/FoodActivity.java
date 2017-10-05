package com.localfriend;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.fragments.AddressFragment;

public class FoodActivity extends CustomActivity {
    private Toolbar toolbar;
    private LinearLayout lnr_bakery, lnr_south_indian, lnr_fast_food, lnr_drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        toolbar = (Toolbar) findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Food");
        actionBar.setTitle("");
        setupUiElements();
    }

    private void setupUiElements() {

        lnr_bakery = (LinearLayout) findViewById(R.id.lnr_bakery);
        lnr_south_indian = (LinearLayout) findViewById(R.id.lnr_south_indian);
        lnr_fast_food = (LinearLayout) findViewById(R.id.lnr_fast_food);
        lnr_drinks = (LinearLayout) findViewById(R.id.lnr_drinks);

        setClick(R.id.lnr_bakery);
        setClick(R.id.lnr_south_indian);
        setClick(R.id.lnr_fast_food);
        setClick(R.id.lnr_drinks);



    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.lnr_bakery) {

            Toast.makeText(this, "You've Selected Bakery", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.lnr_south_indian) {

            Toast.makeText(this, "You've Selected South Indian", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.lnr_fast_food) {

            Toast.makeText(this, "You've Selected Fast Food", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.lnr_drinks) {
            Toast.makeText(this, "You've Selected Drinks", Toast.LENGTH_SHORT).show();

        }
    }
}
