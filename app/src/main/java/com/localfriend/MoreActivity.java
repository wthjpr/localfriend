package com.localfriend;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.localfriend.utils.AppConstant;

public class MoreActivity extends CustomActivity {

    private TextView txt_title, txt_para_title2, txt_third_para;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 1) {
            mTitle.setText("ABOUT US");
        } else if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 2) {
            mTitle.setText("TERMS & CONDITIONS");
        }

        actionBar.setTitle("");
        setContentElements();
    }

    private void setContentElements() {
        txt_title = findViewById(R.id.txt_title);
        txt_third_para = findViewById(R.id.txt_third_para);
        txt_para_title2 = findViewById(R.id.txt_para_title2);

        if (getIntent().getIntExtra(AppConstant.EXTRA_1, 0) == 2) {
            txt_title.setText("TERMS & CONDITIONS");
            txt_para_title2.setVisibility(View.VISIBLE);
            txt_third_para.setVisibility(View.VISIBLE);
        }
        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        txt_title.getPaint().setShader(textShader);
    }
}
