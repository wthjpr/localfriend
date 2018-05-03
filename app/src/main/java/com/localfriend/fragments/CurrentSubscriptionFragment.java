package com.localfriend.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.MainActivity;
import com.localfriend.R;
import com.localfriend.adapter.CurrentSubscriptionAdapter;
import com.localfriend.adapter.SubscriptionDetailsAdapter;
import com.localfriend.adapter.ViewHistoryItemsAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.model.History;
import com.localfriend.model.SubHistory;
import com.localfriend.model.SubscriptionModel;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentSubscriptionFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private RelativeLayout rl_empty;
    private RecyclerView rv_history;
    private TextView tv_start_shopping;
    private CurrentSubscriptionAdapter adapter;
    private List<SubscriptionModel.Data.Track> trackList = new ArrayList<>();

    public CurrentSubscriptionFragment() {
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
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Order/Subscription/Current", 1);
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
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finishAffinity();
        }
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

        if (callNumber == 1) {
            dismissDialog();
            Type listType = new TypeToken<ArrayList<SubHistory>>() {
            }.getType();
            try {
                List<SubHistory> historyList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (historyList.size() > 0) {
                    rl_empty.setVisibility(View.GONE);
                    rv_history.setVisibility(View.VISIBLE);
                    rv_history.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new CurrentSubscriptionAdapter(historyList, CurrentSubscriptionFragment.this);
                    rv_history.setAdapter(adapter);
                } else {
                    rl_empty.setVisibility(View.VISIBLE);
                    rv_history.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 2) {
            dismissDialog();
            try {
                History h = new Gson().fromJson(o.getJSONObject("data").toString(), History.class);
                ViewHistoryItemsAdapter adapter = new ViewHistoryItemsAdapter(getActivity(), false, h.getOrderItemlist());
                showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                        dismissListener, cancelListener,
                        false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 0) {
            dismissDialog();
            MyApp.popMessage("LocalFriend", o.optString("message"), getActivity());
        } else if (callNumber == 3) {
            dismissDialog();
            SubscriptionModel subscriptionModel = new Gson().fromJson(o.toString(), SubscriptionModel.class);
            trackList.addAll(subscriptionModel.getData().getTrack());
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_subscription_details);
            ImageView img_close = dialog.findViewById(R.id.img_close);
            TextView packagetype = dialog.findViewById(R.id.txt_packageType);
            TextView pkg_price = dialog.findViewById(R.id.txt_pkgCost);
            TextView startfrom = dialog.findViewById(R.id.txt_startDate);
            TextView received_bf = dialog.findViewById(R.id.received_bf);
            TextView received_lunch = dialog.findViewById(R.id.received_lunch);
            TextView received_dinner = dialog.findViewById(R.id.received_dinner);
            TextView cancelled_bf = dialog.findViewById(R.id.cancelled_bf);
            TextView cancelled_lunch = dialog.findViewById(R.id.cancelled_lunch);
            TextView cancelled_dinner = dialog.findViewById(R.id.cancelled_dinner);
            TextView remaining_bf = dialog.findViewById(R.id.remaining_bf);
            TextView remaining_lunch = dialog.findViewById(R.id.remaining_lunch);
            TextView remaining_dinner = dialog.findViewById(R.id.remaining_dinner);
            packagetype.setText(subscriptionModel.getData().getPackagedetails().getTitle());
            pkg_price.setText("Rs. " + subscriptionModel.getData().getPackagedetails().getPrice());
            startfrom.setText(subscriptionModel.getData().getPackagedetails().getStartdate());
            try {
                //bf
                received_bf.setText(subscriptionModel.getData().getStatus().get(0).getDeleverd());
                cancelled_bf.setText(subscriptionModel.getData().getStatus().get(0).getCanceled());
                remaining_bf.setText(subscriptionModel.getData().getStatus().get(0).getRemaining());
                //lunch
                received_lunch.setText(subscriptionModel.getData().getStatus().get(1).getDeleverd());
                cancelled_lunch.setText(subscriptionModel.getData().getStatus().get(1).getCanceled());
                remaining_lunch.setText(subscriptionModel.getData().getStatus().get(1).getRemaining());
                //dinner
                received_dinner.setText(subscriptionModel.getData().getStatus().get(2).getDeleverd());
                remaining_dinner.setText(subscriptionModel.getData().getStatus().get(2).getRemaining());
                cancelled_dinner.setText(subscriptionModel.getData().getStatus().get(2).getCanceled());
            } catch (Exception e) {
            }


            RecyclerView rv_subscription = dialog.findViewById(R.id.rv_subscription);
            rv_subscription.setLayoutManager(new LinearLayoutManager(getContext()));
            SubscriptionDetailsAdapter subscriptionDetailsAdapter = new SubscriptionDetailsAdapter(getContext(), trackList);
            rv_subscription.setAdapter(subscriptionDetailsAdapter);
            subscriptionDetailsAdapter.notifyDataSetChanged();

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = -1;
            lp.height = -1;
            dialog.getWindow().setAttributes(lp);
            dialog.show();

        } else {
//            MyApp.showMassage(getActivity(), o.optString("message"));
//            showLoadingDialog("");
            getCallWithHeader(AppConstant.BASE_URL + "Order/Subscription/Current", 1);
        }

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

    public void cancelClicked(String order_id) {
        JSONObject o = new JSONObject();
        try {
            o.put("orderID", order_id);
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Order/Subscription/Cancel", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getOrderDetails(String order_id) {
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Order/" + order_id, 2);
    }

    private void showCompleteDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener,
                                    boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getActivity())

                .setContentHolder(holder)
                .setHeader(R.layout.header_view_item)
//                .setContentBackgroundResource(R.drawable.bg_cart)
//                .setFooter(R.layout.footer)
//                .setCancelable(true)
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

    public void getOrderDetails(SubHistory subHistory) {
        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Order/Subscription/PackageReport?subscriptionid=" + subHistory.getSubscriptionid(), 3);
    }

    public void cancelClicked(final SubHistory subHistory) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_cancel_meals);
        ImageView img_close = dialog.findViewById(R.id.img_close);

        final CheckBox chk_break_fast = dialog.findViewById(R.id.chk_break_fast);
        final CheckBox chk_lunch = dialog.findViewById(R.id.chk_lunch);
        final CheckBox chk_dinner = dialog.findViewById(R.id.chk_dinner);

        if (MyApp.millisTo(System.currentTimeMillis()) > 8.0) {
            chk_break_fast.setEnabled(false);
        }
        if (MyApp.millisTo(System.currentTimeMillis()) > 11.0) {
            chk_lunch.setEnabled(false);
        }
        if (MyApp.millisTo(System.currentTimeMillis()) > 19.0) {
            chk_dinner.setEnabled(false);
        }


        final TextView txt_from_date = dialog.findViewById(R.id.txt_from_date);
        txt_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(txt_from_date, 1);
            }
        });

        final TextView txt_to_date = dialog.findViewById(R.id.txt_to_date);
        txt_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(txt_to_date, 2);
            }
        });

        TextView txt_done = dialog.findViewById(R.id.txt_done);
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("logcancel", startDate + "\n" + endDate);
                boolean isProcessed = false;
                if (chk_break_fast.isChecked() || chk_lunch.isChecked() || chk_dinner.isChecked()) {
                    isProcessed = true;
                    JSONObject o = new JSONObject();
                    try {
                        o.put("subscriptionID", subHistory.getOrderID());
                        JSONArray arr = new JSONArray();
                        arr.put("Breckfast");
                        arr.put("Lunch");
                        arr.put("Dinner");
                        o.put("meals", arr);
                        showLoadingDialog("");
                        postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/CancelToday", o, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (!txt_from_date.getText().toString().equals("Choose Date") && !txt_to_date.getText().toString().equals("Choose Date")) {
                    if (!isProcessed) {
                        showLoadingDialog("");
                    }
                    JSONObject o = new JSONObject();
                    try {
                        o.put("subscriptionID", subHistory.getOrderID());
                        o.put("startdate", startDate);
                        o.put("enddate", endDate);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isProcessed = true;
                    postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/CancelComingDays", o, "");
                }

                if (isProcessed) {
                    dialog.dismiss();
                } else {
                    MyApp.showMassage(getContext(), "No changes detected");
                }
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private String startDate;
    private String endDate;

    public void dateDialog(final TextView textView, final int number) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);

        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (dayOfMonth <= (mDay + 1) && number == 1) {
                            MyApp.showMassage(getContext(), "You can't select today or previous day");
                        } else if (dayOfMonth < (mDay + 2) && number == 2) {
                            MyApp.showMassage(getContext(), "You can't select today or previous day");
                        } else
                            textView.setText(parseDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, number));
                    }
                }, mYear, mMonth, mDay + 1);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public String parseDate(String time, int number) {
        if (time.length() != 10) {
            String[] ar = time.split("-");
            time = "";
            if (ar[0].length() == 1) {
                time += "0" + ar[0] + "-";
            } else {
                time += ar[0] + "-";
            }
            if (ar[1].length() == 1) {
                time += "0" + ar[1] + "-";
            } else {
                time += ar[1] + "-";
            }
            time += ar[2];
        }
        Log.e("Date", "parseDateToHHMM: " + time);
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "d MMM, yyyy";
        String outPutPattern2 = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        SimpleDateFormat outputFormat2 = new SimpleDateFormat(outPutPattern2);

        Date date;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            if (number == 1) {
                startDate = outputFormat2.format(date);
            } else {
                endDate = outputFormat2.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


}
