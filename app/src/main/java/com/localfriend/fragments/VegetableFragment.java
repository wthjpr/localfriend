package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.localfriend.ItemDetailActivity;
import com.localfriend.R;
import com.localfriend.adapter.VegetableAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.ProductDetails;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        GridView gridview = (GridView) myView.findViewById(R.id.all_frag_gridview);

        VegetableAdapter customAdapter = new VegetableAdapter(VegetableFragment.this, allProducts);
        gridview.setAdapter(customAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgUrl = allProducts.get(position).getpGalleryFileList().get(0);
                SingleInstance.getInstance().setSelectedProduct(allProducts.get(position));
                startActivity(new Intent(getContext(), ItemDetailActivity.class)
                        .putExtra(AppConstant.EXTRA_1, imgUrl));
            }
        });
        return myView;
    }

    public void addToCart(ProductDetails p) {
        JSONObject o = new JSONObject();

        try {
            o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
            o.put("oprationid", 1);
            o.put("pDetailsId", p.getId());
            o.put("pQuantity", 1);
            showLoadingDialog("");
            postCallJsonWithAuthorization(getActivity(), AppConstant.BASE_URL + "Cart", o, "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        MyApp.showMassage(getContext(), o.optString("message"));
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
