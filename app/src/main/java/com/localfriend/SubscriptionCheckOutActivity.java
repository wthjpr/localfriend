package com.localfriend;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.localfriend.adapter.AddressAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.PaymentFragment;
import com.localfriend.fragments.ReviewFragment;
import com.localfriend.fragments.ScheduleFragment;
import com.localfriend.model.Address;
import com.localfriend.model.Checkout;
import com.localfriend.model.Product;
import com.localfriend.utils.AppConstant;
import com.localfriend.utils.CustomViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SubscriptionCheckOutActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private Product product;
    private TextView txt_total2, txt_total1, txt_address_type, txt_name, txt_address, txt_title, txt_start_date;
    private EditText edt_note;
    private ImageView txt_add_address, img_change;
    private TextView tv_make_payment;
    private boolean isAddressSelected = false;
    private String dateSelected;
    private String shippingId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_subscription_check_out);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText(getIntent().getStringExtra(AppConstant.EXTRA_1));
        actionBar.setTitle("");
        product = SingleInstance.getInstance().getSubscriptionProduct();
//        showLoadingDialog("");
//        getCallWithHeader(AppConstant.BASE_URL + "CheckOut", 1);

        setupUiElements();
        txt_title.setText(mTitle.getText().toString());
    }

    private void setupUiElements() {
        txt_total2 = findViewById(R.id.txt_total2);
        txt_total1 = findViewById(R.id.txt_total1);
        txt_address_type = findViewById(R.id.txt_address_type);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);
        txt_title = findViewById(R.id.txt_title);
        txt_start_date = findViewById(R.id.txt_start_date);
        edt_note = findViewById(R.id.edt_note);
        txt_add_address = findViewById(R.id.txt_add_address);
        img_change = findViewById(R.id.img_change);
        tv_make_payment = findViewById(R.id.tv_make_payment);

        setTouchNClick(R.id.txt_start_date);
        setTouchNClick(R.id.img_change);
        setTouchNClick(R.id.txt_add_address);
        setTouchNClick(R.id.tv_make_payment);

        dateSelected = MyApp.millsToDate(System.currentTimeMillis());
        if (getIntent().getBooleanExtra(AppConstant.EXTRA_2, false)) {
            txt_total2.setText("Total Price :- Rs. " + product.getpPrice() + "/ "+product.getpName());//weekly
        } else {
            txt_total2.setText("Total Price :- Rs. " + product.getpPrice() + "/ mo");
        }

        txt_total1.setText("Total Price :- Rs. " + product.getpPrice());
        txt_start_date.setText("Start From : " + dateSelected);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_add_address) {
            startActivity(new Intent(getContext(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true));
        } else if (v == img_change) {
            startActivity(new Intent(getContext(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true));
        } else if (v == txt_start_date) {
            Bundle bundle = new Bundle();
            Calendar c = Calendar.getInstance();
            bundle.putInt("year", c.get(Calendar.YEAR));
            bundle.putInt("month", c.get(Calendar.MONTH));
            bundle.putInt("day", c.get(Calendar.DAY_OF_MONTH));
            DatePickerFragment fragment = DatePickerFragment
                    .newInstance(bundle);
            fragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateSelected = (month + 1) + "/" + dayOfMonth + "/" + year;
                    txt_start_date.setText("Start From : " + (month + 1) + "/" + dayOfMonth + "/" + year);
                }
            });
            fragment.show(getSupportFragmentManager(), null);
        } else if (v == tv_make_payment) {
            if (isAddressSelected) {
                try {
                    JSONObject o = new JSONObject();

                    o.put("addressID", shippingId);
                    o.put("packageID", product.getpId());
                    o.put("transactionID", "");
                    o.put("startdate", dateSelected);
                    o.put("extraNote", edt_note.getText().toString());
                    o.put("payAmount", "0");
                    o.put("promoCode", "");
                    o.put("promoDiscount", "");
                    o.put("paymentMethod", "");
                    if (getIntent().getBooleanExtra(AppConstant.EXTRA_2, false))
                        o.put("CategoryID", "35");//weekly
                    else
                        o.put("CategoryID", "16");//monthly
                    Log.d("object", o.toString());
                    showLoadingDialog("");
                    postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/CategoryPackage", o, 1);
//                    ((CheckOutActivity) getActivity()).changeFragmentPosition(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                MyApp.popMessage("Alert!", "Please select delivery address first.", getContext());
        }
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 1) {
            if (o.optString("status").equals("success")) {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setMessage("Thank you user!\nYour order has been placed successfully.");
                b.setTitle("Success");
                b.setCancelable(false);
                b.setIcon(R.mipmap.ic_launcher);
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        finishAffinity();
                    }
                });
                b.create().show();
            } else {
                MyApp.popMessage("Error!", o.optString("message"), getContext());
            }

        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        MyApp.popMessage("Error", "Time-out error occurred, please try again.", getContext());
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        MyApp.popMessage("Error", error, getContext());
        dismissDialog();
    }

    private Context getContext() {
        return SubscriptionCheckOutActivity.this;
    }

    @Override
    public void onResume() {
        super.onResume();
        addressChanges();
    }

    private void addressChanges() {
        try {
            Address a = SingleInstance.getInstance().getSelectedAddress();
            if (a != null) {
                shippingId = a.getAddID();
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
                List<Address> addresses = MyApp.getApplication().readAddress();
                if (addresses.size() > 0) {
                    a = null;
                    for (int i = 0; i < addresses.size(); i++) {
                        if (addresses.get(i).getAddIsActive()) {
                            a = addresses.get(i);
                        }
                    }
                    if (a != null) {
                        shippingId = a.getAddID();
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
                    }
                } else {
                    shippingId = a.getAddID();
                    isAddressSelected = false;
                    txt_add_address.setVisibility(View.VISIBLE);
                    txt_address.setText("");
                    txt_address_type.setText("");
                    img_change.setVisibility(View.GONE);
                    txt_name.setText("");
                }

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

    public static class DatePickerFragment extends DialogFragment {
        DatePickerDialog.OnDateSetListener ondateSet;
        Calendar c;
        int year = 0, month = 0, day = 0;

        public DatePickerFragment() {
        }

        public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
            ondateSet = ondate;
        }

        public static DatePickerFragment newInstance(Bundle bundle) {
            DatePickerFragment myFragment = new DatePickerFragment();
            myFragment.setArguments(bundle);
            return myFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // final Calendar c = Calendar.getInstance();
//if else for null arguments
            if (getArguments() != null) {
                year = getArguments().getInt("year");
                month = getArguments().getInt("month");
                day = getArguments().getInt("day");
                c = Calendar.getInstance();
                c.set(year, month, day);

            } else {
                c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                Log.d("else", "else");
            }


            DatePickerDialog picker = new DatePickerDialog(getActivity(),
                    ondateSet, year, month, day);
            picker.getDatePicker().setMinDate(c.getTime().getTime());
            Log.d("picker timestamp", c.getTime().getTime() + "");
            return picker;
        }
    }
}
