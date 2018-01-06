package com.localfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.localfriend.BreakFastActivity;
import com.localfriend.R;
import com.localfriend.application.MyApp;
import com.localfriend.model.ProductDetails;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
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
    private boolean isBreakfast;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);
    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public BreakfastAdapter(List<ProductDetails> allProducts, Context c, boolean isBreakfast) {
        this.inflater = LayoutInflater.from(c);
        this.allProducts = allProducts;
        this.c = c;
        this.isBreakfast = isBreakfast;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.breakfast_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(final DataHolder holder, int position) {
        final ProductDetails p = allProducts.get(position);
        holder.tv_breakfast_name.setText(p.getName());
        String string = "Rs. ";
//        byte[] utf8 = null;
//        try {
//            utf8 = string.getBytes("UTF-8");
//            string = new String(utf8, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        holder.tv_breakfast_cost.setText(string + p.getSellingPrice());
        try {
            Glide.with(c).load(p.getpGalleryFileList().get(0)).into(holder.rel_break_fast);
            Glide.with(c).load(p.getpGalleryFileList().get(0)).into(holder.rel_animated);
        } catch (Exception e) {
        }
        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("time float", MyApp.millisTo(System.currentTimeMillis()) + "");
                if (MyApp.millisTo(System.currentTimeMillis()) <= 10.3 || !isBreakfast) {
                    ((BreakFastActivity) c).addToCart(p, holder.rel_break_fast);
                } else {
                    MyApp.popMessage("Alert!", "You are not allowed to order breakfast at this time.\n" +
                            "You can order breakfast before 10.30 AM only.\nThank you.", c);
                }
            }
        });
//        holder.rel_break_fast.setBackgroundResource(item.getBreakFastImage());
    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_breakfast_cost, tv_add, tv_breakfast_name;
        ImageView rel_break_fast;
        ImageView rel_animated;


        public DataHolder(final View itemView) {
            super(itemView);
            tv_breakfast_name = itemView.findViewById(R.id.tv_breakfast_name);
            tv_breakfast_cost = itemView.findViewById(R.id.tv_breakfast_cost);
            tv_add = itemView.findViewById(R.id.tv_add);
            rel_break_fast = itemView.findViewById(R.id.rel_break_fast);
            rel_animated = itemView.findViewById(R.id.rel_animated);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((BreakFastActivity) c).gotoDetails(allProducts.get(getLayoutPosition()));
        }
    }
}


