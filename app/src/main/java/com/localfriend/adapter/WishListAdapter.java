package com.localfriend.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.localfriend.R;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.WishListFragment;
import com.localfriend.model.Cartlist;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.DataHolder> {
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

    public WishListAdapter(List<Cartlist> listdata, Fragment c) {
        this.inflater = LayoutInflater.from(c.getContext());
        this.listdata = listdata;
        this.c = c;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.wishlist_item, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataHolder holder, final int position) {
        final Cartlist item = listdata.get(position);
        holder.tv_item_name.setText(item.getProductname());
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
        holder.tv_unit.setText(item.getVarient());

        holder.tv_move_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WishListFragment) c).moveToCart(item, holder.img_food);
                listdata.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.img_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listdata.remove(position);
                notifyDataSetChanged();
                ((WishListFragment) c).deleteItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_cost, tv_move_to_cart, tv_unit;
        ImageView img_btn_close;
        ImageView img_food;

        public DataHolder(final View itemView) {
            super(itemView);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_cost = itemView.findViewById(R.id.tv_item_cost);
            tv_move_to_cart = itemView.findViewById(R.id.tv_move_to_cart);
            img_btn_close = itemView.findViewById(R.id.img_btn_close);
            img_food = itemView.findViewById(R.id.img_food);
        }
    }

}