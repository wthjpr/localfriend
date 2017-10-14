package com.localfriend;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.adapter.SimpleAdapter;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.AddressFragment;
import com.localfriend.model.CategoryDetails;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends CustomActivity {
    private Toolbar toolbar;
    private ImageView img1, img2, img3, img4;
    private TextView txt1, txt2, txt3, txt4;
    private List<CategoryDetails> catList;

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

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);


        catList = SingleInstance.getInstance().getCatList();

        if (catList.size() == 1) {
            setTouchNClick(R.id.rl_cat1);
            Picasso.with(getContext()).load(catList.get(0).getImage()).into(img1);
            txt1.setText(catList.get(0).getName());
        }
        if (catList.size() == 2) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            Picasso.with(getContext()).load(catList.get(0).getImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getImage()).into(img2);
            txt2.setText(catList.get(1).getName());
        }
        if (catList.size() == 3) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            setTouchNClick(R.id.rl_cat3);
            Picasso.with(getContext()).load(catList.get(0).getImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getImage()).into(img2);
            txt2.setText(catList.get(1).getName());
            Picasso.with(getContext()).load(catList.get(2).getImage()).into(img3);
            txt3.setText(catList.get(2).getName());
        }
        if (catList.size() == 4) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            setTouchNClick(R.id.rl_cat3);
            setTouchNClick(R.id.rl_cat4);
            Picasso.with(getContext()).load(catList.get(0).getImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getImage()).into(img2);
            txt2.setText(catList.get(1).getName());
            Picasso.with(getContext()).load(catList.get(2).getImage()).into(img3);
            txt3.setText(catList.get(2).getName());
            Picasso.with(getContext()).load(catList.get(3).getImage()).into(img4);
            txt4.setText(catList.get(3).getName());
        }
    }

    private Context getContext() {
        return FoodActivity.this;
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_cat1) {
            List<String> listStore = new ArrayList<>();
            for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                listStore.add(catList.get(0).getStorelist().get(i).getsName());
            }
            SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
            showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter,clickListener, itemClickListener, dismissListener, cancelListener,
                    true);
        } else if (v.getId() == R.id.rl_cat2) {

        } else if (v.getId() == R.id.rl_cat3) {

        } else if (v.getId() == R.id.rl_cat4) {

        }
    }
    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            //        switch (view.getId()) {
            //          case R.id.header_container:
            //            Toast.makeText(MainActivity.this, "Header clicked", Toast.LENGTH_LONG).show();
            //            break;
            //          case R.id.like_it_button:
            //            Toast.makeText(MainActivity.this, "We're glad that you like it", Toast.LENGTH_LONG).show();
            //            break;
            //          case R.id.love_it_button:
            //            Toast.makeText(MainActivity.this, "We're glad that you love it", Toast.LENGTH_LONG).show();
            //            break;
            //          case R.id.footer_confirm_button:
            //            Toast.makeText(MainActivity.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
            //            break;
            //          case R.id.footer_close_button:
            //            Toast.makeText(MainActivity.this, "Close button clicked", Toast.LENGTH_LONG).show();
            //            break;
            //        }
            //        dialog.dismiss();
        }
    };
    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {
            //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
        }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {
            //        Toast.makeText(MainActivity.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
        }
    };
    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            String clickedAppName = textView.getText().toString();
            //        dialog.dismiss();
            //        Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
        }
    };

    private void showCompleteDialog(Holder holder, int gravity, BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setHeader(R.layout.header_store)
//                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
//        .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(android.R.color.transparent)
//        .setContentBackgroundResource(R.drawable.corner_background)
                //                .setOutMostMargin(0, 100, 0, 0)
                .create();
        dialog.show();
    }
}
