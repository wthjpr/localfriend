package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.localfriend.R;
import com.localfriend.adapter.AllItem;
import com.localfriend.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {


    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_all, container, false);
        GridView gridview = (GridView)myView.findViewById(R.id.all_frag_gridview);
        List<AllItem> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), allItems);
        gridview.setAdapter(customAdapter);
        return myView;
    }

    private List<AllItem> getAllItemObject(){
        List<AllItem> items = new ArrayList<>();

        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));
        items.add(new AllItem("Rs.50", R.drawable.bakery,"Veggie Burger"));


        return items;
    }


}
