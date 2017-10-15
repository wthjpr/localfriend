package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.localfriend.R;
import com.localfriend.adapter.CustomAdapter;
import com.localfriend.model.ProductDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

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
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_all, container, false);
        GridView gridview = (GridView) myView.findViewById(R.id.all_frag_gridview);

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), allProducts);
        gridview.setAdapter(customAdapter);
        return myView;
    }

}
