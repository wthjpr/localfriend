package com.localfriend.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localfriend.CheckOutActivity;
import com.localfriend.R;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.adapter.DummyCartData;
import com.localfriend.adapter.DummyCartItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private TextView tv_checkout;
    private RecyclerView recy_cart;
    private CartAdapter adapter;
    private ArrayList listdata;
    public CartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_cart, container, false);
        tv_checkout = (TextView) myView.findViewById(R.id.tv_checkout);

        recy_cart = (RecyclerView) myView.findViewById(R.id.recy_cart);
        listdata = (ArrayList) DummyCartData.getListData();
        recy_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(listdata, getContext());
        recy_cart.setAdapter(adapter);
        tv_checkout=(TextView)myView.findViewById(R.id.tv_checkout);
        tv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CheckOutActivity.class));
            }
        });
        return myView;
    }

}
