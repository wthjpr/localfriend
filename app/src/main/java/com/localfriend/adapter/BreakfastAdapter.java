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

import com.localfriend.BreakFastActivity;
import com.localfriend.R;
import com.localfriend.model.ProductDetails;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.DataHolder> {
    private List<ProductDetails> allProducts;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private int count = 0;
    private Context c;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);
    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public BreakfastAdapter(List<ProductDetails> allProducts, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.allProducts = allProducts;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.breakfast_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        final ProductDetails p = allProducts.get(position);
        holder.tv_breakfast_name.setText(p.getName());
        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        holder.tv_breakfast_cost.setText(string + p.getSellingPrice());
        Picasso.with(c).load(p.getpGalleryFileList().get(0)).into(holder.rel_break_fast);
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BreakFastActivity)c).addToCart(p);
            }
        });
//        holder.rel_break_fast.setBackgroundResource(item.getBreakFastImage());
    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        TextView tv_breakfast_cost, tv_add, tv_breakfast_name;
        ImageView rel_break_fast;


        public DataHolder(final View itemView) {
            super(itemView);
            tv_breakfast_name = (TextView) itemView.findViewById(R.id.tv_breakfast_name);
            tv_breakfast_cost = (TextView) itemView.findViewById(R.id.tv_breakfast_cost);
            tv_add = (TextView) itemView.findViewById(R.id.tv_add);

            rel_break_fast = (ImageView) itemView.findViewById(R.id.rel_break_fast);
        }
    }
}


