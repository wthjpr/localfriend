package com.localfriend.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.localfriend.R;
import com.localfriend.application.MyApp;
import com.localfriend.fragments.CartFragment;
import com.localfriend.model.Cartlist;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.DataHolder> {
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private List<Cartlist> listdata;
    private Fragment c;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);
    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public CartAdapter(List<Cartlist> listdata, Fragment c) {
        this.inflater = LayoutInflater.from(c.getContext());
        this.listdata = listdata;
        this.c = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {
        final Cartlist item = listdata.get(position);
        holder.tv_item_name.setText(item.getProductname());
        holder.tv_counter.setText(item.getQuantiy() + "");
//        if (item.getQuantiy() > 10) {
//            listdata.get(position).setQuantiy(listdata.get(position).getQuantiy() - 1);
//            ((CartFragment) c).decreaseCount(item);
//            notifyDataSetChanged();
//        }
        Glide.with(c.getContext()).load(item.getProductimage()).placeholder(R.drawable.place_holder).into(holder.img_food);
        String string = "Rs. ";
//        byte[] utf8 = null;
//        try {
//            utf8 = string.getBytes("UTF-8");
//            string = new String(utf8, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        holder.tv_item_cost.setText(string + item.getSellingprice());
        holder.tv_item_cost_old.setText(string + item.getPrice());

        if (item.getPrice()==(item.getSellingprice())) {
            holder.tv_item_cost_old.setVisibility(View.GONE);
        } else {
            holder.tv_item_cost_old.setVisibility(View.VISIBLE);
            MyApp.strikeThroughText(holder.tv_item_cost_old);
        }

        holder.tv_unit.setText(item.getVarient());
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listdata.get(position).getQuantiy() >= 10) {
                    listdata.get(position).setQuantiy(10);
                    MyApp.showMassage(c.getActivity(), "Can not add more than 10 items");
                    notifyDataSetChanged();
//                    ((CartFragment) c).decreaseCount(item);
                    return;
                } else {
                    listdata.get(position).setQuantiy(listdata.get(position).getQuantiy() + 1);
                    notifyDataSetChanged();
                    ((CartFragment) c).increaseCount(item);
                }

            }
        });
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listdata.get(position).getQuantiy() > 1){
                    listdata.get(position).setQuantiy(listdata.get(position).getQuantiy() - 1);
                    ((CartFragment) c).decreaseCount(item);
                    notifyDataSetChanged();
                }


            }
        });
        holder.img_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listdata.remove(position);
                notifyDataSetChanged();
                ((CartFragment) c).deleteItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_cost, tv_item_cost_old, tv_counter, tv_unit;
        ImageView img_btn_close;
        ImageView img_food, tv_plus, tv_minus;

        public DataHolder(final View itemView) {
            super(itemView);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_cost = itemView.findViewById(R.id.tv_item_cost);
            tv_item_cost_old = itemView.findViewById(R.id.tv_item_cost_old);
            tv_plus = itemView.findViewById(R.id.tv_plus);
            tv_counter = itemView.findViewById(R.id.tv_counter);
            tv_minus = itemView.findViewById(R.id.tv_minus);
            img_btn_close = itemView.findViewById(R.id.img_btn_close);
            img_food = itemView.findViewById(R.id.img_food);


        }
    }

}