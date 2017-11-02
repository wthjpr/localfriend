package com.localfriend.adapter;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.AddressListActivity;
import com.localfriend.R;
import com.localfriend.application.MyApp;
import com.localfriend.fragments.ReviewFragment;
import com.localfriend.fragments.ScheduleFragment;
import com.localfriend.model.Checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.DataHolder> {
    public List<Checkout.CheckoutListData> listdata;
    private LayoutInflater inflater;
    private Fragment c;
    private boolean isReview;


    public CheckoutAdapter(List<Checkout.CheckoutListData> listdata, Fragment c, boolean isReview) {
        this.inflater = LayoutInflater.from(c.getContext());
        this.listdata = listdata;
        this.isReview = isReview;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.checkout_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        Checkout.CheckoutListData a = listdata.get(position);
        holder.txt_category.setText(a.getCategoryname());
        holder.txt_price.setText("Total Price :- "+ MyApp.getRupeeCurrency() + a.getPayamount());
        try {
            holder.select_time.setText(MyApp.parseDateToddMMMyyyy(a.getTimestemp().get(a.getSelection()).getTimedate())
                    + "      " + a.getTimestemp().get(a.getSelection()).getTimestemp());
        } catch (Exception e) {
            holder.select_time.setText("Not available");
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }



    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_view_items, select_time, txt_category, txt_price;
        ImageView img_select_time, img_view_items;


        public DataHolder(final View itemView) {
            super(itemView);
            img_select_time = itemView.findViewById(R.id.img_select_time);
            txt_view_items = itemView.findViewById(R.id.txt_view_items);
            img_view_items = itemView.findViewById(R.id.img_view_items);
            select_time = itemView.findViewById(R.id.select_time);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_price = itemView.findViewById(R.id.txt_price);

            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
//            txt_address_type.getPaint().setShader(textShader);

            if (isReview)
                img_select_time.setVisibility(View.GONE);
            img_select_time.setOnClickListener(this);
//            select_time.setOnClickListener(this);
            txt_view_items.setOnClickListener(this);
            img_view_items.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_view_items) {
                if (isReview)
                    ((ReviewFragment) c).viewItemsClick(listdata.get(getLayoutPosition()));
                else
                    ((ScheduleFragment) c).viewItemsClick(listdata.get(getLayoutPosition()));
            } else if (v == img_view_items) {
                if (isReview)
                    ((ReviewFragment) c).viewItemsClick(listdata.get(getLayoutPosition()));
                else
                    ((ScheduleFragment) c).viewItemsClick(listdata.get(getLayoutPosition()));
            } else if (v == img_select_time) {
                ((ScheduleFragment) c).setTimingsClick(listdata.get(getLayoutPosition()), getLayoutPosition(),
                        listdata.get(getLayoutPosition()).getCategoryid());
            }
        }
    }

}


