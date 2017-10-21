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

import com.localfriend.R;
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
    public void onBindViewHolder(DataHolder holder, int position) {
        final Cartlist item = listdata.get(position);
        holder.tv_item_name.setText(item.getProductname());
        holder.tv_counter.setText(item.getQuantiy());
        Picasso.with(c.getContext()).load(item.getProductimage()).into(holder.img_food);
        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.tv_item_cost.setText(string + item.getPrice());
        holder.tv_unit.setText(item.getVarient());
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartFragment)c).increaseCount(item);
            }
        });
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartFragment)c).decreaseCount(item);
            }
        });
        holder.img_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartFragment)c).deleteItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_cost, tv_plus, tv_counter, tv_minus, tv_unit;
        ImageView img_btn_close;
        ImageView img_food;

        public DataHolder(final View itemView) {
            super(itemView);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            tv_item_cost = (TextView) itemView.findViewById(R.id.tv_item_cost);
            tv_plus = (TextView) itemView.findViewById(R.id.tv_plus);
            tv_counter = (TextView) itemView.findViewById(R.id.tv_counter);
            tv_minus = (TextView) itemView.findViewById(R.id.tv_minus);
            img_btn_close = itemView.findViewById(R.id.img_btn_close);
            img_food = (ImageView) itemView.findViewById(R.id.img_food);


        }
    }

}