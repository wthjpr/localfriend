package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.AllActivity;
import com.localfriend.FoodActivity;
import com.localfriend.MainActivity;
import com.localfriend.R;
import com.localfriend.VegetableActivity;
import com.localfriend.adapter.SimpleAdapter;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.CategoryDetails;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
import ss.com.bannerslider.views.BannerSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private BannerSlider bannerSlider;
    private LinearLayout lrn_fruit, lrn_vegetable, lrn_tiffin, lrn_food, lrn_mithaiwala, lrn_discount;
    private GifImageView gif_loader;
//    private AVLoadingIndicatorView loading;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        bannerSlider = (BannerSlider) myView.findViewById(R.id.banner_slider1);
        lrn_fruit = myView.findViewById(R.id.lrn_fruit);
        lrn_vegetable = myView.findViewById(R.id.lrn_vegetable);
        lrn_tiffin = myView.findViewById(R.id.lrn_tiffin);
        lrn_food = myView.findViewById(R.id.lrn_food);
        lrn_mithaiwala = myView.findViewById(R.id.lrn_mithaiwala);
        lrn_discount = myView.findViewById(R.id.lrn_discount);
        gif_loader = myView.findViewById(R.id.gif_loader);
        setResponseListener(this);

        setTouchNClick(lrn_fruit);
        setTouchNClick(lrn_vegetable);
        setTouchNClick(lrn_tiffin);
        setTouchNClick(lrn_food);
        setTouchNClick(lrn_mithaiwala);
        setTouchNClick(lrn_discount);

        List<Banner> banners = new ArrayList<>();
        List<Slider> sliderList = SingleInstance.getInstance().getSliderList();
//        if (sliderList.size() == 0) {
//            getCall(AppConstants.BASE_URL + AppConstants.SLIDER, "", 1);
//        }

        for (int i = 0; i < sliderList.size(); i++) {
            String url = sliderList.get(i).getThumbImage();
            banners.add(new RemoteBanner(url));
        }
        bannerSlider.setBanners(banners);
        bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
//                startActivity(new Intent(getContext(), AllActivity.class));
            }
        });

        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCall(AppConstants.BASE_URL + AppConstants.SLIDER, "", 1);
    }

    private String title;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == lrn_fruit) {
            title = "Fruits";
            showLoadingDialog("");
            getCallWithHeader(AppConstants.BASE_URL + "product?categoryid=" + 4 + "&storeid=90390cdd-f991-4539-b666-488858d60a94", 3);
        } else if (v == lrn_vegetable) {
            title = "Vegetable";
            showLoadingDialog("");
            getCallWithHeader(AppConstants.BASE_URL + "product?categoryid=" + 1 + "&storeid=90390cdd-f991-4539-b666-488858d60a94", 3);
        } else if (v == lrn_tiffin) {
//            title = "Tiffin";
//            loadCategory(3);
            ((MainActivity) getActivity()).changeTab(2);
        } else if (v == lrn_food) {
            title = "Food";
            loadCategory(2);
        } else if (v == lrn_mithaiwala) {
            title = "Mithai";
            showLoadingDialog("");
            getCall(AppConstants.BASE_URL + "Category/" + 0, "", 6);
        } else if (v == lrn_discount) {
            title = "Morning Meals";
            showLoadingDialog("");
            getCall(AppConstants.BASE_URL + "Category/" + 0, "",8 );
//            MyApp.popMessage("LocalFriend", "No Deals available for now.\nThank you", getContext());
        }
    }

    private void loadCategory(int i) {
        showLoadingDialog("");
        getCall(AppConstants.BASE_URL + "Category/" + i, "", 2);
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

        if (callNumber == 1) {
            if (o.optString("status").equals("success")) {
                List<Slider> sliderList = null;
                try {
                    Type listType = new TypeToken<ArrayList<Slider>>() {
                    }.getType();
                    sliderList = new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                    SingleInstance.getInstance().setSliderList(sliderList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<Banner> banners = new ArrayList<>();
                for (Slider s : sliderList) {
                    banners.add(new RemoteBanner(s.getThumbImage()));
                }
                bannerSlider.removeAllBanners();
                bannerSlider.setBanners(banners);
                bannerSlider.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onClick(int position) {
                    }
                });
            }
        } else if (callNumber == 2) {

            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                    showComingSoon();
//                    MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    SingleInstance.getInstance().setCatList(catList);
                    startActivity(new Intent(getContext(), FoodActivity.class).putExtra(AppConstant.EXTRA_1, title));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 1000);
        } else if (callNumber == 3) {
            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    startActivity(new Intent(getContext(), VegetableActivity.class).putExtra(AppConstant.EXTRA_1, title));
                } else {
                    showComingSoon();
//                    MyApp.popMessage("Local Friend", "We are not able to find any product related to selected category & store," +
//                            " Please come back later.\nThank you.", getContext());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 1000);
        } else if (callNumber == 4) {

            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                    showComingSoon();
//                    MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    List<String> listStore = new ArrayList<>();
                    for (int i = 0; i < catList.get(3).getStorelist().size(); i++) {
                        listStore.add(catList.get(3).getStorelist().get(i).getsName());
                    }
                    if (listStore.size() == 1) {
                        getProducts("5", catList.get(3).getStorelist().get(0).getsID(), false);
                        return;
                    }
                    dismissDialog();
                    currentStoreList = catList.get(3).getStorelist();
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                    showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                            true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 6) {

            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
                    MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    List<String> listStore = new ArrayList<>();
                    for (int i = 0; i < catList.get(4).getStorelist().size(); i++) {
                        listStore.add(catList.get(4).getStorelist().get(i).getsName());
                    }
                    if (listStore.size() == 1) {
                        getMithaiProducts("5", catList.get(4).getStorelist().get(0).getsID(), false);
                        return;
                    }
                    dismissDialog();
                    currentStoreList = catList.get(4).getStorelist();
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                    showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                            true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 5) {

            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    startActivity(new Intent(getContext(), AllActivity.class).putExtra(AppConstant.EXTRA_1, title));
                } else {
                    showComingSoon();
//                    MyApp.popMessage("Local Friend", "We are not able to find any product related to selected category & store," +
//                            " Please come back later.\nThank you.", getContext());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 1000);
        } else if (callNumber == 7) {

            try {
                ProductData data = new Gson().fromJson(o.getJSONObject("data").toString(), ProductData.class);
                SingleInstance.getInstance().setProductData(data);
                if (data.getProduct().size() > 0) {
                    startActivity(new Intent(getContext(), VegetableActivity.class).putExtra(AppConstant.EXTRA_1, title));
                } else {
                    showComingSoon();
//                    MyApp.popMessage("Local Friend", "We are not able to find any product related to selected category & store," +
//                            " Please come back later.\nThank you.", getContext());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissDialog();
                }
            }, 1000);
        } else if (callNumber == 8) {
            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
//                    MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                    showComingSoon();
                } else {
                    List<String> listStore = new ArrayList<>();
                    for (int i = 0; i < catList.get(3).getStorelist().size(); i++) {
                        listStore.add(catList.get(3).getStorelist().get(i).getsName());
                    }
                    if (listStore.size() == 1) {
                        getMithaiProducts("29", catList.get(3).getStorelist().get(0).getsID(), false);
                        return;
                    }
                    dismissDialog();
                    currentStoreList = catList.get(3).getStorelist();
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), false, listStore);
                    showCompleteDialog(new ListHolder(), Gravity.CENTER, adapter, clickListener, itemClickListener, dismissListener, cancelListener,
                            true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private List<StoreList> currentStoreList;

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        if (callNumber == 2) {
            dismissDialog();
            MyApp.popMessage("Error!", "Time-out error occurred, please try again.", getContext());
        }
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error!", error, getContext());
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

        }
    };

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
                        getProducts("5", currentStoreList.get(position).getsID(), true);
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                .setOverlayBackgroundResource(R.color.transparent)
                .create();
        dialog.show();
    }

    private void getProducts(String catId, String storeId, boolean isShowDialog) {
        if (isShowDialog)
            showLoadingDialog("");
        getCallWithHeader(AppConstants.BASE_URL + "product?categoryid=" + catId + "&storeid=" + storeId, 5);

    }

    private void getMithaiProducts(String catId, String storeId, boolean isShowDialog) {
        if (isShowDialog)
            showLoadingDialog("");
        getCallWithHeader(AppConstants.BASE_URL + "product?categoryid=" + catId + "&storeid=" + storeId, 7);

    }
}
