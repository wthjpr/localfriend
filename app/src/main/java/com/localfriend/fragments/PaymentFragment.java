package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.CheckOutActivity;
import com.localfriend.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends CustomFragment {

    private RelativeLayout rl_cod;
    private RelativeLayout rl_paytm;
    private TextView tv_review_order;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        rl_cod = v.findViewById(R.id.rl_cod);
        rl_paytm = v.findViewById(R.id.rl_paytm);
        tv_review_order = v.findViewById(R.id.tv_review_order);
        setTouchNClick(rl_cod);
        setTouchNClick(rl_paytm);
        setTouchNClick(tv_review_order);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == rl_cod) {

        } else if (v == rl_paytm) {

        } else if (v == tv_review_order) {
            ((CheckOutActivity) getActivity()).changeFragmentPosition(2);
        }
    }
}
