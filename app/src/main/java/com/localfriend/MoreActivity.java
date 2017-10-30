package com.localfriend;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.localfriend.utils.AppConstant;

public class MoreActivity extends CustomActivity {

    private TextView txt_first_para;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 1) {
            mTitle.setText("About us");
        } else if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 2) {
            mTitle.setText("Terms & Conditions");
        }

        actionBar.setTitle("");
        setContentElements();
    }

    private void setContentElements() {
        txt_first_para = findViewById(R.id.txt_first_para);


        if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 1) {
            txt_first_para.setText(Html.fromHtml(getString(R.string.about_us)));
        } else if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 2) {
            txt_first_para.setText(Html.fromHtml(getString(R.string.terms)));
        }

        if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 2) {
//            txt_title.setText("TERMS & CONDITIONS");
//            txt_para_title2.setVisibility(View.VISIBLE);
//            txt_third_para.setVisibility(View.VISIBLE);
        }
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
//        txt_title.getPaint().setShader(textShader);
    }
}
