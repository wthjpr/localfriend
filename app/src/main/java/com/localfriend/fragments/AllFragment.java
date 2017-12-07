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
import com.google.gson.JsonObject;
import com.localfriend.AllActivity;
import com.localfriend.ItemDetailActivity;
import com.localfriend.R;
import com.localfriend.VegetableActivity;
import com.localfriend.adapter.CustomAdapter;
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
public class AllFragment extends CustomFragment implements CustomFragment.ResponseCallback {

    private List<ProductDetails> allProducts;

    public AllFragment() {
        // Required empty public constructor
    }

    public AllFragment(List<ProductDetails> allProducts) {
        this.allProducts = allProducts;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setResponseListener(this);
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_all, container, false);
        GridView gridview = (GridView) myView.findViewById(R.id.all_frag_gridview);

        CustomAdapter customAdapter = new CustomAdapter(AllFragment.this, allProducts);
        gridview.setAdapter(customAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String imgUrl = allProducts.get(position).getpGalleryFileList().get(0);
                String catId = allProducts.get(position).getCategoryId();
                SingleInstance.getInstance().setSelectedProduct(allProducts.get(position));
                startActivity(new Intent(getContext(), ItemDetailActivity.class)
                        .putExtra(AppConstant.EXTRA_1, imgUrl).putExtra(AppConstant.EXTRA_2,
                                catId));
            }
        });
        return myView;
    }

    public void addToCart(ImageView view, ProductDetails p) {
        JSONObject o = new JSONObject();
        ((AllActivity) getActivity()).makeFlyAnimation(view, p.getId());
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

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
//        {"status":"success","message":"successfully add quantity"}
//        MyApp.popMessage("Local Friend",);
        dismissDialog();
//        MyApp.showMassage(getContext(), o.optString("message"));
        getCallWithHeader(AppConstant.BASE_URL + "Cart", 33);
        if (callNumber == 33) {
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
