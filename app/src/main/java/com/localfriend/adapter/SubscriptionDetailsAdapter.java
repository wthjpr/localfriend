package com.localfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.R;
import com.localfriend.model.SubscriptionModel;

import java.util.List;

public class SubscriptionDetailsAdapter extends RecyclerView.Adapter<SubscriptionDetailsAdapter.MyViewHolder>
{
    private Context context;
    private List<SubscriptionModel.Data.Track> list;
    public SubscriptionDetailsAdapter(Context context, List<SubscriptionModel.Data.Track> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(context).inflate(R.layout.item_rv_subscription, parent,false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        SubscriptionModel.Data.Track item = list.get(position);

        holder.detail_name.setText(item.getDetailsname());
        if (item.getBreakfast().isEmpty()){
            holder.brkfast_chk.setImageResource(R.drawable.ic_black_cross);
        } else if (item.getBreakfast().equals(0)){
            holder.brkfast_chk.setImageResource(R.drawable.ic_red_cross);
        } else {
            holder.brkfast_chk.setImageResource(R.drawable.ic_green_tick);
        }
        if (item.getLunch().isEmpty()){
            holder.lunch_chk.setImageResource(R.drawable.ic_black_cross);
        } else if (item.getLunch().equals(0)){
            holder.lunch_chk.setImageResource(R.drawable.ic_red_cross);
        } else {
            holder.lunch_chk.setImageResource(R.drawable.ic_green_tick);
        }

        if (item.getDinner().isEmpty()){
            holder.dinner_chk.setImageResource(R.drawable.ic_black_cross);
        } else if (item.getDinner().equals(0)){
            holder.dinner_chk.setImageResource(R.drawable.ic_red_cross);
        } else {
            holder.dinner_chk.setImageResource(R.drawable.ic_green_tick);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView detail_name;
        ImageView brkfast_chk, lunch_chk, dinner_chk;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            detail_name = itemView.findViewById(R.id.detail_name);
            brkfast_chk = itemView.findViewById(R.id.brkfast_chk);
            lunch_chk = itemView.findViewById(R.id.lunch_chk);
            dinner_chk = itemView.findViewById(R.id.dinner_chk);
        }
    }
}
