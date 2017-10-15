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
import com.localfriend.adapter.CustomAdapter;
import com.localfriend.adapter.VegetableAdapter;
import com.localfriend.adapter.VeggetableItem;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.ProductDetails;
import com.localfriend.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VegetableFragment extends Fragment {

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
        View myView = inflater.inflate(R.layout.fragment_vegetable, container, false);
        GridView gridview = (GridView) myView.findViewById(R.id.all_frag_gridview);

        VegetableAdapter customAdapter = new VegetableAdapter(getActivity(), allProducts);
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

}
