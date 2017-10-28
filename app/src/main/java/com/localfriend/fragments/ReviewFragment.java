package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.aigestudio.wheelpicker.WheelPicker;
import com.google.gson.JsonObject;
import com.localfriend.AddressListActivity;
import com.localfriend.CheckOutActivity;
import com.localfriend.OrderPlacedActivity;
import com.localfriend.R;
import com.localfriend.adapter.CheckoutAdapter;
import com.localfriend.adapter.ViewItemsAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Address;
import com.localfriend.model.Checkout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private TextView txt_ok;
    private TextView txt_toal;
    private TextView txt_subtotal;
    private TextView tv_make_payment;
    private TextView txt_address_type;
    private TextView txt_name;
    private TextView txt_address;
    private WheelPicker mainWheel;
    private RelativeLayout rl_timestamp;
    private RecyclerView rv_items;
    private CheckoutAdapter adapter;
    private boolean isAddressSelected = false;


    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_review, container, false);
        rv_items = myView.findViewById(R.id.rv_items);
        rv_items.setLayoutManager(new LinearLayoutManager(getContext()));
        txt_ok = myView.findViewById(R.id.txt_ok);
        txt_subtotal = myView.findViewById(R.id.txt_subtotal);
        txt_toal = myView.findViewById(R.id.txt_toal);
        mainWheel = myView.findViewById(R.id.main_wheel);
        rl_timestamp = myView.findViewById(R.id.rl_timestamp);
        tv_make_payment = myView.findViewById(R.id.tv_make_payment);

        txt_address = myView.findViewById(R.id.txt_address);
        txt_name = myView.findViewById(R.id.txt_name);
        txt_address_type = myView.findViewById(R.id.txt_address_type);
        setClick(txt_ok);
        try {
            Checkout c = SingleInstance.getInstance().getCheckoutData();
            txt_subtotal.setText("Rs. " + c.getTotalprice());
            txt_toal.setText("Rs. " + c.getSellingprice());
            adapter = new CheckoutAdapter(c.getCheckoutlist(), ReviewFragment.this, true);

        } catch (Exception e) {
        }

        rv_items.setAdapter(adapter);
        rv_items.setNestedScrollingEnabled(false);
        setTouchNClick(tv_make_payment);

        return myView;

    }

    private String dateString = "";
    private int selectedItemPosition = 0;

    private void initWheel(List<String> data) {
        rl_timestamp.setVisibility(View.VISIBLE);
        mainWheel.setCurved(false);
        mainWheel.setData(data);
        mainWheel.setSelectedItemPosition(3);
        mainWheel.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                dateString = String.valueOf(data);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Address a = SingleInstance.getInstance().getSelectedAddress();
            if (a != null) {
                isAddressSelected = true;
                String address = "";
                if (a.getAddDetails().length() > 0) {
                    address += a.getAddDetails() + ", ";
                }
                if (a.getAddDetails1().length() > 0) {
                    address += a.getAddDetails1() + ", ";
                }
                if (a.getAddDetails2().length() > 0) {
                    address += a.getAddDetails2() + ", ";
                }
                if (a.getAddZipCode().length() > 0) {
                    address += "(" + a.getAddZipCode() + ")\n";
                }
                if (a.getAddSatate().length() > 0) {
                    address += a.getAddSatate() + "";
                }

                txt_address.setText(address);
                txt_address_type.setText(a.getAddType());
                txt_name.setText(a.getAddName());
            } else {
                isAddressSelected = false;
                txt_address.setText("");
                txt_address_type.setText("");
                txt_name.setText("");
            }
        } catch (Exception e) {
            isAddressSelected = false;
            txt_address.setText("");
            txt_address_type.setText("");
            txt_name.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_ok) {
            rl_timestamp.setVisibility(View.GONE);
            MyApp.showMassage(getContext(), dateString);
        } else if (v == tv_make_payment) {
            JSONObject o = new JSONObject();
            try {
                o.put("shippingID", SingleInstance.getInstance().getShippingID());
                o.put("transactionID", "");
                o.put("payAmount", SingleInstance.getInstance().getPayAmount());
                o.put("promoCode", "");
                o.put("promoDiscount", "");
                o.put("paymentMethod", "COD");
                showLoadingDialog("");
                postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Order", o, "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            showLoadingDialog("Thank you for your confirmation. Please do not close this mobile application while we are " +
//                    "processing your order");


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(getActivity(), OrderPlacedActivity.class));
//                    getActivity().finish();
//                }
//            }, 1500);
        }
    }

    public void setTimingsClick(Checkout.CheckoutListData checkoutListData) {
        List<String> dateTimeData = new ArrayList<>();
        for (int i = 0; i < checkoutListData.getTimestemp().size(); i++) {
            dateTimeData.add(checkoutListData.getTimestemp().get(i).getTimedate() + " " +
                    checkoutListData.getTimestemp().get(i).getTimestemp());
        }
        initWheel(dateTimeData);
    }

    public void viewItemsClick(Checkout.CheckoutListData checkoutListData) {
        ViewItemsAdapter adapter = new ViewItemsAdapter(getActivity(), false, checkoutListData.getItem());
        showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                dismissListener, cancelListener,
                true);
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
//            getProducts(catId, catList.get(position).getStorelist().get(0).getsID());
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
//                .setFooter(R.layout.footer)
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

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (o.optString("status").equals("success")) {
            startActivity(new Intent(getActivity(), OrderPlacedActivity.class));
        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Alert!", "Time-out error occurred, please try again.", getActivity());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Alert!", error, getActivity());
    }
}
