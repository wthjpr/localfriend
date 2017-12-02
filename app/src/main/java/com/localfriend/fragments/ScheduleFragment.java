package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.localfriend.AddressListActivity;
import com.localfriend.CheckOutActivity;
import com.localfriend.R;
import com.localfriend.adapter.AddressAdapter;
import com.localfriend.adapter.CheckoutAdapter;
import com.localfriend.adapter.ViewItemsAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Address;
import com.localfriend.model.Checkout;
import com.localfriend.model.TimeStamp;
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
public class ScheduleFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private TextView txt_ok;
    private TextView txt_total;
    private TextView txt_subtotal;
    private TextView tv_make_payment;
    private TextView txt_address_type;
    private TextView txt_name;
    private TextView txt_address;
    private ImageView txt_add_address;
    private ImageView img_change;
    private WheelPicker mainWheel;
    private FrameLayout rl_timestamp;
    private RecyclerView rv_items;
    private CheckoutAdapter adapter;
    private boolean isAddressSelected = false;
    private List<HashMap<String, String>> mapList = new ArrayList<>();


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_schedule, container, false);
        rv_items = myView.findViewById(R.id.rv_items);
        rv_items.setLayoutManager(new LinearLayoutManager(getContext()));
        txt_ok = myView.findViewById(R.id.txt_ok);
        txt_subtotal = myView.findViewById(R.id.txt_subtotal);
        txt_total = myView.findViewById(R.id.txt_total);
        mainWheel = myView.findViewById(R.id.main_wheel);
        rl_timestamp = myView.findViewById(R.id.rl_timestamp);
        tv_make_payment = myView.findViewById(R.id.tv_make_payment);
        img_change = myView.findViewById(R.id.img_change);
        setTouchNClick(img_change);
        txt_add_address = myView.findViewById(R.id.txt_add_address);
        txt_address = myView.findViewById(R.id.txt_address);
        txt_name = myView.findViewById(R.id.txt_name);
        txt_address_type = myView.findViewById(R.id.txt_address_type);
        setTouchNClick(txt_add_address);
        setClick(txt_ok);

        try {
            Checkout c = SingleInstance.getInstance().getCheckoutData();
            txt_subtotal.setText(MyApp.getRupeeCurrency() + c.getTotalprice());
            txt_total.setText(MyApp.getRupeeCurrency() + c.getSellingprice());
            adapter = new CheckoutAdapter(c.getCheckoutlist(), ScheduleFragment.this, false);
            for (int i = 0; i < c.getCheckoutlist().size(); i++) {
                HashMap<String, String> m = new HashMap<>();
                m.put("catId", c.getCheckoutlist().get(i).getCategoryid());
                m.put("stampId", c.getCheckoutlist().get(i).getTimestemp().get(0).getId());
                m.put("deliverydate", c.getCheckoutlist().get(i).getTimestemp().get(0).getTimedate());
                mapList.add(m);
            }
        } catch (Exception e) {
        }

        rv_items.setAdapter(adapter);
        setTouchNClick(tv_make_payment);

        return myView;
    }

    private String dateString = "";
    private int selectedItemPosition = 0;

    private void initWheel(final List<String> oData, final int pos, final List<TimeStamp> tList, final String catId) {
        selectedItemPosition = pos;
        rl_timestamp.setVisibility(View.VISIBLE);
        mainWheel.setCurved(false);
        mainWheel.setData(oData);

        switch (getResources().getDisplayMetrics().densityDpi) {

            case DisplayMetrics.DENSITY_MEDIUM:
                mainWheel.setItemTextSize(18);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                mainWheel.setItemTextSize(22);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                mainWheel.setItemTextSize(36);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                mainWheel.setItemTextSize(40);
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                mainWheel.setItemTextSize(50);
                break;
            case DisplayMetrics.DENSITY_400:
                mainWheel.setItemTextSize(25);
                break;
            default:
                mainWheel.setItemTextSize(22);
                break;
        }


        mainWheel.setSelectedItemPosition(3);
        mainWheel.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                positionForTime = position;
                adapter.listdata.get(selectedItemPosition).setSelection(positionForTime);
                adapter.notifyDataSetChanged();
                dateString = String.valueOf(data);
                HashMap<String, String> reMap = new HashMap<>();
                reMap.put("catId", catId);
                reMap.put("stampId", tList.get(adapter.listdata.get(pos).getSelection()).getId());
                reMap.put("deliverydate", tList.get(adapter.listdata.get(pos).getSelection()).getTimedate());
                mapList.set(pos, reMap);

            }
        });

    }

    private int positionForTime = 0;

    @Override
    public void onResume() {
        super.onResume();
        addressChanges();
    }

    private void addressChanges() {
        try {
            Address a = SingleInstance.getInstance().getSelectedAddress();
            if (a != null) {
                isAddressSelected = true;
                String address = "";
                try {
                    if (a.getAddDetails().length() > 0) {
                        address += a.getAddDetails() + ", ";
                    }
                    try {
                        if (a.getAddDetails1().length() > 0) {
                            address += a.getAddDetails1() + ", ";
                        }
                    } catch (Exception e) {
                    }
                    if (a.getAddDetails2().length() > 0) {
                        address += a.getAddDetails2() + ", ";
                    }
                    try {
                        if (a.getAddDetails3().length() > 0) {
                            address += a.getAddDetails3() + ", ";
                        }
                    } catch (Exception e) {
                    }

                    if (a.getAddCity().length() > 0) {
                        address += a.getAddCity() + "";
                    }

                } catch (Exception e) {
                }

                txt_add_address.setVisibility(View.GONE);
                txt_address.setText(address);
                txt_address_type.setText(a.getAddType());
                img_change.setVisibility(View.VISIBLE);
                txt_name.setText(a.getAddName());
            } else {
                isAddressSelected = false;
                txt_add_address.setVisibility(View.VISIBLE);
                txt_address.setText("");
                txt_address_type.setText("");
                img_change.setVisibility(View.GONE);
                txt_name.setText("");
            }
        } catch (Exception e) {
            isAddressSelected = false;
            txt_add_address.setVisibility(View.VISIBLE);
            txt_address.setText("");
            txt_address_type.setText("");
            img_change.setVisibility(View.GONE);
            txt_name.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_ok) {
            rl_timestamp.setVisibility(View.GONE);

//            MyApp.showMassage(getContext(), dateString);
        } else if (v == tv_make_payment) {
            if (isAddressSelected) {
                try {
                    JSONObject o = new JSONObject();
                    o.put("billingaddressId", SingleInstance.getInstance().getSelectedAddress().getAddID());
                    o.put("shippingaddressId", SingleInstance.getInstance().getSelectedAddress().getAddID());
                    JSONArray arr = new JSONArray();
                    for (int i = 0; i < mapList.size(); i++) {
                        JSONObject oo = new JSONObject();
                        oo.put("categoryId", mapList.get(i).get("catId"));
                        oo.put("timestempId", mapList.get(i).get("stampId"));
                        String dateString = MyApp.changeDateToddMMyyyy(mapList.get(i).get("deliverydate"));
                        oo.put("deliverydate", dateString);
                        arr.put(oo);
                    }

                    o.put("CategoryTimeStemplist", arr);
                    Log.d("object", o.toString());
                    showLoadingDialog("");
                    postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "CheckOut", o, "");
//                    ((CheckOutActivity) getActivity()).changeFragmentPosition(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                MyApp.popMessage("Alert!", "Please select delivery address first.", getContext());
        } else if (v == txt_add_address) {
            startActivity(new Intent(getActivity(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true));
        } else if (v == img_change) {
            startActivity(new Intent(getActivity(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true));
        }
    }

    public void setTimingsClick(Checkout.CheckoutListData checkoutListData, int position, String catId) {
        List<String> dateTimeData = new ArrayList<>();
        for (int i = 0; i < checkoutListData.getTimestemp().size(); i++) {
            dateTimeData.add(MyApp.parseDateToddMMMyyyy(checkoutListData.getTimestemp().get(i).getTimedate()) + "        " +
                    checkoutListData.getTimestemp().get(i).getTimestemp());
        }
        initWheel(dateTimeData, position, checkoutListData.getTimestemp(), catId);
    }

    public void viewItemsClick(Checkout.CheckoutListData checkoutListData) {
        ViewItemsAdapter adapter = new ViewItemsAdapter(getActivity(), false, checkoutListData.getItem());
        showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                dismissListener, cancelListener,
                false);
    }

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
                .setExpanded(false)
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
            SingleInstance.getInstance().setShippingID(o.optString("ShippingID"));
            SingleInstance.getInstance().setPayAmount(txt_total.getText().toString());
            ((CheckOutActivity) getActivity()).changeFragmentPosition(1);
        } else {
//            MyApp.showMassage(getContext(), o.optString("message"));
        }
//
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
        MyApp.popMessage("Alert!", "\"Sorry for your inconvenience. Your order is not submitting, Please try again after sometime.", getActivity());
        dismissDialog();
    }
}
