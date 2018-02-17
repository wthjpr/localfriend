package com.localfriend;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bendik.simplerangeview.SimpleRangeView;

public class SubscriptionCheckOutActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private Product product;
    private TextView txt_total2, txt_total1, txt_discount, txt_address_type, txt_name, txt_address, txt_title, txt_start_date;
    private EditText edt_note;
    private ImageView txt_add_address, img_change;
    private TextView tv_make_payment, txt_apply;
    private boolean isAddressSelected = false;
    private String dateSelected;
    private String shippingId = null;
    private EditText edt_promo;

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

        showDialog();
    }

    private void setupUiElements() {
        txt_apply = findViewById(R.id.txt_apply);
        edt_promo = findViewById(R.id.edt_promo);
        txt_total2 = findViewById(R.id.txt_total2);
        txt_discount = findViewById(R.id.txt_discount);
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
        setTouchNClick(R.id.txt_apply);

        dateSelected = MyApp.millsToDate(System.currentTimeMillis());
        if (getIntent().getBooleanExtra(AppConstant.EXTRA_2, false)) {
            txt_total2.setText("Rs. " + product.getpPrice());//weekly // + "/ "+product.getpName()
            tv_make_payment.setText("Pay Rs. " + product.getpPrice());
        } else {
            txt_total2.setText("Rs. " + product.getpPrice());// + "/ mo"
            tv_make_payment.setText("Pay Rs. " + product.getpPrice());
        }

        txt_total1.setText("Total Price :- Rs. " + product.getpPrice());
        txt_start_date.setText("Start From : " + dateSelected);
    }

    private JSONArray scheduleArr;

    private JSONArray getScheduleArr() {
        return scheduleArr;
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
                    o.put("suhedule", getScheduleArr());
                    if (promoDiscount > 0) {
                        o.put("promoCode", "NEW30");
                        o.put("promoDiscount", 70);
                    } else {
                        o.put("promoCode", "");
                        o.put("promoDiscount", "");
                    }


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
        } else if (v == txt_apply) {
            showLoadingShadowDialog("");
            JSONObject o = new JSONObject();

            try {
                o.put("packageID", product.getpId());
                o.put("promocode", edt_promo.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/PackagePromoApply?",
                    o, 2);
        }
    }

    private int promoDiscount = 0;

    private int finalPayment = 0;

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
        } else if (callNumber == 2) {
//            {"status":"success","message":"Promo Code Successfully Applied","discount":900}
            dismissDialog();
            if (o.optString("status").equals("success")) {
                promoDiscount = o.optInt("discount");
                MyApp.showMassage(getContext(), o.optString("message"));
                txt_discount.setText("Rs. " + promoDiscount);
                tv_make_payment.setText("Pay Rs. " + (Integer.parseInt(product.getpPrice()) - promoDiscount));
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

        setValuesScheduleArray();
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

    private void setValuesScheduleArray() {

        scheduleArr = new JSONArray();

        try {
            JSONObject breakfastObject = new JSONObject();
            breakfastObject.put("meals", "Breakfast");

            JSONArray bfArr = new JSONArray();
            JSONObject bfo = new JSONObject();
            bfo.put("addressID", shippingId);
            bfo.put("day", "Monday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");

            bfArr.put(bfo);
            bfo = new JSONObject();
            bfo.put("addressID", shippingId);
            bfo.put("day", "Tuesday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId);
            bfo.put("day", "Wednesday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId);
            bfo.put("day", "Thursday");
            bfo.put("timestemp", "8:00 AM- 8:30 AM");
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId);
            bfo.put("day", "Friday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId);
            bfo.put("day", "Saturday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId);
            bfo.put("day", "Sunday");
            bfo.put("timestemp", "8:00 AM - 8:30 AM");
            bfArr.put(bfo);

            breakfastObject.put("mealsday", bfArr);
            scheduleArr.put(breakfastObject);

            JSONObject lunchObject = new JSONObject();
            lunchObject.put("meals", "Lunch");

            JSONArray lunchArr = new JSONArray();
            JSONObject lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Monday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Tuesday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Wednesday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Thursday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Friday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Saturday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId);
            lo.put("day", "Sunday");
            lo.put("timestemp", "1:00 PM - 1:30 PM");
            lunchArr.put(lo);

            lunchObject.put("mealsday", lunchArr);
            scheduleArr.put(lunchObject);

            JSONObject dinnerObject = new JSONObject();
            lunchObject.put("meals", "Dinner");
            JSONArray dinnerArr = new JSONArray();
            JSONObject d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Monday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Tuesday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Wednesday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Thursday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Friday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Saturday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId);
            d.put("day", "Sunday");
            d.put("timestemp", "8:00 PM - 8:30 PM");
            dinnerArr.put(d);

            dinnerObject.put("mealsday", dinnerArr);
            scheduleArr.put(dinnerObject);
            Log.d("timings", scheduleArr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Dialog dialog;

    private void showDialog() {


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_schedule_meal);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        chk_break_fast = dialog.findViewById(R.id.chk_break_fast);
        chk_lunch = dialog.findViewById(R.id.chk_lunch);
        chk_dinner = dialog.findViewById(R.id.chk_dinner);

// Schedule Label Above the layout

        tv_schedule_breakfast = dialog.findViewById(R.id.tv_schedule_breakfast);
        tv_schedule_breakfast.setVisibility(View.VISIBLE);
        tv_schedule_lunch = dialog.findViewById(R.id.tv_schedule_lunch);
        tv_schedule_lunch.setVisibility(View.VISIBLE);
        tv_schedule_dinner = dialog.findViewById(R.id.tv_schedule_dinner);
        tv_schedule_dinner.setVisibility(View.VISIBLE);


//Linear layout the layout

        lnr_breakfast = dialog.findViewById(R.id.lnr_breakfast);
        lnr_breakfast.setVisibility(View.VISIBLE);
        lnr_lunch = dialog.findViewById(R.id.lnr_lunch);
        lnr_lunch.setVisibility(View.VISIBLE);
        lnr_dinner = dialog.findViewById(R.id.lnr_dinner);
        lnr_dinner.setVisibility(View.VISIBLE);

// time range texviews
        tv_breakfast_time = dialog.findViewById(R.id.tv_breakfast_time);
        tv_lunch_time = dialog.findViewById(R.id.tv_lunch_time);
        tv_dinner_time = dialog.findViewById(R.id.tv_dinner_time);

        //Range bars for setting time range
        rangeView = dialog.findViewById(R.id.rangeview);
        {
            rangeView.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
                @Nullable
                @Override
                public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
                    return labels[i];
                }
            });
            rangeView.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
                @Override
                public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangeView.setMinDistance(1);
                    rangeView.setMaxDistance(1);
                    rangeView.setMovable(true);
                    breakfast_end = labels[i];
                    tv_breakfast_time.setText(breakfast_start + "-" + breakfast_end);
                }

                @Override
                public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangeView.setMinDistance(1);
                    rangeView.setMaxDistance(1);
                    rangeView.setMovable(true);
                    breakfast_start = labels[i];
                    tv_breakfast_time.setText(breakfast_start + "-" + breakfast_end);
                }
            });

            rangeView.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
            rangeView.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangeView.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangeView.setActiveFocusThumbAlpha(0.26f);
        }
        rangebar_dinner = dialog.findViewById(R.id.rangebar_dinner);
        {


            rangebar_dinner.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
                @Nullable
                @Override
                public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
                    return labelsDinner[i];
                }
            });
            rangebar_dinner.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
                @Override
                public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangebar_dinner.setMinDistance(1);
                    rangebar_dinner.setMaxDistance(1);
                    rangebar_dinner.setMovable(true);
                    dinner_start = labelsDinner[i];
                    tv_dinner_time.setText(dinner_start + "-" + dinner_end);

                }

                @Override
                public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangebar_dinner.setMinDistance(1);
                    rangebar_dinner.setMaxDistance(1);
                    rangebar_dinner.setMovable(true);
                    dinner_end = labelsDinner[i];
                    tv_dinner_time.setText(dinner_start + "-" + dinner_end);
                }
            });

            rangebar_dinner.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_dinner.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_dinner.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_dinner.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_dinner.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_dinner.setActiveFocusThumbAlpha(0.26f);
        }
        rangebar_lunch = dialog.findViewById(R.id.rangebar_lunch);
        {

            rangebar_lunch.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
                @Nullable
                @Override
                public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
                    return labelsLunch[i];
                }
            });
            rangebar_lunch.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
                @Override
                public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangebar_lunch.setMinDistance(1);
                    rangebar_lunch.setMaxDistance(1);
                    rangebar_lunch.setMovable(true);
                    lunch_start = labelsLunch[i];
                    tv_lunch_time.setText(lunch_start + "-" + lunch_end);
                }

                @Override
                public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                    rangebar_lunch.setMinDistance(1);
                    rangebar_lunch.setMaxDistance(1);
                    rangebar_lunch.setMovable(true);
                    lunch_end = labelsLunch[i];
                    tv_lunch_time.setText(lunch_start + "-" + lunch_end);
                }
            });

            rangebar_lunch.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_lunch.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_lunch.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_lunch.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_lunch.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
            rangebar_lunch.setActiveFocusThumbAlpha(0.26f);

        }

//Textview for showing selected days.

        tv_selected_days = dialog.findViewById(R.id.tv_selected_days);
        tv_selected_days_lunch = dialog.findViewById(R.id.tv_selected_days_lunch);
        tv_selected_days_dinner = dialog.findViewById(R.id.tv_selected_days_dinner);

////////////////////////////////////////////Breakfast/////////////////////////////////////////////////
        //relative layout for breakfast days

        rel_sun_bf = dialog.findViewById(R.id.rel_sun_bf);
        rel_mon_bf = dialog.findViewById(R.id.rel_mon_bf);
        rel_tue_bf = dialog.findViewById(R.id.rel_tue_bf);
        rel_wed_bf = dialog.findViewById(R.id.rel_wed_bf);
        rel_thurs_bf = dialog.findViewById(R.id.rel_thurs_bf);
        rel_fri_bf = dialog.findViewById(R.id.rel_fri_bf);
        rel_sat_bf = dialog.findViewById(R.id.rel_sat_bf);

        //Textview for breakfast days
        tv_sunday_bf = dialog.findViewById(R.id.tv_sunday_bf);
        tv_monday_bf = dialog.findViewById(R.id.tv_monday_bf);
        tv_tuesday_bf = dialog.findViewById(R.id.tv_tuesday_bf);
        tv_wednesday_bf = dialog.findViewById(R.id.tv_wednesday_bf);
        tv_thursday_bf = dialog.findViewById(R.id.tv_thursday_bf);
        tv_friday_bf = dialog.findViewById(R.id.tv_friday_bf);
        tv_saturday_bf = dialog.findViewById(R.id.tv_saturday_bf);

        rel_sun_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sun_bfc == 0) {
                    rel_sun_bf.setSelected(true);
                    rel_sun_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sun_bfc = 1;
                    breakfast = breakfast + "Sun,";

                } else {
                    rel_sun_bf.setSelected(false);
                    rel_sun_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_sunday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_sun_bfc = 0;
                    breakfast = breakfast.replace("Sun,", "");
                }
                tv_selected_days.setText(breakfast);

            }
        });
        rel_mon_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_mon_bfc == 0) {
                    rel_mon_bf.setSelected(true);
                    rel_mon_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_monday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_mon_bfc = 1;
                    breakfast = breakfast + "Mon,";

                } else {
                    rel_mon_bf.setSelected(false);
                    rel_mon_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_monday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_mon_bfc = 0;
                    breakfast = breakfast.replace("Mon,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_tue_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_tue_bfc == 0) {
                    rel_tue_bf.setSelected(true);
                    rel_tue_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_tuesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_tue_bfc = 1;
                    breakfast = breakfast + "Tue,";

                } else {
                    rel_tue_bf.setSelected(false);
                    rel_tue_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_tuesday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_tue_bfc = 0;
                    breakfast = breakfast.replace("Tue,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_wed_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wed_bfc == 0) {
                    rel_wed_bf.setSelected(true);
                    rel_wed_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wed_bfc = 1;
                    breakfast = breakfast + "Wed,";

                } else {
                    rel_wed_bf.setSelected(false);
                    rel_wed_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_wednesday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_wed_bfc = 0;
                    breakfast = breakfast.replace("Wed,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_thurs_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thurs_bfc == 0) {
                    rel_thurs_bf.setSelected(true);
                    rel_thurs_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thurs_bfc = 1;
                    breakfast = breakfast + "Thu,";
                } else {
                    rel_thurs_bf.setSelected(false);
                    rel_thurs_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_thursday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_thurs_bfc = 0;
                    breakfast = breakfast.replace("Thu,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_fri_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fri_bfc == 0) {
                    rel_fri_bf.setSelected(true);
                    rel_fri_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fri_bfc = 1;
                    breakfast = breakfast + "Fri,";
                } else {
                    rel_fri_bf.setSelected(false);
                    rel_fri_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_friday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_fri_bfc = 0;
                    breakfast = breakfast.replace("Fri,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_sat_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sat_bfc == 0) {
                    rel_sat_bf.setSelected(true);
                    rel_sat_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sat_bfc = 1;
                    breakfast = breakfast + "Sat,";

                } else {
                    rel_sat_bf.setSelected(false);
                    rel_sat_bf.setBackgroundResource(R.drawable.edt_bg);
                    tv_saturday_bf.setTextColor(Color.parseColor("#999999"));
                    rel_sat_bfc = 0;
                    breakfast = breakfast.replace("Sat,", "");
                }
                tv_selected_days.setText(breakfast);
            }
        });


////////////////////////////////////////LUNCH//////////////////////////////////////////
        //relative layout for lunch days

        rel_sun_lnch = dialog.findViewById(R.id.rel_sun_lnch);
        rel_mon_lnch = dialog.findViewById(R.id.rel_mon_lnch);
        rel_tue_lnch = dialog.findViewById(R.id.rel_tue_lnch);
        rel_wed_lnch = dialog.findViewById(R.id.rel_wed_lnch);
        rel_thurs_lnch = dialog.findViewById(R.id.rel_thurs_lnch);
        rel_fri_lnch = dialog.findViewById(R.id.rel_fri_lnch);
        rel_sat_lnch = dialog.findViewById(R.id.rel_sat_lnch);

        //Textview for lunch days

        tv_sunday_lnch = dialog.findViewById(R.id.tv_sunday_lnch);
        tv_monday_lnch = dialog.findViewById(R.id.tv_monday_lnch);
        tv_tuesday_lnch = dialog.findViewById(R.id.tv_tuesday_lnch);
        tv_wednesday_lnch = dialog.findViewById(R.id.tv_wednesday_lnch);
        tv_thursday_lnch = dialog.findViewById(R.id.tv_thursday_lnch);
        tv_friday_lnch = dialog.findViewById(R.id.tv_friday_lnch);
        tv_saturday_lnch = dialog.findViewById(R.id.tv_saturday_lnch);

        tv_sunday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sun_lnchc == 0) {
                    rel_sun_lnch.setSelected(true);
                    rel_sun_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sun_lnchc = 1;
                    lunch = lunch + "Sun,";
                } else {
                    rel_sun_lnch.setSelected(false);
                    rel_sun_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_sunday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_sun_lnchc = 0;
                    lunch = lunch.replace("Sun,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_monday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_mon_lnchc == 0) {
                    rel_mon_lnch.setSelected(true);
                    rel_mon_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_monday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_mon_lnchc = 1;
                    lunch = lunch + "Mon,";
                } else {
                    rel_mon_lnch.setSelected(false);
                    rel_mon_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_monday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_mon_lnchc = 0;
                    lunch = lunch.replace("Mon,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_tuesday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_tue_lnchc == 0) {
                    rel_tue_lnch.setSelected(true);
                    rel_tue_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_tuesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_tue_lnchc = 1;
                    lunch = lunch + "Tue,";
                } else {
                    rel_tue_lnch.setSelected(false);
                    rel_tue_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_tuesday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_tue_lnchc = 0;
                    lunch = lunch.replace("Tue,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_wednesday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wed_lnchc == 0) {
                    rel_wed_lnch.setSelected(true);
                    rel_wed_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wed_lnchc = 1;
                    lunch = lunch + "Wed,";
                } else {
                    rel_wed_lnch.setSelected(false);
                    rel_wed_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_wednesday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_wed_lnchc = 0;
                    lunch = lunch.replace("Wed,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_thursday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thurs_lnchc == 0) {
                    rel_thurs_lnch.setSelected(true);
                    rel_thurs_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thurs_lnchc = 1;
                    lunch = lunch + "Thu,";
                } else {
                    rel_thurs_lnch.setSelected(false);
                    rel_thurs_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_thursday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_thurs_lnchc = 0;
                    lunch = lunch.replace("Thu,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_friday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fri_lnchc == 0) {
                    rel_fri_lnch.setSelected(true);
                    rel_fri_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fri_lnchc = 1;
                    lunch = lunch + "Fri,";
                } else {
                    rel_fri_lnch.setSelected(false);
                    rel_fri_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_friday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_fri_lnchc = 0;
                    lunch = lunch.replace("Fri,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        tv_saturday_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sat_lnchc == 0) {
                    rel_sat_lnch.setSelected(true);
                    rel_sat_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sat_lnchc = 1;
                    lunch = lunch + "Sat,";
                } else {
                    rel_sat_lnch.setSelected(false);
                    rel_sat_lnch.setBackgroundResource(R.drawable.edt_bg);
                    tv_saturday_lnch.setTextColor(Color.parseColor("#999999"));
                    rel_sat_lnchc = 0;
                    lunch = lunch.replace("Sat,", "");
                }
                tv_selected_days_lunch.setText(lunch);
            }
        });

/////////////Dinner///////////////////////////////////////////////////////
        //relative layout for Dinner days

        rel_sun = dialog.findViewById(R.id.rel_sun);
        rel_mon = dialog.findViewById(R.id.rel_mon);
        rel_tue = dialog.findViewById(R.id.rel_tue);
        rel_wed = dialog.findViewById(R.id.rel_wed);
        rel_thurs = dialog.findViewById(R.id.rel_thurs);
        rel_fri = dialog.findViewById(R.id.rel_fri);
        rel_sat = dialog.findViewById(R.id.rel_sat);

        //Textview for Dinner days

        tv_sunday = dialog.findViewById(R.id.tv_sunday);
        tv_monday = dialog.findViewById(R.id.tv_monday);
        tv_tuesday = dialog.findViewById(R.id.tv_tuesday);
        tv_wednesday = dialog.findViewById(R.id.tv_wednesday);
        tv_thursday = dialog.findViewById(R.id.tv_thursday);
        tv_friday = dialog.findViewById(R.id.tv_friday);
        tv_saturday = dialog.findViewById(R.id.tv_saturday);

        tv_sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sunc == 0) {
                    rel_sun.setSelected(true);
                    rel_sun.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sunc = 1;
                    dinner = dinner + "Sun,";

                } else {
                    rel_sun.setSelected(false);
                    rel_sun.setBackgroundResource(R.drawable.edt_bg);
                    tv_sunday.setTextColor(Color.parseColor("#999999"));
                    rel_sunc = 0;
                    dinner = dinner.replace("Sun,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_monc == 0) {
                    rel_mon.setSelected(true);
                    rel_mon.setBackgroundResource(R.drawable.selected_bg);
                    tv_monday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_monc = 1;
                    dinner = dinner + "Mon,";
                } else {
                    rel_mon.setSelected(false);
                    rel_mon.setBackgroundResource(R.drawable.edt_bg);
                    tv_monday.setTextColor(Color.parseColor("#999999"));
                    rel_monc = 0;
                    dinner = dinner.replace("Mon,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_tuec == 0) {
                    rel_tue.setSelected(true);
                    rel_tue.setBackgroundResource(R.drawable.selected_bg);
                    tv_tuesday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_tuec = 1;
                    dinner = dinner + "Tue,";
                } else {
                    rel_tue.setSelected(false);
                    rel_tue.setBackgroundResource(R.drawable.edt_bg);
                    tv_tuesday.setTextColor(Color.parseColor("#999999"));
                    rel_tuec = 0;
                    dinner = dinner.replace("Tue,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wedc == 0) {
                    rel_wed.setSelected(true);
                    rel_wed.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wedc = 1;
                    dinner = dinner + "Wed,";
                } else {
                    rel_wed.setSelected(false);
                    rel_wed.setBackgroundResource(R.drawable.edt_bg);
                    tv_wednesday.setTextColor(Color.parseColor("#999999"));
                    rel_wedc = 0;
                    dinner = dinner.replace("Wed,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thursc == 0) {
                    rel_thurs.setSelected(true);
                    rel_thurs.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thursc = 1;
                    dinner = dinner + "Thu,";
                } else {
                    rel_thurs.setSelected(false);
                    rel_thurs.setBackgroundResource(R.drawable.edt_bg);
                    tv_thursday.setTextColor(Color.parseColor("#999999"));
                    rel_thursc = 0;
                    dinner = dinner.replace("Thu,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fric == 0) {
                    rel_fri.setSelected(true);
                    rel_fri.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fric = 1;
                    dinner = dinner + "Fri,";
                } else {
                    rel_fri.setSelected(false);
                    rel_fri.setBackgroundResource(R.drawable.edt_bg);
                    tv_friday.setTextColor(Color.parseColor("#999999"));
                    rel_fric = 0;
                    dinner = dinner.replace("Fri,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        tv_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_satc == 0) {
                    rel_sat.setSelected(true);
                    rel_sat.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_satc = 1;
                    dinner = dinner + "Sat,";
                } else {
                    rel_sat.setSelected(false);
                    rel_sat.setBackgroundResource(R.drawable.edt_bg);
                    tv_saturday.setTextColor(Color.parseColor("#999999"));
                    rel_satc = 0;
                    dinner = dinner.replace("Sat,", "");
                }
                tv_selected_days_dinner.setText(dinner);
            }
        });


        chk_break_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_break_fast.isChecked()) {
                    tv_schedule_breakfast.setVisibility(View.VISIBLE);
                    lnr_breakfast.setVisibility(View.VISIBLE);
                } else if (!chk_break_fast.isChecked()) {
                    tv_schedule_breakfast.setVisibility(View.GONE);
                    lnr_breakfast.setVisibility(View.GONE);
                }
            }
        });

        chk_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_lunch.isChecked()) {
                    tv_schedule_lunch.setVisibility(View.VISIBLE);
                    lnr_lunch.setVisibility(View.VISIBLE);
                } else if (!chk_lunch.isChecked()) {
                    tv_schedule_lunch.setVisibility(View.GONE);
                    lnr_lunch.setVisibility(View.GONE);
                }
            }
        });
        chk_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_dinner.isChecked()) {
                    tv_schedule_dinner.setVisibility(View.VISIBLE);
                    lnr_dinner.setVisibility(View.VISIBLE);
                } else if (!chk_dinner.isChecked()) {
                    tv_schedule_dinner.setVisibility(View.GONE);
                    lnr_dinner.setVisibility(View.GONE);
                }
            }
        });


        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }


    CheckBox chk_break_fast, chk_lunch, chk_dinner;
    TextView tv_breakfast_time, tv_lunch_time, tv_dinner_time;
    //   RangeBar rangebar, rangebar_dinner, rangebar_lunch;
    TextView tv_schedule_breakfast, tv_schedule_lunch, tv_schedule_dinner;
    TextView tv_selected_days, tv_selected_days_lunch, tv_selected_days_dinner;
    RelativeLayout rel_sun_bf, rel_mon_bf, rel_tue_bf, rel_wed_bf, rel_thurs_bf, rel_fri_bf, rel_sat_bf;
    TextView tv_sunday_bf, tv_monday_bf, tv_tuesday_bf, tv_wednesday_bf, tv_thursday_bf, tv_friday_bf, tv_saturday_bf;

    RelativeLayout rel_sun_lnch, rel_mon_lnch, rel_tue_lnch, rel_wed_lnch, rel_thurs_lnch, rel_fri_lnch, rel_sat_lnch;
    TextView tv_sunday_lnch, tv_monday_lnch, tv_tuesday_lnch, tv_wednesday_lnch, tv_thursday_lnch, tv_friday_lnch, tv_saturday_lnch;

    RelativeLayout rel_sun, rel_mon, rel_tue, rel_wed, rel_thurs, rel_fri, rel_sat;
    int rel_sun_lnchc = 0, rel_mon_lnchc = 0, rel_tue_lnchc = 0, rel_wed_lnchc = 0, rel_thurs_lnchc = 0, rel_fri_lnchc = 0, rel_sat_lnchc = 0;
    int rel_sunc = 0, rel_monc = 0, rel_tuec = 0, rel_wedc = 0, rel_thursc = 0, rel_fric = 0, rel_satc = 0;
    int rel_sun_bfc = 0, rel_mon_bfc = 0, rel_tue_bfc = 0, rel_wed_bfc = 0, rel_thurs_bfc = 0, rel_fri_bfc = 0, rel_sat_bfc = 0;
    TextView tv_sunday, tv_monday, tv_tuesday, tv_wednesday, tv_thursday, tv_friday, tv_saturday;
    String breakfast = "", lunch = "", dinner = "";

    LinearLayout lnr_breakfast, lnr_lunch, lnr_dinner;
    final String[] labels = new String[]{"7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00"};
    final String[] labelsLunch = new String[]{"12:00", "12:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00"};
    final String[] labelsDinner = new String[]{"7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00"};
    SimpleRangeView rangeView, rangebar_dinner, rangebar_lunch;
    String breakfast_start = "8:00", breakfast_end = "8:30";
    String lunch_start = "13:00", lunch_end = "13:30";
    String dinner_start = "20:00", dinner_end = "20:30";
}
