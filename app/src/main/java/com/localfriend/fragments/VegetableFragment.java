package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.localfriend.R;
import com.localfriend.adapter.VegetableAdapter;
import com.localfriend.adapter.VeggetableItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VegetableFragment extends Fragment {


    public VegetableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_vegetable, container, false);
        GridView gridview = (GridView)myView.findViewById(R.id.all_frag_gridview);
        List<VeggetableItem> veggetableItems = getAllItemObject();
        VegetableAdapter vegetableAdapter = new VegetableAdapter(getActivity(), veggetableItems);
        gridview.setAdapter(vegetableAdapter);
        return myView;
    }
    private List<VeggetableItem> getAllItemObject(){
        List<VeggetableItem> items = new ArrayList<>();

        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));
        items.add(new VeggetableItem("Rs.50","50% Off",R.drawable.apple,"Shimla Apple\n 1 Kg"));




        return items;
    }
}
