package com.localfriend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.BreakFastActivity;
import com.localfriend.R;


public class TiffinFragment extends Fragment implements View.OnClickListener {
    CardView card_breakfast, card_lunch, card_dinner, card_snacks;

    public TiffinFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_tiffin, container, false);
        card_breakfast=(CardView)myView.findViewById(R.id.card_breakfast);
        card_lunch=(CardView)myView.findViewById(R.id.card_lunch);
        card_dinner=(CardView)myView.findViewById(R.id.card_dinner);
        card_snacks=(CardView)myView.findViewById(R.id.card_snacks);
        card_breakfast.setOnClickListener(this);
        card_lunch.setOnClickListener(this);
        card_dinner.setOnClickListener(this);
        card_snacks.setOnClickListener(this);
        return myView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_breakfast:
            {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_lunch:
            {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_dinner:
            {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_snacks:
            {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
        }
    }
}
