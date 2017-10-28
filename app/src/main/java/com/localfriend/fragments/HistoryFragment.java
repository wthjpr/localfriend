package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.CheckOutActivity;
import com.localfriend.R;
import com.localfriend.adapter.CurrentAdapter;
import com.localfriend.adapter.HistoryAdapter;
import com.localfriend.adapter.ViewHistoryItemsAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.model.History;
import com.localfriend.utils.AppConstant;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private RelativeLayout rl_empty;
    private RecyclerView rv_history;
    private TextView tv_start_shopping;
    private HistoryAdapter adapter;

    public HistoryFragment() {
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
        getCallWithHeader(AppConstant.BASE_URL + "Order/History", 1);
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
        if (callNumber == 1) {
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
                    adapter = new HistoryAdapter(historyList, HistoryFragment.this);
                    rv_history.setAdapter(adapter);
                } else {
                    rl_empty.setVisibility(View.VISIBLE);
                    rv_history.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 2) {
            {
                dismissDialog();
                try {
                    History h = new Gson().fromJson(o.getJSONObject("data").toString(), History.class);
                    ViewHistoryItemsAdapter adapter = new ViewHistoryItemsAdapter(getActivity(), false, h.getOrderItemlist());
                    showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                            dismissListener, cancelListener,
                            true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            MyApp.showMassage(getActivity(), o.optString("message"));
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

    public void viewDetails(String order_id) {
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Order/" + order_id, 2);
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            TextView textView = (TextView) view.findViewById(R.id.text_view);
            String clickedAppName = textView.getText().toString();
//            getProducts(catId, catList.get(position).getStorelist().get(0).getsID());
        }
    };
    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            dialog.dismiss();
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

    private void showCompleteDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getActivity())

                .setContentHolder(holder)
                .setHeader(R.layout.header_view_item)
                .setContentBackgroundResource(R.drawable.bg_cart)
                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setExpanded(true)
                .setExpanded(true, ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
//        .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(R.color.transparent)
//        .setContentBackgroundResource(R.drawable.corner_background)
                //                .setOutMostMargin(0, 100, 0, 0)
                .create();
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}
