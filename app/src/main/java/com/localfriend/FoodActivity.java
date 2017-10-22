package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.adapter.SimpleAdapter;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.AddressFragment;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.CustomFragment;
import com.localfriend.fragments.HomeFragment;
import com.localfriend.fragments.TiffinFragment;
import com.localfriend.fragments.VegetableFragment;
import com.localfriend.model.CategoryDetails;
import com.localfriend.model.Product;
import com.localfriend.model.ProductData;
import com.localfriend.model.Slider;
import com.localfriend.model.StoreList;
import com.localfriend.utils.AppConstant;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class FoodActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private ImageView img1, img2, img3, img4;
    private TextView txt1, txt2, txt3, txt4;
    private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    private ImageView img_home, img_tiffin, img_cart, img_more;
    private List<CategoryDetails> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
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

        img_home = findViewById(R.id.img_home);
        img_tiffin = findViewById(R.id.img_tiffin);
        img_cart = findViewById(R.id.img_cart);
        img_more = findViewById(R.id.img_more);


        tv_home = findViewById(R.id.tv_home);
        tv_tiffin = findViewById(R.id.tv_tiffin);
        tv_cart = findViewById(R.id.tv_cart);
        tv_more = findViewById(R.id.tv_more);

        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);

        catList = SingleInstance.getInstance().getCatList();

        if (catList.size() == 1) {
            setTouchNClick(R.id.rl_cat1);
            Picasso.with(getContext()).load(catList.get(0).getThumbImage()).placeholder(R.drawable.bakery)
                    .error(R.drawable.bakery).into(img1);
            txt1.setText(catList.get(0).getName());
        }
        if (catList.size() == 2) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            Picasso.with(getContext()).load(catList.get(0).getThumbImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getThumbImage()).into(img2);
            txt2.setText(catList.get(1).getName());
        }
        if (catList.size() == 3) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            setTouchNClick(R.id.rl_cat3);
            Picasso.with(getContext()).load(catList.get(0).getThumbImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getThumbImage()).into(img2);
            txt2.setText(catList.get(1).getName());
            Picasso.with(getContext()).load(catList.get(2).getThumbImage()).into(img3);
            txt3.setText(catList.get(2).getName());
        }
        if (catList.size() >= 4) {
            setTouchNClick(R.id.rl_cat1);
            setTouchNClick(R.id.rl_cat2);
            setTouchNClick(R.id.rl_cat3);
            setTouchNClick(R.id.rl_cat4);
            Picasso.with(getContext()).load(catList.get(0).getThumbImage()).into(img1);
            txt1.setText(catList.get(0).getName());
            Picasso.with(getContext()).load(catList.get(1).getThumbImage()).into(img2);
            txt2.setText(catList.get(1).getName());
            Picasso.with(getContext()).load(catList.get(2).getThumbImage()).into(img3);
            txt3.setText(catList.get(2).getName());
            Picasso.with(getContext()).load(catList.get(3).getThumbImage()).into(img4);
            txt4.setText(catList.get(3).getName());
        }
    }

    private Context getContext() {
        return FoodActivity.this;
    }

    private String catId;
    private String title;
    private List<StoreList> currentStoreList;

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_cat1) {
            title = catList.get(0).getName();
            catId = catList.get(0).getID();
            List<String> listStore = new ArrayList<>();
            for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                listStore.add(catList.get(0).getStorelist().get(i).getsName());
            }
            if (listStore.size() == 1) {
                getProducts(catId, catList.get(0).getStorelist().get(0).getsID());
                return;
            }
            currentStoreList = catList.get(0).getStorelist();
            SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
            showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                    dismissListener, cancelListener,
                    true);
        } else if (v.getId() == R.id.rl_cat2) {
            title = catList.get(1).getName();
            catId = catList.get(1).getID();
            List<String> listStore = new ArrayList<>();
            for (int i = 0; i < catList.get(1).getStorelist().size(); i++) {
                listStore.add(catList.get(1).getStorelist().get(i).getsName());
            }
            if (listStore.size() == 1) {
                getProducts(catId, catList.get(1).getStorelist().get(0).getsID());
                return;
            }
            currentStoreList = catList.get(1).getStorelist();
            SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
            showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    true);
        } else if (v.getId() == R.id.rl_cat3) {
            title = catList.get(2).getName();
            catId = catList.get(2).getID();
            List<String> listStore = new ArrayList<>();
            for (int i = 0; i < catList.get(2).getStorelist().size(); i++) {
                listStore.add(catList.get(2).getStorelist().get(i).getsName());
            }
            if (listStore.size() == 1) {
                getProducts(catId, catList.get(2).getStorelist().get(0).getsID());
                return;
            }
            currentStoreList = catList.get(2).getStorelist();
            SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
            showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    true);
        } else if (v.getId() == R.id.rl_cat4) {
            title = catList.get(3).getName();
            catId = catList.get(3).getID();
            List<String> listStore = new ArrayList<>();
            for (int i = 0; i < catList.get(3).getStorelist().size(); i++) {
                listStore.add(catList.get(3).getStorelist().get(i).getsName());
            }
            if (listStore.size() == 1) {
                getProducts(catId, catList.get(3).getStorelist().get(0).getsID());
                return;
            }
            currentStoreList = catList.get(3).getStorelist();
            SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
            showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                    true);
        } else if (v.getId() == R.id.rl_tab_1) {

            img_home.setSelected(true);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(true);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#275B89"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home_active);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 1));
            finish();
        } else if (v.getId() == R.id.rl_tab_2) {
            img_home.setSelected(false);
            img_tiffin.setSelected(true);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(true);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#275B89"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tiffin_active);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 2));
            finish();
        } else if (v.getId() == R.id.rl_tab_3) {
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(true);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(true);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#275B89"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart_active);
            img_more.setImageResource(R.drawable.ic_more);

            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 3));
            finish();
        } else if (v.getId() == R.id.rl_tab_4) {

            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(true);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(true);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#275B89"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more_active);
            startActivity(new Intent(getContext(), MainActivity.class).putExtra(AppConstant.TAB, 4));
            finish();
        }
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
        }
    };
    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {
        }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {
        }
    };
    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            String clickedAppName = textView.getText().toString();
            getProducts(catId, catList.get(position).getStorelist().get(0).getsID());
        }
    };

    private void showCompleteDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this)

                .setContentHolder(holder)
                .setHeader(R.layout.header_store)
                .setContentBackgroundResource(R.drawable.rounded_corner_white)
//                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        getProducts(catId, currentStoreList.get(position).getsID());
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
//        .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(R.color.transparent)
//        .setContentBackgroundResource(R.drawable.corner_background)
                //                .setOutMostMargin(0, 100, 0, 0)
                .create();
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void getProducts(String catId, String storeId) {
//        catId = "22";
//        storeId = "85d0c459-9025-4806-a634-2cc1553ef115";
//        http://www.localfriend.co.in/api/product?categoryid=22&storeid=85d0c459-9025-4806-a634-2cc1553ef115
        showLoadingDialog("");
        getCall(AppConstants.BASE_URL + "product?categoryid=" + catId + "&storeid=" + storeId, "", 1);

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 1) {
            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    if (title.equals("Bakery")) {
                        startActivity(new Intent(getContext(), VegetableActivity.class).putExtra(AppConstant.EXTRA_1, title));
                    } else {
                        startActivity(new Intent(getContext(), AllActivity.class).putExtra(AppConstant.EXTRA_1, title));
                    }

                } else {
                    MyApp.popMessage("Local Friend", "We are not able to find any product related to selected category & store," +
                            " Please come back later.\nThank you.", getContext());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error", "Time-out error occurred, please try again later.", getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }
}
