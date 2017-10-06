package com.localfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.DataHolder> {
    private List<DummyBreakfastItem> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private int count = 0;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public BreakfastAdapter(List<DummyBreakfastItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.breakfast_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        DummyBreakfastItem item = listdata.get(position);
        holder.tv_breakfast_name.setText(item.getBreakFastName());
        holder.rel_break_fast.setBackgroundResource(item.getBreakFastImage());
        holder.tv_breakfast_cost.setText(item.getBreakFastCost());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_breakfast_cost, tv_add, tv_breakfast_name;
        RelativeLayout rel_break_fast;


        public DataHolder(final View itemView) {
            super(itemView);
            tv_breakfast_name = (TextView) itemView.findViewById(R.id.tv_breakfast_name);
            tv_breakfast_cost = (TextView) itemView.findViewById(R.id.tv_breakfast_cost);
            tv_add = (TextView) itemView.findViewById(R.id.tv_add);


            rel_break_fast = (RelativeLayout) itemView.findViewById(R.id.rel_break_fast);


        }


    }

    public void setListData(ArrayList<DummyBreakfastItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }


}


