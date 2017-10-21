package com.localfriend.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.localfriend.AllActivity;
import com.localfriend.BreakFastActivity;
import com.localfriend.R;
import com.localfriend.adapter.SimpleAdapter;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.CategoryDetails;
import com.localfriend.model.ProductData;
import com.localfriend.model.StoreList;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TiffinFragment extends CustomFragment implements View.OnClickListener, CustomFragment.ResponseCallback {
    private CardView card_breakfast, card_lunch, card_dinner, card_snacks;
    private ImageView img_snacks, img_dinner, img_lunch, img_breakfast;
    private TextView tv_breakfast, tv_lunch, tv_dinner, tv_snacks;
    private List<CategoryDetails> catList;

    public TiffinFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catList = SingleInstance.getInstance().getCatList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setResponseListener(this);
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tiffin, container, false);
        card_breakfast = myView.findViewById(R.id.card_breakfast);
        card_lunch = myView.findViewById(R.id.card_lunch);
        card_dinner = myView.findViewById(R.id.card_dinner);
        card_snacks = myView.findViewById(R.id.card_snacks);
        img_breakfast = (ImageView) myView.findViewById(R.id.img_breakfast);
        img_lunch = (ImageView) myView.findViewById(R.id.img_lunch);
        img_dinner = (ImageView) myView.findViewById(R.id.img_dinner);
        img_snacks = (ImageView) myView.findViewById(R.id.img_snacks);

        tv_breakfast = (TextView) myView.findViewById(R.id.tv_breakfast);
        tv_lunch = (TextView) myView.findViewById(R.id.tv_lunch);
        tv_dinner = (TextView) myView.findViewById(R.id.tv_dinner);
        tv_snacks = (TextView) myView.findViewById(R.id.tv_snacks);
        hidenView();
        showView();
        card_breakfast.setOnClickListener(this);
        card_lunch.setOnClickListener(this);
        card_dinner.setOnClickListener(this);
        card_snacks.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 21) {

            // Set the status bar to dark-semi-transparentish
//            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            LinearLayout v =  myView.findViewById(R.id.ll_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 3*MyApp.getApplication().getStatusBarHeight(), 0, 0);
//            v.setPadding(getStatusBarHeight(), getStatusBarHeight(), getStatusBarHeight(), 0);
        }

        return myView;
    }

    public void hidenView() {
        tv_breakfast.setVisibility(View.GONE);
        img_breakfast.setVisibility(View.GONE);
        tv_lunch.setVisibility(View.GONE);
        img_lunch.setVisibility(View.GONE);
        tv_dinner.setVisibility(View.GONE);
        img_dinner.setVisibility(View.GONE);
        tv_snacks.setVisibility(View.GONE);
        img_snacks.setVisibility(View.GONE);

    }

    public void showView() {
        Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.bottom_up);
        tv_breakfast.startAnimation(bottomUp);
        tv_breakfast.setVisibility(View.VISIBLE);
        img_breakfast.startAnimation(bottomUp);
        img_breakfast.setVisibility(View.VISIBLE);
        tv_lunch.startAnimation(bottomUp);
        tv_lunch.setVisibility(View.VISIBLE);
        img_lunch.startAnimation(bottomUp);
        img_lunch.setVisibility(View.VISIBLE);
        tv_dinner.startAnimation(bottomUp);
        tv_dinner.setVisibility(View.VISIBLE);
        img_dinner.startAnimation(bottomUp);
        img_dinner.setVisibility(View.VISIBLE);
        tv_snacks.startAnimation(bottomUp);
        tv_snacks.setVisibility(View.VISIBLE);
        img_snacks.startAnimation(bottomUp);
        img_snacks.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_breakfast: {
                currentStoreList = catList.get(0).getStorelist();
                catId = "12";
                List<String> listStore = new ArrayList<>();
                for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                    listStore.add(catList.get(0).getStorelist().get(i).getsName());
                }
                if (listStore.size() == 1) {
                    getProducts("12", catList.get(0).getStorelist().get(0).getsID());
                    return;
                }
                currentStoreList = catList.get(0).getStorelist();
                SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                        true);
                break;
            }
            case R.id.card_lunch: {
                currentStoreList = catList.get(2).getStorelist();
                catId = "17";
                List<String> listStore = new ArrayList<>();
                for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                    listStore.add(catList.get(0).getStorelist().get(i).getsName());
                }
                if (listStore.size() == 1) {
                    getProducts("17", catList.get(0).getStorelist().get(0).getsID());
                    return;
                }
                currentStoreList = catList.get(0).getStorelist();
                SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                        true);
                break;
            }
            case R.id.card_dinner: {
                currentStoreList = catList.get(1).getStorelist();
                catId = "21";
                List<String> listStore = new ArrayList<>();
                for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                    listStore.add(catList.get(0).getStorelist().get(i).getsName());
                }
                if (listStore.size() == 1) {
                    getProducts("21", catList.get(0).getStorelist().get(0).getsID());
                    return;
                }
                currentStoreList = catList.get(0).getStorelist();
                SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                        true);
                break;
            }
            case R.id.card_snacks: {
                currentStoreList = catList.get(3).getStorelist();
                catId = "18";
                List<String> listStore = new ArrayList<>();
                for (int i = 0; i < catList.get(0).getStorelist().size(); i++) {
                    listStore.add(catList.get(0).getStorelist().get(i).getsName());
                }
                if (listStore.size() == 1) {
                    getProducts("18", catList.get(0).getStorelist().get(0).getsID());
                    return;
                }
                currentStoreList = catList.get(0).getStorelist();
                SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                        true);

                break;
            }
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
//            TextView textView = (TextView) view.findViewById(R.id.text_view);
//            String clickedAppName = textView.getText().toString();
//            getProducts(catId, catList.get(position).getStorelist().get(0).getsID());
        }
    };

    private void getProducts(String catId, String storeId) {
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
                    startActivity(new Intent(getActivity(), BreakFastActivity.class));
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

    private String catId;
    private List<StoreList> currentStoreList;

    private void showCompleteDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getActivity())

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
}
