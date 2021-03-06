package com.localfriend;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Address;
import com.localfriend.model.Product;
import com.localfriend.utils.AppConstant;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

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
    private String dateSelectedSend;
    private String shippingId1 = null;
    private String shippingId2 = null;
    private String shippingId3 = null;
    private EditText edt_promo;
    private TextView txt_shipping_address;
    private CardView card_first_address, card_main_address;
    private TextView txt_address1, txt_first_times;
    private TextView txt_sun_b1, txt_mon_b1, txt_tue_b1, txt_wed_b1, txt_thu_b1, txt_fri_b1, txt_sat_b1;
    private TextView txt_second_times, txt_sun_l1, txt_mon_l1, txt_tue_l1, txt_wed_l1, txt_thu_l1, txt_fri_l1, txt_sat_l1;
    private TextView txt_third_times, txt_sun_d1, txt_mon_d1, txt_tue_d1, txt_wed_d1, txt_thu_d1, txt_fri_d1, txt_sat_d1;
    private CardView card_add_second_address;
    private TextView txt_add_address2, txt_add_address3;
    private CardView card_second_address;
    private TextView txt_address2, txt_bf_times_a2, txt_sun_b2, txt_mon_b2, txt_tue_b2, txt_wed_b2, txt_thu_b2, txt_fri_b2, txt_sat_b2;
    private TextView txt_second_times_a2, txt_sun_l2, txt_mon_l2, txt_tue_l2, txt_wed_l2, txt_thu_l2, txt_fri_l2, txt_sat_l2;
    private TextView txt_third_times_a2, txt_sun_d2, txt_mon_d2, txt_tue_d2, txt_wed_d2, txt_thu_d2, txt_fri_d2, txt_sat_d2;
    private CardView card_add_third_address;
    private CardView card_third_address;
    private TextView txt_address3, txt_bf_times_a3, txt_sun_b3, txt_mon_b3, txt_tue_b3, txt_wed_b3, txt_thu_b3, txt_fri_b3, txt_sat_b3;
    private TextView txt_second_times_a3, txt_sun_l3, txt_mon_l3, txt_tue_l3, txt_wed_l3, txt_thu_l3, txt_fri_l3, txt_sat_l3;
    private TextView txt_third_times_a3, txt_sun_d3, txt_mon_d3, txt_tue_d3, txt_wed_d3, txt_thu_d3, txt_fri_d3, txt_sat_d3;
    private TextView txt_head1;
    private String title = "";
    private RelativeLayout rl_bf_time_a2, rl_first_time, rl_second_time, rl_third_time, rl_second_time_a2, rl_third_time_a2, rl_bf_time_a3, rl_second_time_a3, rl_third_time_a3;
    private LinearLayout ll_bf_times_a2, ll_bf_times_a3, ll_first_times, ll_second_times, ll_third_times, ll_second_times_a2, ll_third_times_a2, ll_second_times_a3, ll_third_times_a3;

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
        title = getIntent().getStringExtra(AppConstant.EXTRA_1);
        mTitle.setText(title);
        actionBar.setTitle("");
        product = SingleInstance.getInstance().getSubscriptionProduct();
//        showLoadingDialog("");
//        getCallWithHeader(AppConstant.BASE_URL + "CheckOut", 1);

        setupUiElements();
        txt_title.setText(mTitle.getText().toString());


    }

    private void setupUiElements() {
        rl_first_time = findViewById(R.id.rl_first_time);
        ll_first_times = findViewById(R.id.ll_first_times);
        rl_second_time = findViewById(R.id.rl_second_time);
        ll_second_times = findViewById(R.id.ll_second_times);
        rl_third_time = findViewById(R.id.rl_third_time);
        ll_third_times = findViewById(R.id.ll_third_times);
        rl_bf_time_a2 = findViewById(R.id.rl_bf_time_a2);
        rl_second_time_a2 = findViewById(R.id.rl_second_time_a2);
        ll_bf_times_a2 = findViewById(R.id.ll_bf_times_a2);
        ll_bf_times_a3 = findViewById(R.id.ll_bf_times_a3);
        ll_second_times_a2 = findViewById(R.id.ll_second_times_a2);
        rl_third_time_a2 = findViewById(R.id.rl_third_time_a2);
        ll_third_times_a2 = findViewById(R.id.ll_third_times_a2);
        rl_bf_time_a3 = findViewById(R.id.rl_bf_time_a3);
        rl_second_time_a3 = findViewById(R.id.rl_second_time_a3);
        ll_second_times_a3 = findViewById(R.id.ll_second_times_a3);
        rl_third_time_a3 = findViewById(R.id.rl_third_time_a3);
        ll_third_times_a3 = findViewById(R.id.ll_third_times_a3);

        txt_head1 = findViewById(R.id.txt_head1);
        card_add_third_address = findViewById(R.id.card_add_third_address);
        txt_add_address3 = findViewById(R.id.txt_add_address3);
        card_third_address = findViewById(R.id.card_third_address);
        txt_address3 = findViewById(R.id.txt_address3);

        txt_third_times_a3 = findViewById(R.id.txt_third_times_a3);
        txt_sun_d3 = findViewById(R.id.txt_sun_d3);
        txt_mon_d3 = findViewById(R.id.txt_mon_d3);
        txt_tue_d3 = findViewById(R.id.txt_tue_d3);
        txt_wed_d3 = findViewById(R.id.txt_wed_d3);
        txt_thu_d3 = findViewById(R.id.txt_thu_d3);
        txt_fri_d3 = findViewById(R.id.txt_fri_d3);
        txt_sat_d3 = findViewById(R.id.txt_sat_d3);

        txt_second_times_a3 = findViewById(R.id.txt_second_times_a3);
        txt_sun_l3 = findViewById(R.id.txt_sun_l3);
        txt_mon_l3 = findViewById(R.id.txt_mon_l3);
        txt_tue_l3 = findViewById(R.id.txt_tue_l3);
        txt_wed_l3 = findViewById(R.id.txt_wed_l3);
        txt_thu_l3 = findViewById(R.id.txt_thu_l3);
        txt_fri_l3 = findViewById(R.id.txt_fri_l3);
        txt_sat_l3 = findViewById(R.id.txt_sat_l3);

        txt_bf_times_a3 = findViewById(R.id.txt_bf_times_a3);
        txt_sun_b3 = findViewById(R.id.txt_sun_b3);
        txt_mon_b3 = findViewById(R.id.txt_mon_b3);
        txt_tue_b3 = findViewById(R.id.txt_tue_b3);
        txt_wed_b3 = findViewById(R.id.txt_wed_b3);
        txt_thu_b3 = findViewById(R.id.txt_thu_b3);
        txt_fri_b3 = findViewById(R.id.txt_fri_b3);
        txt_sat_b3 = findViewById(R.id.txt_sat_b3);

        txt_third_times_a2 = findViewById(R.id.txt_third_times_a2);
        txt_sun_d2 = findViewById(R.id.txt_sun_d2);
        txt_mon_d2 = findViewById(R.id.txt_mon_d2);
        txt_tue_d2 = findViewById(R.id.txt_tue_d2);
        txt_wed_d2 = findViewById(R.id.txt_wed_d2);
        txt_thu_d2 = findViewById(R.id.txt_thu_d2);
        txt_fri_d2 = findViewById(R.id.txt_fri_d2);
        txt_sat_d2 = findViewById(R.id.txt_sat_d2);

        txt_first_times = findViewById(R.id.txt_first_times);
        txt_mon_b1 = findViewById(R.id.txt_mon_b1);
        txt_tue_b1 = findViewById(R.id.txt_tue_b1);
        txt_wed_b1 = findViewById(R.id.txt_wed_b1);
        txt_thu_b1 = findViewById(R.id.txt_thu_b1);
        txt_fri_b1 = findViewById(R.id.txt_fri_b1);
        txt_sat_b1 = findViewById(R.id.txt_sat_b1);
        txt_sun_b1 = findViewById(R.id.txt_sun_b1);

        txt_second_times = findViewById(R.id.txt_second_times);
        txt_sun_l1 = findViewById(R.id.txt_sun_l1);
        txt_mon_l1 = findViewById(R.id.txt_mon_l1);
        txt_tue_l1 = findViewById(R.id.txt_tue_l1);
        txt_wed_l1 = findViewById(R.id.txt_wed_l1);
        txt_thu_l1 = findViewById(R.id.txt_thu_l1);
        txt_fri_l1 = findViewById(R.id.txt_fri_l1);
        txt_sat_l1 = findViewById(R.id.txt_sat_l1);

        txt_third_times = findViewById(R.id.txt_third_times);
        txt_sun_d1 = findViewById(R.id.txt_sun_d1);
        txt_mon_d1 = findViewById(R.id.txt_mon_d1);
        txt_tue_d1 = findViewById(R.id.txt_tue_d1);
        txt_wed_d1 = findViewById(R.id.txt_wed_d1);
        txt_thu_d1 = findViewById(R.id.txt_thu_d1);
        txt_fri_d1 = findViewById(R.id.txt_fri_d1);
        txt_sat_d1 = findViewById(R.id.txt_sat_d1);

        txt_second_times_a2 = findViewById(R.id.txt_second_times_a2);
        txt_sun_l2 = findViewById(R.id.txt_sun_l2);
        txt_mon_l2 = findViewById(R.id.txt_mon_l2);
        txt_tue_l2 = findViewById(R.id.txt_tue_l2);
        txt_wed_l2 = findViewById(R.id.txt_wed_l2);
        txt_thu_l2 = findViewById(R.id.txt_thu_l2);
        txt_fri_l2 = findViewById(R.id.txt_fri_l2);
        txt_sat_l2 = findViewById(R.id.txt_sat_l2);

        card_add_second_address = findViewById(R.id.card_add_second_address);
        txt_add_address2 = findViewById(R.id.txt_add_address2);
        card_second_address = findViewById(R.id.card_second_address);
        txt_address2 = findViewById(R.id.txt_address2);

        txt_bf_times_a2 = findViewById(R.id.txt_bf_times_a2);
        txt_sun_b2 = findViewById(R.id.txt_sun_b2);
        txt_mon_b2 = findViewById(R.id.txt_mon_b2);
        txt_tue_b2 = findViewById(R.id.txt_tue_b2);
        txt_wed_b2 = findViewById(R.id.txt_wed_b2);
        txt_thu_b2 = findViewById(R.id.txt_thu_b2);
        txt_fri_b2 = findViewById(R.id.txt_fri_b2);
        txt_sat_b2 = findViewById(R.id.txt_sat_b2);

        card_main_address = findViewById(R.id.card_main_address);
        txt_address1 = findViewById(R.id.txt_address1);
        card_first_address = findViewById(R.id.card_first_address);
        txt_shipping_address = findViewById(R.id.txt_shipping_address);
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
        setTouchNClick(R.id.txt_add_address2);
        setTouchNClick(R.id.txt_address3);
        setTouchNClick(R.id.txt_add_address3);

        long milli = System.currentTimeMillis() + (1000*60*60*24);

        dateSelected = MyApp.millsTommddyyyy(milli);

        dateSelectedSend = MyApp.millsToDate(milli);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_add_address) {
            currentAddress = 1;
            isAddressSelected = false;
            startActivity(new Intent(getContext(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true)
                    .putExtra(AppConstant.EXTRA_2, true));
            txt_shipping_address.setVisibility(View.VISIBLE);
        } else if (v == img_change) {
            currentAddress = 1;
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
                    dateSelected =  dayOfMonth  + "/" +(month + 1)+ "/" + year;
                    dateSelectedSend = (month + 1)+ "/" +dayOfMonth  + "/" + year;
                    txt_start_date.setText("Start From : " + (month + 1) + "/" + dayOfMonth + "/" + year);
                }
            });
            fragment.show(getSupportFragmentManager(), null);
        } else if (v == tv_make_payment) {
            if (isDayRemaining(true, false)) {
                MyApp.popMessage("Alert!", "Please add address for the week days.", getContext());
                return;
            }
            if (isAddressSelected) {
                try {

                    if (!title.contains("Breakfast")) {
                        scheduleArr.remove(0);
                    }
                    if (!title.contains("Lunch")) {
                        scheduleArr.remove(1);
                    }
                    if (!title.contains("Dinner")) {
                        scheduleArr.remove(2);
                    }

                    JSONObject o = new JSONObject();

                    o.put("addressID", Long.parseLong(shippingId1));
                    o.put("packageID", Integer.parseInt(product.getpId()));
                    o.put("transactionID", 0);
                    o.put("startdate", dateSelectedSend);
                    o.put("extraNote", edt_note.getText().toString());
                    o.put("payAmount", (Float.parseFloat(product.getpPrice()) - (float) promoDiscount));
                    o.put("suhedule", getScheduleArr());
                    if (promoDiscount > 0) {
                        o.put("promoCode", "NEW30");
                        o.put("promoDiscount", 70.0);
                    } else {
                        o.put("promoCode", "");
                        o.put("promoDiscount", 0);
                    }


                    o.put("paymentMethod", "COD");
                    if (getIntent().getBooleanExtra(AppConstant.EXTRA_2, false))
                        o.put("CategoryID", 35);//weekly
                    else
                        o.put("CategoryID", 16);//monthly
                    Log.d("object", o.toString());
                    showLoadingDialog("");
                    postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/CatPackSubscription", o, 1);
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
        } else if (v == txt_add_address2) {
            currentAddress = 2;
            isAddressSelected = false;
            startActivity(new Intent(getContext(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true)
                    .putExtra(AppConstant.EXTRA_2, true));
        } else if (v == txt_add_address3) {
            currentAddress = 3;
            isAddressSelected = false;
            startActivity(new Intent(getContext(), AddressListActivity.class).putExtra(AppConstant.EXTRA_1, true)
                    .putExtra(AppConstant.EXTRA_2, true));
        }
    }

    private int promoDiscount = 0;


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

    private int currentAddress = 1;

    @Override
    public void onResume() {
        super.onResume();
        addressChanges();
    }

    private void addressChanges() {
        try {
            String shippingId;
            Address a = SingleInstance.getInstance().getSelectedAddress();
            if (a != null) {
                shippingId = a.getAddID();

                if (currentAddress == 1) {
                    shippingId1 = shippingId;
                    card_first_address.setVisibility(View.VISIBLE);

                    setValuesScheduleArray(currentAddress);
                    if (!isDoneOnce && shippingId != null) {
                        isDoneOnce = true;
                        setValuesScheduleArray();
                    }

                    showTimingsDialog(1);
                } else if (currentAddress == 2) {
                    card_second_address.setVisibility(View.VISIBLE);
                    shippingId2 = shippingId;
                    setValuesScheduleArray(currentAddress);
                    if (!isDoneOnce && shippingId != null) {
                        isDoneOnce = true;
                        setValuesScheduleArray();
                    }
                    showTimingsDialog(2);
                } else {
                    card_third_address.setVisibility(View.VISIBLE);
                    shippingId3 = shippingId;
                    setValuesScheduleArray(currentAddress);
                    if (!isDoneOnce && shippingId != null) {
                        isDoneOnce = true;
                        setValuesScheduleArray();
                    }
                    showTimingsDialog(3);
                }

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
                if (currentAddress == 1) {
                    card_main_address.setVisibility(View.GONE);
                    txt_add_address.setVisibility(View.GONE);
                    txt_address1.setText(address);
                    txt_address_type.setText(a.getAddType());
                    img_change.setVisibility(View.GONE);
                    txt_name.setText(a.getAddName());
                } else if (currentAddress == 2) {
                    card_main_address.setVisibility(View.GONE);
                    txt_add_address.setVisibility(View.GONE);
                    txt_address2.setText(address);
                } else {
                    card_add_third_address.setVisibility(View.VISIBLE);
                    txt_address3.setText(address);
                }

            } else {
                if (currentAddress == 1) {
                    card_main_address.setVisibility(View.VISIBLE);
                    isAddressSelected = false;
                    txt_add_address.setVisibility(View.VISIBLE);
                    txt_address1.setText("");
                    txt_address_type.setText("");
                    img_change.setVisibility(View.GONE);
                    txt_name.setText("");
                } else if (currentAddress == 2) {
                    card_main_address.setVisibility(View.GONE);
                    txt_add_address.setVisibility(View.GONE);
                    card_add_second_address.setVisibility(View.VISIBLE);
                    card_second_address.setVisibility(View.VISIBLE);
                } else {
                    card_add_second_address.setVisibility(View.GONE);
                    card_third_address.setVisibility(View.VISIBLE);
                    card_add_third_address.setVisibility(View.VISIBLE);
                }

            }

        } catch (Exception e) {

        }
    }

    private boolean isDoneOnce = false;

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

    String shippingIdToBeSet;
    int checkCounter = 0;
    int daysCounter = 0;

    private void setValuesScheduleArray(int number) {

        if (number == 2) {
            shippingIdToBeSet = shippingId2;
        } else if (number == 1) {
            shippingIdToBeSet = shippingId1;
        } else
            shippingIdToBeSet = shippingId3;
    }

    private void setValuesScheduleArray() {
        scheduleArr = new JSONArray();
        try {
            JSONObject breakfastObject = new JSONObject();
            breakfastObject.put("meals", "Breakfast");

            JSONArray bfArr = new JSONArray();
            JSONObject bfo = new JSONObject();

            bfo.put("addressID", shippingId1);
            bfo.put("day", "Sunday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);

            bfo = new JSONObject();
            bfo.put("addressID", shippingId1);
            bfo.put("day", "Monday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);

            bfArr.put(bfo);
            bfo = new JSONObject();
            bfo.put("addressID", shippingId1);
            bfo.put("day", "Tuesday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId1);
            bfo.put("day", "Wednesday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId1);
            bfo.put("day", "Thursday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId1);
            bfo.put("day", "Friday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);
            bfo = new JSONObject();

            bfo.put("addressID", shippingId1);
            bfo.put("day", "Saturday");
            bfo.put("timestemp", "08:00 AM - 08:30 AM");
            bfo.put("isSelect", true);
            bfo.put("isChecked", false);
            bfArr.put(bfo);


            bfArr.put(bfo);

            breakfastObject.put("mealsday", bfArr);
//            if (title.contains("Breakfast"))
            scheduleArr.put(breakfastObject);

            JSONObject lunchObject = new JSONObject();
            lunchObject.put("meals", "Lunch");

            JSONArray lunchArr = new JSONArray();
            JSONObject lo = new JSONObject();

            lo.put("addressID", shippingId1);
            lo.put("day", "Sunday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Monday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Tuesday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Wednesday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Thursday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Friday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lo = new JSONObject();
            lo.put("addressID", shippingId1);
            lo.put("day", "Saturday");
            lo.put("timestemp", "01:00 PM - 01:30 PM");
            lo.put("isSelect", true);
            lo.put("isChecked", false);
            lunchArr.put(lo);

            lunchObject.put("mealsday", lunchArr);
//            if (title.contains("Lunch")) {
            scheduleArr.put(lunchObject);
//            }


            JSONObject dinnerObject = new JSONObject();
            dinnerObject.put("meals", "Dinner");
            JSONArray dinnerArr = new JSONArray();
            JSONObject d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Sunday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Monday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Tuesday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Wednesday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Thursday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Friday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            d = new JSONObject();
            d.put("addressID", shippingId1);
            d.put("day", "Saturday");
            d.put("timestemp", "08:00 PM - 08:30 PM");
            d.put("isSelect", true);
            d.put("isChecked", false);
            dinnerArr.put(d);

            dinnerObject.put("mealsday", dinnerArr);
//            if (title.contains("Dinner"))
            scheduleArr.put(dinnerObject);
            Log.d("timings", scheduleArr.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Dialog dialog;
    private int extraCounterBf = 0;
    private int extraCounterLunch = 0;
    private int extraCounterDinner = 0;

    int rel_sun_lnchc = 1, rel_mon_lnchc = 1, rel_tue_lnchc = 1, rel_wed_lnchc = 1, rel_thurs_lnchc = 1, rel_fri_lnchc = 1, rel_sat_lnchc = 1;
    int rel_sunc = 1, rel_monc = 1, rel_tuec = 1, rel_wedc = 1, rel_thursc = 1, rel_fric = 1, rel_satc = 1;
    int rel_sun_bfc = 1, rel_mon_bfc = 1, rel_tue_bfc = 1, rel_wed_bfc = 1, rel_thurs_bfc = 1, rel_fri_bfc = 1, rel_sat_bfc = 1;

    private void showTimingsDialog(final int number) {
        extraCounterBf = 0;
        extraCounterLunch = 0;
        extraCounterDinner = 0;
        rel_sun_bfc = 1;
        rel_mon_bfc = 1;
        rel_tue_bfc = 1;
        rel_wed_bfc = 1;
        rel_thurs_bfc = 1;
        rel_fri_bfc = 1;
        rel_sat_bfc = 1;
        rel_sunc = 1;
        rel_monc = 1;
        rel_tuec = 1;
        rel_wedc = 1;
        rel_thursc = 1;
        rel_fric = 1;
        rel_satc = 1;
        rel_sun_lnchc = 1;
        rel_mon_lnchc = 1;
        rel_tue_lnchc = 1;
        rel_wed_lnchc = 1;
        rel_thurs_lnchc = 1;
        rel_fri_lnchc = 1;
        rel_sat_lnchc = 1;
        if (isDialogShown) {
            return;
        }
        makeAllTrue();
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_schedule_meal);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("timestemp", getBreakfastTime());
                    if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("timestemp", getBreakfastTime());

                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("timestemp", getLunchTime());
                    if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("timestemp", getLunchTime());

                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("timestemp", getDinnerTime());
                    if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect"))
                        scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("timestemp", getDinnerTime());


                    if (number == 1) {
                        txt_head1.setVisibility(View.GONE);
                        txt_first_times.setText(getBreakfastTime());
                        txt_second_times.setText(getLunchTime());
                        txt_third_times.setText(getDinnerTime());

                        if (title.contains("Breakfast")) {
                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                txt_sun_b1.setTextColor(Color.WHITE);
                                txt_sun_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_b2.setAlpha(.5f);
                                txt_sun_b2.setTextColor(Color.WHITE);
                                txt_sun_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_b3.setAlpha(.5f);
                                txt_sun_b3.setTextColor(Color.WHITE);
                                txt_sun_b3.setBackgroundResource(R.drawable.selected_bg);

                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_b1.setTextColor(Color.WHITE);
                                txt_mon_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_b2.setAlpha(.5f);
                                txt_mon_b2.setTextColor(Color.WHITE);
                                txt_mon_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_b3.setAlpha(.5f);
                                txt_mon_b3.setTextColor(Color.WHITE);
                                txt_mon_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_b1.setTextColor(Color.WHITE);
                                txt_tue_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_b2.setAlpha(.5f);
                                txt_tue_b2.setTextColor(Color.WHITE);
                                txt_tue_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_b3.setAlpha(.5f);
                                txt_tue_b3.setTextColor(Color.WHITE);
                                txt_tue_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_b1.setTextColor(Color.WHITE);
                                txt_wed_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_b2.setAlpha(.5f);
                                txt_wed_b2.setTextColor(Color.WHITE);
                                txt_wed_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_b3.setAlpha(.5f);
                                txt_wed_b3.setTextColor(Color.WHITE);
                                txt_wed_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_b1.setTextColor(Color.WHITE);
                                txt_thu_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_b2.setAlpha(.5f);
                                txt_thu_b2.setTextColor(Color.WHITE);
                                txt_thu_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_b3.setAlpha(.5f);
                                txt_thu_b3.setTextColor(Color.WHITE);
                                txt_thu_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_b1.setTextColor(Color.WHITE);
                                txt_fri_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_b2.setAlpha(.5f);
                                txt_fri_b2.setTextColor(Color.WHITE);
                                txt_fri_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_b3.setAlpha(.5f);
                                txt_fri_b3.setTextColor(Color.WHITE);
                                txt_fri_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_b1.setTextColor(Color.WHITE);
                                txt_sat_b1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_b2.setAlpha(.5f);
                                txt_sat_b2.setTextColor(Color.WHITE);
                                txt_sat_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_b3.setAlpha(.5f);
                                txt_sat_b3.setTextColor(Color.WHITE);
                                txt_sat_b3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        } else {
                            rl_first_time.setVisibility(View.GONE);
                            ll_first_times.setVisibility(View.GONE);
                            rl_bf_time_a2.setVisibility(View.GONE);
                            ll_bf_times_a2.setVisibility(View.GONE);
                            rl_bf_time_a3.setVisibility(View.GONE);
                            ll_bf_times_a3.setVisibility(View.GONE);
                        }

                        if (title.contains("Lunch")) {
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                                txt_sun_l1.setTextColor(Color.WHITE);
                                txt_sun_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_l2.setAlpha(.5f);
                                txt_sun_l2.setTextColor(Color.WHITE);
                                txt_sun_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_l3.setAlpha(.5f);
                                txt_sun_l3.setTextColor(Color.WHITE);
                                txt_sun_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_l1.setTextColor(Color.WHITE);
                                txt_mon_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_l2.setAlpha(.5f);
                                txt_mon_l2.setTextColor(Color.WHITE);
                                txt_mon_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_l3.setAlpha(.5f);
                                txt_mon_l3.setTextColor(Color.WHITE);
                                txt_mon_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_l1.setTextColor(Color.WHITE);
                                txt_tue_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_l2.setAlpha(.5f);
                                txt_tue_l2.setTextColor(Color.WHITE);
                                txt_tue_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_l3.setAlpha(.5f);
                                txt_tue_l3.setTextColor(Color.WHITE);
                                txt_tue_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_l1.setTextColor(Color.WHITE);
                                txt_wed_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_l2.setAlpha(.5f);
                                txt_wed_l2.setTextColor(Color.WHITE);
                                txt_wed_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_l3.setAlpha(.5f);
                                txt_wed_l3.setTextColor(Color.WHITE);
                                txt_wed_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_l1.setTextColor(Color.WHITE);
                                txt_thu_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_l2.setAlpha(.5f);
                                txt_thu_l2.setTextColor(Color.WHITE);
                                txt_thu_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_l3.setAlpha(.5f);
                                txt_thu_l3.setTextColor(Color.WHITE);
                                txt_thu_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_l1.setTextColor(Color.WHITE);
                                txt_fri_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_l2.setAlpha(.5f);
                                txt_fri_l2.setTextColor(Color.WHITE);
                                txt_fri_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_l3.setAlpha(.5f);
                                txt_fri_l3.setTextColor(Color.WHITE);
                                txt_fri_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_l1.setTextColor(Color.WHITE);
                                txt_sat_l1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_l2.setAlpha(.5f);
                                txt_sat_l2.setTextColor(Color.WHITE);
                                txt_sat_l2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_l3.setAlpha(.5f);
                                txt_sat_l3.setTextColor(Color.WHITE);
                                txt_sat_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        } else {
                            rl_second_time.setVisibility(View.GONE);
                            ll_second_times.setVisibility(View.GONE);
                            rl_second_time_a2.setVisibility(View.GONE);
                            ll_second_times_a2.setVisibility(View.GONE);
                            rl_second_time_a3.setVisibility(View.GONE);
                            ll_second_times_a3.setVisibility(View.GONE);
                        }

                        if (title.contains("Dinner")) {
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                                txt_sun_d1.setTextColor(Color.WHITE);
                                txt_sun_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_d2.setAlpha(.5f);
                                txt_sun_d2.setTextColor(Color.WHITE);
                                txt_sun_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_d3.setAlpha(.5f);
                                txt_sun_d3.setTextColor(Color.WHITE);
                                txt_sun_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_d1.setTextColor(Color.WHITE);
                                txt_mon_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_d2.setAlpha(.5f);
                                txt_mon_d2.setTextColor(Color.WHITE);
                                txt_mon_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_d3.setAlpha(.5f);
                                txt_mon_d3.setTextColor(Color.WHITE);
                                txt_mon_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_d1.setTextColor(Color.WHITE);
                                txt_tue_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_d2.setAlpha(.5f);
                                txt_tue_d2.setTextColor(Color.WHITE);
                                txt_tue_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_tue_d3.setAlpha(.5f);
                                txt_tue_d3.setTextColor(Color.WHITE);
                                txt_tue_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_d1.setTextColor(Color.WHITE);
                                txt_wed_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_d2.setAlpha(.5f);
                                txt_wed_d2.setTextColor(Color.WHITE);
                                txt_wed_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_wed_d3.setAlpha(.5f);
                                txt_wed_d3.setTextColor(Color.WHITE);
                                txt_wed_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_d1.setTextColor(Color.WHITE);
                                txt_thu_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_d2.setAlpha(.5f);
                                txt_thu_d2.setTextColor(Color.WHITE);
                                txt_thu_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_thu_d3.setAlpha(.5f);
                                txt_thu_d3.setTextColor(Color.WHITE);
                                txt_thu_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_d1.setTextColor(Color.WHITE);
                                txt_fri_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_d2.setAlpha(.5f);
                                txt_fri_d2.setTextColor(Color.WHITE);
                                txt_fri_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_fri_d3.setAlpha(.5f);
                                txt_fri_d3.setTextColor(Color.WHITE);
                                txt_fri_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_d1.setTextColor(Color.WHITE);
                                txt_sat_d1.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_d2.setAlpha(.5f);
                                txt_sat_d2.setTextColor(Color.WHITE);
                                txt_sat_d2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sat_d3.setAlpha(.5f);
                                txt_sat_d3.setTextColor(Color.WHITE);
                                txt_sat_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        } else {
                            rl_third_time.setVisibility(View.GONE);
                            ll_third_times.setVisibility(View.GONE);
                            rl_third_time_a2.setVisibility(View.GONE);
                            ll_third_times_a2.setVisibility(View.GONE);
                            rl_third_time_a3.setVisibility(View.GONE);
                            ll_third_times_a3.setVisibility(View.GONE);
                        }

                        if (isDayRemaining(false, false)) {
                            card_add_second_address.setVisibility(View.VISIBLE);
                            card_second_address.setVisibility(View.VISIBLE);
                        }

                    } else if (number == 2) {
                        card_add_second_address.setVisibility(View.GONE);
                        txt_bf_times_a2.setText(getBreakfastTime());
                        txt_second_times_a2.setText(getLunchTime());
                        txt_third_times_a2.setText(getDinnerTime());

                        if (title.contains("Breakfast")) {
                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                                txt_sun_b2.setTextColor(Color.WHITE);
                                txt_sun_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_sun_b3.setTextColor(Color.WHITE);
                                txt_sun_b3.setAlpha(.5f);
                                txt_sun_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_b2.setTextColor(Color.WHITE);
                                txt_mon_b2.setBackgroundResource(R.drawable.selected_bg);

                                txt_mon_b3.setTextColor(Color.WHITE);
                                txt_mon_b3.setAlpha(.5f);
                                txt_mon_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_b2.setTextColor(Color.WHITE);
                                txt_tue_b2.setBackgroundResource(R.drawable.selected_bg);
                                txt_tue_b3.setTextColor(Color.WHITE);
                                txt_tue_b3.setAlpha(.5f);
                                txt_tue_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_b2.setTextColor(Color.WHITE);
                                txt_wed_b2.setBackgroundResource(R.drawable.selected_bg);
                                txt_wed_b3.setTextColor(Color.WHITE);
                                txt_wed_b3.setAlpha(.5f);
                                txt_wed_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_b2.setTextColor(Color.WHITE);
                                txt_thu_b2.setBackgroundResource(R.drawable.selected_bg);
                                txt_thu_b3.setTextColor(Color.WHITE);
                                txt_thu_b3.setAlpha(.5f);
                                txt_thu_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_b2.setTextColor(Color.WHITE);
                                txt_fri_b2.setBackgroundResource(R.drawable.selected_bg);
                                txt_fri_b3.setTextColor(Color.WHITE);
                                txt_fri_b3.setAlpha(.5f);
                                txt_fri_b3.setBackgroundResource(R.drawable.selected_bg);
                            }

                            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_b2.setTextColor(Color.WHITE);
                                txt_sat_b2.setBackgroundResource(R.drawable.selected_bg);
                                txt_sat_b3.setTextColor(Color.WHITE);
                                txt_sat_b3.setAlpha(.5f);
                                txt_sat_b3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        }
                        if (title.contains("Lunch")) {
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                                txt_sun_l2.setTextColor(Color.WHITE);
                                txt_sun_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_sun_l3.setTextColor(Color.WHITE);
                                txt_sun_l3.setAlpha(.5f);
                                txt_sun_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_l2.setTextColor(Color.WHITE);
                                txt_mon_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_mon_l3.setTextColor(Color.WHITE);
                                txt_mon_l3.setAlpha(.5f);
                                txt_mon_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_l2.setTextColor(Color.WHITE);
                                txt_tue_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_tue_l3.setTextColor(Color.WHITE);
                                txt_tue_l3.setAlpha(.5f);
                                txt_tue_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_l2.setTextColor(Color.WHITE);
                                txt_wed_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_wed_l3.setTextColor(Color.WHITE);
                                txt_wed_l3.setAlpha(.5f);
                                txt_wed_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_l2.setTextColor(Color.WHITE);
                                txt_thu_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_thu_l3.setTextColor(Color.WHITE);
                                txt_thu_l3.setAlpha(.5f);
                                txt_thu_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_l2.setTextColor(Color.WHITE);
                                txt_fri_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_fri_l3.setTextColor(Color.WHITE);
                                txt_fri_l3.setAlpha(.5f);
                                txt_fri_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_l2.setTextColor(Color.WHITE);
                                txt_sat_l2.setBackgroundResource(R.drawable.selected_bg);
                                txt_sat_l3.setTextColor(Color.WHITE);
                                txt_sat_l3.setAlpha(.5f);
                                txt_sat_l3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        }

                        if (title.contains("Dinner")) {
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                                txt_sun_d2.setTextColor(Color.WHITE);
                                txt_sun_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_sun_d3.setTextColor(Color.WHITE);
                                txt_sun_d3.setAlpha(.5f);
                                txt_sun_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                                txt_mon_d2.setTextColor(Color.WHITE);
                                txt_mon_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_mon_d3.setTextColor(Color.WHITE);
                                txt_mon_d3.setAlpha(.5f);
                                txt_mon_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                                txt_tue_d2.setTextColor(Color.WHITE);
                                txt_tue_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_tue_d3.setTextColor(Color.WHITE);
                                txt_tue_d3.setAlpha(.5f);
                                txt_tue_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                                txt_wed_d2.setTextColor(Color.WHITE);
                                txt_wed_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_wed_d3.setTextColor(Color.WHITE);
                                txt_wed_d3.setAlpha(.5f);
                                txt_wed_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                                txt_thu_d2.setTextColor(Color.WHITE);
                                txt_thu_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_thu_d3.setTextColor(Color.WHITE);
                                txt_thu_d3.setAlpha(.5f);
                                txt_thu_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                                txt_fri_d2.setTextColor(Color.WHITE);
                                txt_fri_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_fri_d3.setTextColor(Color.WHITE);
                                txt_fri_d3.setAlpha(.5f);
                                txt_fri_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                                scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                                txt_sat_d2.setTextColor(Color.WHITE);
                                txt_sat_d2.setBackgroundResource(R.drawable.selected_bg);
                                txt_sat_d3.setTextColor(Color.WHITE);
                                txt_sat_d3.setAlpha(.5f);
                                txt_sat_d3.setBackgroundResource(R.drawable.selected_bg);
                            }
                        }

                        if (isDayRemaining(false, false)) {
                            card_add_third_address.setVisibility(View.VISIBLE);
                            card_third_address.setVisibility(View.VISIBLE);
                        }

                    } else {

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                            txt_sun_b3.setTextColor(Color.WHITE);
                            txt_sun_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                            txt_mon_b3.setTextColor(Color.WHITE);
                            txt_mon_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                            txt_tue_b3.setTextColor(Color.WHITE);
                            txt_tue_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                            txt_wed_b3.setTextColor(Color.WHITE);
                            txt_wed_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                            txt_thu_b3.setTextColor(Color.WHITE);
                            txt_thu_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                            txt_fri_b3.setTextColor(Color.WHITE);
                            txt_fri_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                            txt_sat_b3.setTextColor(Color.WHITE);
                            txt_sat_b3.setBackgroundResource(R.drawable.selected_bg);
                        }

                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                            txt_sun_l3.setTextColor(Color.WHITE);
                            txt_sun_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                            txt_mon_l3.setTextColor(Color.WHITE);
                            txt_mon_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                            txt_tue_l3.setTextColor(Color.WHITE);
                            txt_tue_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                            txt_wed_l3.setTextColor(Color.WHITE);
                            txt_wed_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                            txt_thu_l3.setTextColor(Color.WHITE);
                            txt_thu_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                            txt_fri_l3.setTextColor(Color.WHITE);
                            txt_fri_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                            txt_sat_l3.setTextColor(Color.WHITE);
                            txt_sat_l3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).put("isChecked", true);
                            txt_sun_d3.setTextColor(Color.WHITE);
                            txt_sun_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).put("isChecked", true);
                            txt_mon_d3.setTextColor(Color.WHITE);
                            txt_mon_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).put("isChecked", true);
                            txt_tue_d3.setTextColor(Color.WHITE);
                            txt_tue_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).put("isChecked", true);
                            txt_wed_d3.setTextColor(Color.WHITE);
                            txt_wed_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).put("isChecked", true);
                            txt_thu_d3.setTextColor(Color.WHITE);
                            txt_thu_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).put("isChecked", true);
                            txt_fri_d3.setTextColor(Color.WHITE);
                            txt_fri_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).getBoolean("isSelect")) {
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("addressID", shippingIdToBeSet);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).put("isChecked", true);
                            txt_sat_d3.setTextColor(Color.WHITE);
                            txt_sat_d3.setBackgroundResource(R.drawable.selected_bg);
                        }
                        card_add_third_address.setVisibility(View.GONE);
                        txt_bf_times_a3.setText(getBreakfastTime());
                        txt_second_times_a3.setText(getLunchTime());
                        txt_third_times_a3.setText(getDinnerTime());
                        if (isDayRemaining(false, true)) {
                            MyApp.popMessage("Local Friend", "Max 3 addresses allowed. Please select remaining weekdays for this delivery address.\nThank you", getContext());
                            return;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("timings change", scheduleArr.toString());
                dialog.dismiss();
                isDialogShown = false;
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isDialogShown = false;
            }
        });


        chk_break_fast = dialog.findViewById(R.id.chk_break_fast);
        chk_lunch = dialog.findViewById(R.id.chk_lunch);
        chk_dinner = dialog.findViewById(R.id.chk_dinner);

        daysCounter = 0;
        if (title.contains("Breakfast")) {
            ++checkCounter;
            daysCounter += 7;
        }
        if (title.contains("Lunch")) {
            ++checkCounter;
            daysCounter += 7;
        }
        if (title.contains("Dinner")) {
            ++checkCounter;
            daysCounter += 7;
        }


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


        lunch = "";
        dinner = "";
        //Range bars for setting time range
        rangeView = dialog.findViewById(R.id.rangeview);
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
                rangeView.setStart(i - 1);
                tv_breakfast_time.setText(breakfast_start + "-" + breakfast_end);
            }

            @Override
            public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
                rangeView.setMinDistance(1);
                rangeView.setMaxDistance(1);
                rangeView.setMovable(true);
                breakfast_start = labels[i];
                rangeView.setEnd(i + 1);
                tv_breakfast_time.setText(breakfast_start + "-" + breakfast_end);
            }
        });

        rangeView.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveFocusThumbAlpha(0.26f);
        rangebar_dinner = dialog.findViewById(R.id.rangebar_dinner);
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
                rangebar_dinner.setEnd(i + 1);
                tv_dinner_time.setText(dinner_start + "-" + dinner_end);
            }

            @Override
            public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                rangebar_dinner.setMinDistance(1);
                rangebar_dinner.setMaxDistance(1);
                rangebar_dinner.setMovable(true);
                dinner_end = labelsDinner[i];
                rangebar_dinner.setStart(i - 1);
                tv_dinner_time.setText(dinner_start + "-" + dinner_end);
            }
        });

        rangebar_dinner.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveFocusThumbAlpha(0.26f);
        rangebar_lunch = dialog.findViewById(R.id.rangebar_lunch);
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
                rangebar_lunch.setEnd(i + 1);
                lunch_start = labelsLunch[i];
                tv_lunch_time.setText(lunch_start + "-" + lunch_end);
            }

            @Override
            public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                rangebar_lunch.setMinDistance(1);
                rangebar_lunch.setMaxDistance(1);
                rangebar_lunch.setMovable(true);
                lunch_end = labelsLunch[i];
                rangebar_lunch.setStart(i - 1);
                tv_lunch_time.setText(lunch_start + "-" + lunch_end);
            }
        });

        rangebar_lunch.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveFocusThumbAlpha(0.26f);

        tv_selected_days = dialog.findViewById(R.id.tv_selected_days);
        tv_selected_days_lunch = dialog.findViewById(R.id.tv_selected_days_lunch);
        tv_selected_days_dinner = dialog.findViewById(R.id.tv_selected_days_dinner);
        breakfast = tv_selected_days.getText().toString();
        lunch = tv_selected_days.getText().toString();
        dinner = tv_selected_days.getText().toString();
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

        try {
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isChecked")) {
                rel_sun_bf.setEnabled(false);
                rel_sun_bf.setClickable(false);
                rel_sun_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(1).getBoolean("isChecked")) {
                rel_mon_bf.setEnabled(false);
                rel_mon_bf.setClickable(false);
                rel_mon_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(2).getBoolean("isChecked")) {
                rel_tue_bf.setEnabled(false);
                rel_tue_bf.setClickable(false);
                rel_tue_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(3).getBoolean("isChecked")) {
                rel_wed_bf.setEnabled(false);
                rel_wed_bf.setClickable(false);
                rel_wed_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(4).getBoolean("isChecked")) {
                rel_thurs_bf.setEnabled(false);
                rel_thurs_bf.setClickable(false);
                rel_thurs_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(5).getBoolean("isChecked")) {
                rel_fri_bf.setEnabled(false);
                rel_fri_bf.setClickable(false);
                rel_fri_bf.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(6).getBoolean("isChecked")) {
                rel_sat_bf.setEnabled(false);
                rel_sat_bf.setClickable(false);
                rel_sat_bf.setAlpha(.5f);
                --daysCounter;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rel_sun_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sun_bfc == 0) {
                    rel_sun_bf.setSelected(true);
                    rel_sun_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sun_bfc = 1;
                    setUpSelectCheckbox(0, 0, true);
                    breakfast = breakfast + "Sun,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        rel_sun_bf.setSelected(true);
                        rel_sun_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_sunday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_sun_bfc = 1;
                        setUpSelectCheckbox(0, 0, true);
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        rel_sun_bf.setSelected(false);
                        rel_sun_bf.setBackgroundResource(R.drawable.edt_bg);
                        setUpSelectCheckbox(0, 0, false);
                        tv_sunday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_sun_bfc = 0;
                        breakfast = breakfast.replace("Sun,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

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
                    setUpSelectCheckbox(0, 1, true);
                    tv_monday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_mon_bfc = 1;
                    breakfast = breakfast + "Mon,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        rel_mon_bf.setSelected(true);
                        rel_mon_bf.setBackgroundResource(R.drawable.selected_bg);
                        setUpSelectCheckbox(0, 1, true);
                        tv_monday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_mon_bfc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(0, 1, false);
                        rel_mon_bf.setSelected(false);
                        rel_mon_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_monday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_mon_bfc = 0;
                        breakfast = breakfast.replace("Mon,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

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
                    setUpSelectCheckbox(0, 2, true);
                    breakfast = breakfast + "Tue,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        rel_tue_bf.setSelected(true);
                        rel_tue_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_tuesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_tue_bfc = 1;
                        setUpSelectCheckbox(0, 2, true);
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(0, 2, false);
                        rel_tue_bf.setSelected(false);
                        rel_tue_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_tuesday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_tue_bfc = 0;
                        breakfast = breakfast.replace("Tue,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_wed_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wed_bfc == 0) {
                    setUpSelectCheckbox(0, 3, true);
                    rel_wed_bf.setSelected(true);
                    rel_wed_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wed_bfc = 1;
                    breakfast = breakfast + "Wed,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(0, 3, true);
                        rel_wed_bf.setSelected(true);
                        rel_wed_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_wednesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_wed_bfc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        rel_wed_bf.setSelected(false);
                        setUpSelectCheckbox(0, 3, false);
                        rel_wed_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_wednesday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_wed_bfc = 0;
                        breakfast = breakfast.replace("Wed,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_thurs_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thurs_bfc == 0) {
                    rel_thurs_bf.setSelected(true);
                    setUpSelectCheckbox(0, 4, true);
                    rel_thurs_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thurs_bfc = 1;
                    breakfast = breakfast + "Thu,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        rel_thurs_bf.setSelected(true);
                        setUpSelectCheckbox(0, 4, true);
                        rel_thurs_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_thursday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_thurs_bfc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        rel_thurs_bf.setSelected(false);
                        setUpSelectCheckbox(0, 4, false);
                        rel_thurs_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_thursday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_thurs_bfc = 0;
                        breakfast = breakfast.replace("Thu,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_fri_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fri_bfc == 0) {
                    setUpSelectCheckbox(0, 5, true);
                    rel_fri_bf.setSelected(true);
                    rel_fri_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fri_bfc = 1;
                    breakfast = breakfast + "Fri,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(0, 5, true);
                        rel_fri_bf.setSelected(true);
                        rel_fri_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_friday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_fri_bfc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        rel_fri_bf.setSelected(false);
                        setUpSelectCheckbox(0, 5, false);
                        rel_fri_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_friday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_fri_bfc = 0;
                        breakfast = breakfast.replace("Fri,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

                }
                tv_selected_days.setText(breakfast);
            }
        });
        rel_sat_bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sat_bfc == 0) {
                    rel_sat_bf.setSelected(true);
                    setUpSelectCheckbox(0, 6, true);
                    rel_sat_bf.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sat_bfc = 1;
                    breakfast = breakfast + "Sat,";
                    ++daysCounter;
                    extraCounterBf += 1;
                } else {
                    if (daysCounter == 1) {
                        rel_sat_bf.setSelected(true);
                        setUpSelectCheckbox(0, 6, true);
                        rel_sat_bf.setBackgroundResource(R.drawable.selected_bg);
                        tv_saturday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_sat_bfc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(0, 6, false);
                        rel_sat_bf.setSelected(false);
                        rel_sat_bf.setBackgroundResource(R.drawable.edt_bg);
                        tv_saturday_bf.setTextColor(Color.parseColor("#999999"));
                        rel_sat_bfc = 0;
                        breakfast = breakfast.replace("Sat,", "");
                        --daysCounter;
                        extraCounterBf -= 1;
                    }

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

        try {

            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(0).getBoolean("isChecked")) {
                rel_sun_lnch.setEnabled(false);
                rel_sun_lnch.setClickable(false);
                rel_sun_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(1).getBoolean("isChecked")) {
                rel_mon_lnch.setEnabled(false);
                rel_mon_lnch.setClickable(false);
                rel_mon_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(2).getBoolean("isChecked")) {
                rel_tue_lnch.setEnabled(false);
                rel_tue_lnch.setClickable(false);
                rel_tue_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(3).getBoolean("isChecked")) {
                rel_wed_lnch.setEnabled(false);
                rel_wed_lnch.setClickable(false);
                rel_wed_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(4).getBoolean("isChecked")) {
                rel_thurs_lnch.setEnabled(false);
                rel_thurs_lnch.setClickable(false);
                rel_thurs_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(5).getBoolean("isChecked")) {
                rel_fri_lnch.setEnabled(false);
                rel_fri_lnch.setClickable(false);
                rel_fri_lnch.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(6).getBoolean("isChecked")) {
                rel_sat_lnch.setEnabled(false);
                rel_sat_lnch.setClickable(false);
                rel_sat_lnch.setAlpha(.5f);
                --daysCounter;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        rel_sun_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sun_lnchc == 0) {
                    setUpSelectCheckbox(1, 0, true);
                    rel_sun_lnch.setSelected(true);
                    rel_sun_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sun_lnchc = 1;
                    lunch = lunch + "Sun,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 0, true);
                        rel_sun_lnch.setSelected(true);
                        rel_sun_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_sunday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_sun_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 0, false);
                        rel_sun_lnch.setSelected(false);
                        rel_sun_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_sunday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_sun_lnchc = 0;
                        lunch = lunch.replace("Sun,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_mon_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_mon_lnchc == 0) {
                    setUpSelectCheckbox(1, 1, true);
                    rel_mon_lnch.setSelected(true);
                    rel_mon_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_monday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_mon_lnchc = 1;
                    lunch = lunch + "Mon,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 1, true);
                        rel_mon_lnch.setSelected(true);
                        rel_mon_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_monday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_mon_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 1, false);
                        rel_mon_lnch.setSelected(false);
                        rel_mon_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_monday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_mon_lnchc = 0;
                        lunch = lunch.replace("Mon,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_tue_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_tue_lnchc == 0) {
                    setUpSelectCheckbox(1, 2, true);
                    rel_tue_lnch.setSelected(true);
                    rel_tue_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_tuesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_tue_lnchc = 1;
                    lunch = lunch + "Tue,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 2, true);
                        rel_tue_lnch.setSelected(true);
                        rel_tue_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_tuesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_tue_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselct");
                    } else {
                        setUpSelectCheckbox(1, 2, false);
                        rel_tue_lnch.setSelected(false);
                        rel_tue_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_tuesday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_tue_lnchc = 0;
                        lunch = lunch.replace("Tue,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_wed_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wed_lnchc == 0) {
                    setUpSelectCheckbox(1, 3, true);
                    rel_wed_lnch.setSelected(true);
                    rel_wed_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wed_lnchc = 1;
                    lunch = lunch + "Wed,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 3, true);
                        rel_wed_lnch.setSelected(true);
                        rel_wed_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_wednesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_wed_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 3, false);
                        rel_wed_lnch.setSelected(false);
                        rel_wed_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_wednesday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_wed_lnchc = 0;
                        lunch = lunch.replace("Wed,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_thurs_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thurs_lnchc == 0) {
                    setUpSelectCheckbox(1, 4, true);
                    rel_thurs_lnch.setSelected(true);
                    rel_thurs_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thurs_lnchc = 1;
                    lunch = lunch + "Thu,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 4, true);
                        rel_thurs_lnch.setSelected(true);
                        rel_thurs_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_thursday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_thurs_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 4, false);
                        rel_thurs_lnch.setSelected(false);
                        rel_thurs_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_thursday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_thurs_lnchc = 0;
                        lunch = lunch.replace("Thu,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_fri_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fri_lnchc == 0) {
                    setUpSelectCheckbox(1, 5, true);
                    rel_fri_lnch.setSelected(true);
                    rel_fri_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fri_lnchc = 1;
                    lunch = lunch + "Fri,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 5, true);
                        rel_fri_lnch.setSelected(true);
                        rel_fri_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_friday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_fri_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 5, false);
                        rel_fri_lnch.setSelected(false);
                        rel_fri_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_friday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_fri_lnchc = 0;
                        lunch = lunch.replace("Fri,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

                }
                tv_selected_days_lunch.setText(lunch);
            }
        });
        rel_sat_lnch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sat_lnchc == 0) {
                    setUpSelectCheckbox(1, 6, true);
                    rel_sat_lnch.setSelected(true);
                    rel_sat_lnch.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sat_lnchc = 1;
                    lunch = lunch + "Sat,";
                    ++daysCounter;
                    extraCounterLunch += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(1, 6, true);
                        rel_sat_lnch.setSelected(true);
                        rel_sat_lnch.setBackgroundResource(R.drawable.selected_bg);
                        tv_saturday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_sat_lnchc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(1, 6, false);
                        rel_sat_lnch.setSelected(false);
                        rel_sat_lnch.setBackgroundResource(R.drawable.edt_bg);
                        tv_saturday_lnch.setTextColor(Color.parseColor("#999999"));
                        rel_sat_lnchc = 0;
                        lunch = lunch.replace("Sat,", "");
                        --daysCounter;
                        extraCounterLunch -= 1;
                    }

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

        try {

            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(0).getBoolean("isChecked")) {
                rel_sun.setClickable(false);
                rel_sun.setEnabled(false);
                rel_sun.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(1).getBoolean("isChecked")) {
                rel_mon.setClickable(false);
                rel_mon.setEnabled(false);
                rel_mon.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(2).getBoolean("isChecked")) {
                rel_tue.setClickable(false);
                rel_tue.setEnabled(false);
                rel_tue.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(3).getBoolean("isChecked")) {
                rel_wed.setClickable(false);
                rel_wed.setEnabled(false);
                rel_wed.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(4).getBoolean("isChecked")) {
                rel_thurs.setClickable(false);
                rel_thurs.setEnabled(false);
                rel_thurs.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(5).getBoolean("isChecked")) {
                rel_fri.setClickable(false);
                rel_fri.setEnabled(false);
                rel_fri.setAlpha(.5f);
                --daysCounter;
            }
            if (scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(6).getBoolean("isChecked")) {
                rel_sat.setClickable(false);
                rel_sat.setEnabled(false);
                rel_sat.setAlpha(.5f);
                --daysCounter;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rel_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_sunc == 0) {
                    setUpSelectCheckbox(2, 0, true);
                    rel_sun.setSelected(true);
                    rel_sun.setBackgroundResource(R.drawable.selected_bg);
                    tv_sunday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_sunc = 1;
                    dinner = dinner + "Sun,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 0, true);
                        rel_sun.setSelected(true);
                        rel_sun.setBackgroundResource(R.drawable.selected_bg);
                        tv_sunday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_sunc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 0, false);
                        rel_sun.setSelected(false);
                        rel_sun.setBackgroundResource(R.drawable.edt_bg);
                        tv_sunday.setTextColor(Color.parseColor("#999999"));
                        rel_sunc = 0;
                        dinner = dinner.replace("Sun,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_monc == 0) {
                    setUpSelectCheckbox(2, 1, true);
                    rel_mon.setSelected(true);
                    rel_mon.setBackgroundResource(R.drawable.selected_bg);
                    tv_monday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_monc = 1;
                    dinner = dinner + "Mon,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 1, true);
                        rel_mon.setSelected(true);
                        rel_mon.setBackgroundResource(R.drawable.selected_bg);
                        tv_monday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_monc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 1, false);
                        rel_mon.setSelected(false);
                        rel_mon.setBackgroundResource(R.drawable.edt_bg);
                        tv_monday.setTextColor(Color.parseColor("#999999"));
                        rel_monc = 0;
                        dinner = dinner.replace("Mon,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_tuec == 0) {
                    setUpSelectCheckbox(2, 2, true);
                    rel_tue.setSelected(true);
                    rel_tue.setBackgroundResource(R.drawable.selected_bg);
                    tv_tuesday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_tuec = 1;
                    dinner = dinner + "Tue,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 2, true);
                        rel_tue.setSelected(true);
                        rel_tue.setBackgroundResource(R.drawable.selected_bg);
                        tv_tuesday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_tuec = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 2, false);
                        rel_tue.setSelected(false);
                        rel_tue.setBackgroundResource(R.drawable.edt_bg);
                        tv_tuesday.setTextColor(Color.parseColor("#999999"));
                        rel_tuec = 0;
                        dinner = dinner.replace("Tue,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_wedc == 0) {
                    setUpSelectCheckbox(2, 3, true);
                    rel_wed.setSelected(true);
                    rel_wed.setBackgroundResource(R.drawable.selected_bg);
                    tv_wednesday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_wedc = 1;
                    dinner = dinner + "Wed,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 3, true);
                        rel_wed.setSelected(true);
                        rel_wed.setBackgroundResource(R.drawable.selected_bg);
                        tv_wednesday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_wedc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 3, false);
                        rel_wed.setSelected(false);
                        rel_wed.setBackgroundResource(R.drawable.edt_bg);
                        tv_wednesday.setTextColor(Color.parseColor("#999999"));
                        rel_wedc = 0;
                        dinner = dinner.replace("Wed,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_thursc == 0) {
                    setUpSelectCheckbox(2, 4, true);
                    rel_thurs.setSelected(true);
                    rel_thurs.setBackgroundResource(R.drawable.selected_bg);
                    tv_thursday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_thursc = 1;
                    dinner = dinner + "Thu,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 4, true);
                        rel_thurs.setSelected(true);
                        rel_thurs.setBackgroundResource(R.drawable.selected_bg);
                        tv_thursday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_thursc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 4, false);
                        rel_thurs.setSelected(false);
                        rel_thurs.setBackgroundResource(R.drawable.edt_bg);
                        tv_thursday.setTextColor(Color.parseColor("#999999"));
                        rel_thursc = 0;
                        dinner = dinner.replace("Thu,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_fric == 0) {
                    setUpSelectCheckbox(2, 5, true);
                    rel_fri.setSelected(true);
                    rel_fri.setBackgroundResource(R.drawable.selected_bg);
                    tv_friday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_fric = 1;
                    dinner = dinner + "Fri,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 5, true);
                        rel_fri.setSelected(true);
                        rel_fri.setBackgroundResource(R.drawable.selected_bg);
                        tv_friday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_fric = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 5, false);
                        rel_fri.setSelected(false);
                        rel_fri.setBackgroundResource(R.drawable.edt_bg);
                        tv_friday.setTextColor(Color.parseColor("#999999"));
                        rel_fric = 0;
                        dinner = dinner.replace("Fri,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });
        rel_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rel_satc == 0) {
                    setUpSelectCheckbox(2, 6, true);
                    rel_sat.setSelected(true);
                    rel_sat.setBackgroundResource(R.drawable.selected_bg);
                    tv_saturday.setTextColor(Color.parseColor("#FFFFFF"));
                    rel_satc = 1;
                    dinner = dinner + "Sat,";
                    ++daysCounter;
                    extraCounterDinner += 1;
                } else {
                    if (daysCounter == 1) {
                        setUpSelectCheckbox(2, 6, true);
                        rel_sat.setSelected(true);
                        rel_sat.setBackgroundResource(R.drawable.selected_bg);
                        tv_saturday.setTextColor(Color.parseColor("#FFFFFF"));
                        rel_satc = 1;
                        MyApp.showMassage(getContext(), "Can't deselect");
                    } else {
                        setUpSelectCheckbox(2, 6, false);
                        rel_sat.setSelected(false);
                        rel_sat.setBackgroundResource(R.drawable.edt_bg);
                        tv_saturday.setTextColor(Color.parseColor("#999999"));
                        rel_satc = 0;
                        dinner = dinner.replace("Sat,", "");
                        --daysCounter;
                        extraCounterDinner -= 1;
                    }

                }
                tv_selected_days_dinner.setText(dinner);
            }
        });


        chk_break_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!title.contains("Breakfast")){
                    return;
                }

                if (chk_break_fast.isChecked()) {
                    tv_schedule_breakfast.setVisibility(View.VISIBLE);
                    lnr_breakfast.setVisibility(View.VISIBLE);
                    ++checkCounter;
                    try {
                        if (extraCounterBf != 0) {
                            daysCounter = daysCounter + extraCounterBf;
                            extraCounterBf = 0;
                        }
                        for (int j = 0; j < 7; j++) {
                            if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
                                ++daysCounter;
                            }
//                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).put("isChecked", true);
                            scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).put("isSelect", true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (!chk_break_fast.isChecked()) {

                    if (checkCounter == 1) {
                        chk_break_fast.setChecked(true);
                        tv_schedule_breakfast.setVisibility(View.VISIBLE);
                        lnr_breakfast.setVisibility(View.VISIBLE);
                    } else {
                        if (extraCounterBf != 0) {
                            daysCounter = daysCounter - extraCounterBf;
                            extraCounterBf = 0;
                        }
                        --checkCounter;
                        tv_schedule_breakfast.setVisibility(View.GONE);
                        lnr_breakfast.setVisibility(View.GONE);
                        try {
                            for (int j = 0; j < 7; j++) {
                                if (!scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
//                                    scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).put("isChecked", false);
                                    scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(j).put("isSelect", false);
                                    --daysCounter;
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        }
                    }

                }
            }
        });

        chk_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!title.contains("Lunch")){
                    return;
                }
                if (chk_lunch.isChecked()) {
                    tv_schedule_lunch.setVisibility(View.VISIBLE);
                    lnr_lunch.setVisibility(View.VISIBLE);
                    ++checkCounter;
                    if (extraCounterLunch != 0) {
                        daysCounter = daysCounter + extraCounterLunch;
                        extraCounterLunch = 0;
                    }
                    try {
                        for (int j = 0; j < 7; j++) {
                            if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
                                ++daysCounter;
                            }
//                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).put("isChecked", false);
                            scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).put("isSelect", false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (!chk_lunch.isChecked()) {

                    if (checkCounter == 1) {
                        chk_lunch.setChecked(true);
                        tv_schedule_lunch.setVisibility(View.VISIBLE);
                        lnr_lunch.setVisibility(View.VISIBLE);
                    } else {
                        --checkCounter;
                        tv_schedule_lunch.setVisibility(View.GONE);
                        lnr_lunch.setVisibility(View.GONE);
//                        daysCounter -= 7;
                        try {
                            if (extraCounterLunch != 0) {
                                daysCounter = daysCounter - extraCounterLunch;
                                extraCounterLunch = 0;
                            }
                            for (int j = 0; j < 7; j++) {
                                if (!scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
                                    --daysCounter;
//                                    scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).put("isChecked", false);
                                    scheduleArr.getJSONObject(1).getJSONArray("mealsday").getJSONObject(j).put("isSelect", false);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        chk_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!title.contains("Dinner")){
                    return;
                }
                if (chk_dinner.isChecked()) {
                    tv_schedule_dinner.setVisibility(View.VISIBLE);
                    lnr_dinner.setVisibility(View.VISIBLE);
                    ++checkCounter;
                    try {
                        if (extraCounterDinner != 0) {
                            daysCounter = daysCounter + extraCounterDinner;
                            extraCounterDinner = 0;
                        }
                        for (int j = 0; j < 7; j++) {
                            if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
                                ++daysCounter;
                            }
//                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).put("isChecked", true);
                            scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).put("isSelect", true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (!chk_dinner.isChecked()) {

                    if (checkCounter == 1) {
                        chk_dinner.setChecked(true);
                        tv_schedule_dinner.setVisibility(View.VISIBLE);
                        lnr_dinner.setVisibility(View.VISIBLE);
                    } else {
                        if (extraCounterDinner != 0) {
                            daysCounter = daysCounter - extraCounterDinner;
                            extraCounterDinner = 0;
                        }
                        --checkCounter;
                        tv_schedule_dinner.setVisibility(View.GONE);
                        lnr_dinner.setVisibility(View.GONE);
                        try {
                            for (int j = 0; j < 7; j++) {
                                if (!scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).optBoolean("isChecked")) {
                                    --daysCounter;
//                                    scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).put("isChecked", false);
                                    scheduleArr.getJSONObject(2).getJSONArray("mealsday").getJSONObject(j).put("isSelect", false);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        if (!title.contains("Breakfast")) {
//            ++checkCounter;
//            daysCounter += 7;
            chk_break_fast.setChecked(false);
            chk_break_fast.setEnabled(false);
            tv_schedule_breakfast.setVisibility(View.GONE);
            lnr_breakfast.setVisibility(View.GONE);
        }
        if (!title.contains("Lunch")) {
//            ++checkCounter;
//            daysCounter += 7;
            chk_lunch.setChecked(false);
            chk_lunch.setEnabled(false);
            tv_schedule_lunch.setVisibility(View.GONE);
            lnr_lunch.setVisibility(View.GONE);
        }

        if (!title.contains("Dinner")) {
//            ++checkCounter;
//            daysCounter += 7;
            chk_dinner.setChecked(false);
            chk_dinner.setEnabled(false);
            tv_schedule_lunch.setVisibility(View.GONE);
            lnr_dinner.setVisibility(View.GONE);
            tv_schedule_dinner.setVisibility(View.GONE);
        }

//        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        isDialogShown = true;
    }

    private boolean isDialogShown = false;

    private void setUpSelectCheckbox(int pos1, int pos2, boolean isSelect) {
        try {
            scheduleArr.getJSONObject(pos1).getJSONArray("mealsday").getJSONObject(pos2).put("isSelect", isSelect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    TextView tv_sunday, tv_monday, tv_tuesday, tv_wednesday, tv_thursday, tv_friday, tv_saturday;
    String breakfast = "", lunch = "", dinner = "";

    LinearLayout lnr_breakfast, lnr_lunch, lnr_dinner;
    final String[] labels = new String[]{"07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00"};
    final String[] labelsLunch = new String[]{"12:00", "12:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00"};
    final String[] labelsDinner = new String[]{"07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00"};
    SimpleRangeView rangeView, rangebar_dinner, rangebar_lunch;
    String breakfast_start = "08:00", breakfast_end = "08:30";
    String lunch_start = "01:00", lunch_end = "01:30";
    String dinner_start = "08:00", dinner_end = "08:30";

    private String getBreakfastTime() {
        if (rangeView.getStart() == 0) {
            return "07:00 AM - 07:30 AM";
        } else if (rangeView.getStart() == 1) {
            return "07:30 AM - 08:00 AM";
        } else if (rangeView.getStart() == 2) {
            return "08:00 AM - 08:30 AM";
        } else if (rangeView.getStart() == 3) {
            return "08:30 AM - 09:00 AM";
        } else if (rangeView.getStart() == 4) {
            return "09:00 AM - 09:30 AM";
        } else if (rangeView.getStart() == 5) {
            return "09:30 AM - 10:00 AM";
        } else if (rangeView.getStart() == 6) {
            return "10:00 AM - 10:30 AM";
        } else if (rangeView.getStart() == 7) {
            return "10:30 AM - 11:00 AM";
        }
        return "08:00 AM - 08:30 AM";
    }

    private String getLunchTime() {
        if (rangebar_lunch.getStart() == 0) {
            return "12:00 PM - 12:30 PM";
        } else if (rangebar_lunch.getStart() == 1) {
            return "12:30 PM - 01:00 PM";
        } else if (rangebar_lunch.getStart() == 2) {
            return "01:00 PM - 01:30 PM";
        } else if (rangebar_lunch.getStart() == 3) {
            return "01:30 PM - 02:00 PM";
        } else if (rangebar_lunch.getStart() == 4) {
            return "02:00 PM - 02:30 PM";
        } else if (rangebar_lunch.getStart() == 5) {
            return "02:30 PM - 03:00 PM";
        } else if (rangebar_lunch.getStart() == 6) {
            return "03:00 PM - 03:30 PM";
        } else if (rangebar_lunch.getStart() == 7) {
            return "03:30 PM - 04:00 PM";
        }
        return "01:00 PM - 01:30 PM";
    }

    private String getDinnerTime() {
        if (rangebar_dinner.getStart() == 0) {
            return "07:00 PM - 07:30 PM";
        } else if (rangebar_dinner.getStart() == 1) {
            return "07:30 PM - 08:00 PM";
        } else if (rangebar_dinner.getStart() == 2) {
            return "08:00 PM - 08:30 PM";
        } else if (rangebar_dinner.getStart() == 3) {
            return "08:30 PM - 09:00 PM";
        } else if (rangebar_dinner.getStart() == 4) {
            return "09:00 PM - 09:30 PM";
        } else if (rangebar_dinner.getStart() == 5) {
            return "09:30 PM - 10:00 PM";
        } else if (rangebar_dinner.getStart() == 6) {
            return "10:00 PM - 10:30 PM";
        } else if (rangebar_dinner.getStart() == 7) {
            return "10:30 PM - 11:00 PM";
        }
        return "08:00 PM - 08:30 PM";
    }

    private boolean isDayRemaining(boolean clearExtra, boolean isLast) {
        boolean yesNo = false;
        getScheduleArr();
        try {
            scheduleArr.length();
        } catch (Exception e) {
            return true;
        }
//        scheduleArr.getJSONObject(0).getJSONArray("mealsday").getJSONObject(0).getBoolean("isSelect")
        for (int i = 0; i < scheduleArr.length(); i++) {
            try {
                JSONObject o = scheduleArr.getJSONObject(i);
                JSONArray daysArr = o.getJSONArray("mealsday");
                for (int j = 0; j < daysArr.length(); j++) {
                    JSONObject oo = daysArr.getJSONObject(j);
                    if (!oo.optBoolean("isSelect") && oo.has("isSelect")) {
//                        if (isLast) {
//                            yesNo = false;
//                            scheduleArr.getJSONObject(i).getJSONArray("mealsday").getJSONObject(j).put("isSelect",true);
//                        } else
                            yesNo = true;
                    }
                    if (clearExtra) {
                        scheduleArr.getJSONObject(i).getJSONArray("mealsday").getJSONObject(j).remove("isChecked");
                        scheduleArr.getJSONObject(i).getJSONArray("mealsday").getJSONObject(j).remove("isSelect");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return yesNo;
    }

    private void makeAllTrue() {
        for (int i = 0; i < scheduleArr.length(); i++) {
            try {
                JSONObject o = scheduleArr.getJSONObject(i);
                JSONArray daysArr = o.getJSONArray("mealsday");
                for (int j = 0; j < daysArr.length(); j++) {
                    JSONObject oo = daysArr.getJSONObject(j);
                    if (!oo.optBoolean("isSelect")) {
//                        oo.put("isSelect", true);
                        scheduleArr.getJSONObject(i).getJSONArray("mealsday").getJSONObject(j).put("isSelect", true);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
