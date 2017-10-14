package com.localfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.DataHolder> {
    private List<DummyCartItem> listdata;
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

    public CartAdapter(List<DummyCartItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        DummyCartItem item = listdata.get(position);
        holder.tv_item_name.setText(item.getItemName());
        holder.img_food.setImageResource(item.getItemImage());
        holder.tv_item_cost.setText(item.getItemCost());

    }
    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_cost, tv_plus, tv_counter, tv_minus;
        ImageButton img_btn_close;
        ImageView img_food;

        public DataHolder(final View itemView) {
            super(itemView);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            tv_item_cost = (TextView) itemView.findViewById(R.id.tv_item_cost);
            tv_plus = (TextView) itemView.findViewById(R.id.tv_plus);
            tv_counter = (TextView) itemView.findViewById(R.id.tv_counter);
            tv_minus = (TextView) itemView.findViewById(R.id.tv_minus);

            img_btn_close=(ImageButton)itemView.findViewById(R.id.img_btn_close);

            img_food=(ImageView)itemView.findViewById(R.id.img_food);

        }
    }

    public void setListData(ArrayList<DummyCartItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }
}