package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.localfriend.ItemDetailActivity;
import com.localfriend.R;
import com.localfriend.VegetableActivity;
import com.localfriend.adapter.VegetableAdapter;
import com.localfriend.adapter.WishListAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.Cart;
import com.localfriend.model.ProductDetails;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VegetableFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private List<ProductDetails> allProducts;

    public VegetableFragment() {
        // Required empty public constructor
    }

    public VegetableFragment(List<ProductDetails> allProducts) {
        this.allProducts = allProducts;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setResponseListener(this);
        View myView = inflater.inflate(R.layout.fragment_vegetable, container, false);
        GridView gridview = myView.findViewById(R.id.all_frag_gridview);

        VegetableAdapter customAdapter = new VegetableAdapter(VegetableFragment.this, allProducts);
        gridview.setAdapter(customAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String imgUrl = allProducts.get(position).getpGalleryFileList().get(0);
                    SingleInstance.getInstance().setSelectedProduct(allProducts.get(position));
                    startActivity(new Intent(getContext(), ItemDetailActivity.class)
                            .putExtra(AppConstant.EXTRA_1, imgUrl));
                } catch (Exception e) {
                    String imgUrl = "";
                    SingleInstance.getInstance().setSelectedProduct(allProducts.get(position));
                    startActivity(new Intent(getContext(), ItemDetailActivity.class)
                            .putExtra(AppConstant.EXTRA_1, imgUrl));
                }
            }
        });
        return myView;
    }

    public void addToCart(ProductDetails p, ImageView view) {
        JSONObject o = new JSONObject();
        ((VegetableActivity) getActivity()).makeFlyAnimation(view, p.getId());
        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 1);
            o.put("pDetailsId", p.getId());
            o.put("pQuantity", 1);
//            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void addToWish(ProductDetails p, boolean isWish) {
        if (isWish) {
            JSONObject o = new JSONObject();

            try {
                o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
                o.put("oprationid", 3);
                o.put("pDetailsId", p.getId());
//                showLoadingDialog("");
                postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "WishList", o, "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject o = new JSONObject();
            try {
                o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
                o.put("oprationid", 1);
                o.put("pDetailsId", p.getId());
//                showLoadingDialog("");
                postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "WishList", o, "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        getCallWithHeader(AppConstant.BASE_URL + "WishList", 325);
        getCallWithHeader(AppConstant.BASE_URL + "Cart", 33);
        dismissDialog();
//        MyApp.showMassage(getContext(), o.optString("message"));
        if (callNumber == 325) {
            try {
                Cart c = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (c.getWishListlist().size() > 0) {
                    MyApp.getApplication().writeWishList(c.getWishListlist());
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(callNumber == 33){
            try {
                Cart c = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (c.getCartlist().size() > 0) {
                    MyApp.getApplication().writeCart(c);
//                    txt_cart_count.setVisibility(View.VISIBLE);
//                    txt_cart_count.setText(c.getCartlist().size() + "");
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, c.getCartlist().size());
                    HashMap<String, String> map = MyApp.getApplication().readType();
                    for (int i = 0; i < c.getCartlist().size(); i++) {
                        if (!map.containsKey(c.getCartlist().get(i).getId())) {
                            map.put(c.getCartlist().get(i).getId(), c.getCartlist().get(i).getId());
                        }
                    }
                    MyApp.getApplication().writeType(map);
                } else {
//                    txt_cart_count.setVisibility(View.GONE);
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, 0);
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


}
