package com.localfriend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localfriend.BreakFastActivity;
import com.localfriend.R;


public class TiffinFragment extends Fragment implements View.OnClickListener {
    private CardView card_breakfast, card_lunch, card_dinner, card_snacks;
    private ImageView img_snacks, img_dinner, img_lunch, img_breakfast;
    private TextView tv_breakfast, tv_lunch, tv_dinner, tv_snacks;

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
        card_breakfast = myView.findViewById(R.id.card_breakfast);
        card_lunch = myView.findViewById(R.id.card_lunch);
        card_dinner = myView.findViewById(R.id.card_dinner);
        card_snacks = myView.findViewById(R.id.card_snacks);
        img_breakfast = (ImageView) myView.findViewById(R.id.img_breakfast);
        img_lunch = (ImageView) myView.findViewById(R.id.img_lunch);
        img_dinner = (ImageView) myView.findViewById(R.id.img_dinner);
        img_snacks = (ImageView) myView.findViewById(R.id.img_snacks);

        tv_breakfast = (TextView) myView.findViewById(R.id.tv_breakfast);
        tv_lunch = (TextView) myView.findViewById(R.id.tv_lunch);
        tv_dinner = (TextView) myView.findViewById(R.id.tv_dinner);
        tv_snacks = (TextView) myView.findViewById(R.id.tv_snacks);
        hidenView();
        showView();
        card_breakfast.setOnClickListener(this);
        card_lunch.setOnClickListener(this);
        card_dinner.setOnClickListener(this);
        card_snacks.setOnClickListener(this);


        return myView;
    }

    public void hidenView() {
        tv_breakfast.setVisibility(View.GONE);
        img_breakfast.setVisibility(View.GONE);
        tv_lunch.setVisibility(View.GONE);
        img_lunch.setVisibility(View.GONE);
        tv_dinner.setVisibility(View.GONE);
        img_dinner.setVisibility(View.GONE);
        tv_snacks.setVisibility(View.GONE);
        img_snacks.setVisibility(View.GONE);

    }

    public void showView() {
        Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.bottom_up);
        tv_breakfast.startAnimation(bottomUp);
        tv_breakfast.setVisibility(View.VISIBLE);
        img_breakfast.startAnimation(bottomUp);
        img_breakfast.setVisibility(View.VISIBLE);
        tv_lunch.startAnimation(bottomUp);
        tv_lunch.setVisibility(View.VISIBLE);
        img_lunch.startAnimation(bottomUp);
        img_lunch.setVisibility(View.VISIBLE);
        tv_dinner.startAnimation(bottomUp);
        tv_dinner.setVisibility(View.VISIBLE);
        img_dinner.startAnimation(bottomUp);
        img_dinner.setVisibility(View.VISIBLE);
        tv_snacks.startAnimation(bottomUp);
        tv_snacks.setVisibility(View.VISIBLE);
        img_snacks.startAnimation(bottomUp);
        img_snacks.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_breakfast: {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_lunch: {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_dinner: {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
            case R.id.card_snacks: {
                startActivity(new Intent(getActivity(), BreakFastActivity.class));
                break;
            }
        }
    }
}
