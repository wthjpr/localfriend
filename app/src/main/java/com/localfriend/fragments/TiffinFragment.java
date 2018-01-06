package com.localfriend.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.AllActivity;
import com.localfriend.BreakFastActivity;
import com.localfriend.MonthlyPackageListActivity;
import com.localfriend.R;
import com.localfriend.adapter.SimpleAdapter;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.CategoryDetails;
import com.localfriend.model.Product;
import com.localfriend.model.ProductData;
import com.localfriend.model.StoreList;
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
        catList = SingleInstance.getInstance().getTiffinCatList();
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
        img_breakfast = myView.findViewById(R.id.img_breakfast);
        img_lunch = myView.findViewById(R.id.img_lunch);
        img_dinner = myView.findViewById(R.id.img_dinner);
        img_snacks = myView.findViewById(R.id.img_snacks);

        tv_breakfast = myView.findViewById(R.id.tv_breakfast);
        tv_lunch = myView.findViewById(R.id.tv_lunch);
        tv_dinner = myView.findViewById(R.id.tv_dinner);
        tv_snacks = myView.findViewById(R.id.tv_snacks);
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
            LinearLayout v = myView.findViewById(R.id.ll_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            lp.setMargins(0, 3 * MyApp.getApplication().getStatusBarHeight(), 0, 0);
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

    private String titleSend = "Breakfast";

    @Override
    public void onClick(View v) {
        if (catList.size() == 0) {
            showLoadingDialog("");
            getCall(AppConstants.BASE_URL + "Category/3", "", 11);
            return;
        }
        switch (v.getId()) {

            case R.id.card_breakfast: {
                titleSend = "Breakfast";
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
                        false);
                break;
            }
            case R.id.card_lunch: {
                titleSend = "Lunch";
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
                        false);
                break;
            }
            case R.id.card_dinner: {
                titleSend = "Dinner";
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
                        false);
                break;
            }
            case R.id.card_snacks: {
//                showTiffinPopup();
//                showLoadingDialog("");
//                getCallWithHeader(AppConstant.BASE_URL + "Product/MonthlyPackage", 5);
                List<String> listStore = new ArrayList<>();
                listStore.add("Monthly Offers");
                listStore.add("Weekly Offers");
                SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                showOffersDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener,
                        dismissListener, cancelListener,
                        false);
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
//            TextView textView =  view.findViewById(R.id.text_view);
//            String clickedAppName = textView.getText().toString();
//            getProducts(catId, catList.get(position).getStorelist().get(0).getsID());
        }
    };

    private void getProducts(String catId, String storeId) {
        showLoadingDialog("");
        getCallWithHeader(AppConstants.BASE_URL + "product?categoryid=" + catId + "&storeid=" + storeId, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9667536664"));
        startActivity(intent);
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 1) {
            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    startActivity(new Intent(getActivity(), BreakFastActivity.class).putExtra(AppConstant.EXTRA_1, titleSend)
                            .putExtra("isBreakfast", titleSend.equals("Breakfast")));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissDialog();
                        }
                    }, 1000);
                } else {
                    dismissDialog();
                    showComingSoon();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 11) {
            dismissDialog();
            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
//                MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    SingleInstance.getInstance().setTiffinCatList(catList);
                    this.catList = catList;
//                startActivity(new Intent(getContext(), FoodActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 5) {
            Type listType = new TypeToken<ArrayList<Product>>() {
            }.getType();
            try {
                List<Product> productList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                } else {
                    SingleInstance.getInstance().setMonthlyPkgList(productList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(getContext(), MonthlyPackageListActivity.class).putExtra(AppConstant.EXTRA_1, false));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 2000);
        } else if (callNumber == 6) {
            Type listType = new TypeToken<ArrayList<Product>>() {
            }.getType();
            try {
                List<Product> productList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                } else {
                    SingleInstance.getInstance().setMonthlyPkgList(productList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(getContext(), MonthlyPackageListActivity.class).putExtra(AppConstant.EXTRA_1, true));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 2000);
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
                        if (position == -1) return;
                        if (position == (currentStoreList.size() - 1)) {
                            dialog.dismiss();
                            return;
                        }
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

    private void showOffersDialog(Holder holder, int gravity, final BaseAdapter adapter,
                                  OnClickListener clickListener, OnItemClickListener itemClickListener,
                                  OnDismissListener dismissListener, OnCancelListener cancelListener,
                                  boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getActivity())

                .setContentHolder(holder)
//                .setHeader(R.layout.header_store)
                .setContentBackgroundResource(R.drawable.rounded_corner_white)
//                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        if (position == -1) return;
                        if (position == 2) {
                            dialog.dismiss();
                            return;
                        }
                        if (position == 0) {
                            showLoadingDialog("");
                            getCallWithHeader(AppConstant.BASE_URL + "Product/MonthlyPackage", 5);
                            dialog.dismiss();

                        } else if (position == 1) {
                            showLoadingDialog("");
                            getCallWithHeader(AppConstant.BASE_URL + "Product/WeeklyPackage", 6);
                            dialog.dismiss();
                        }
//                        getProducts(catId, currentStoreList.get(position).getsID());
//                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
//                                item + "], position = [" + position + "]");
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
