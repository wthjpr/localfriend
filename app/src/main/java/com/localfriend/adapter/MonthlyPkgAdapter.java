package com.localfriend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.AddressListActivity;
import com.localfriend.MonthlyPackageListActivity;
import com.localfriend.R;
import com.localfriend.model.Address;
import com.localfriend.model.Product;

import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class MonthlyPkgAdapter extends RecyclerView.Adapter<MonthlyPkgAdapter.DataHolder> {
    private List<Product> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private Context c;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);
    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    private boolean isCheckShow;

    public MonthlyPkgAdapter(List<Product> listdata, Context c, boolean isCheckShow) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.isCheckShow = isCheckShow;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.montyly_pkg_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        Product a = listdata.get(position);
        holder.txt_description.setText(a.getpDescription());
        holder.txt_name.setText(a.getpTitle());
        if (a.getpName().equals("Monthly")) {
            holder.txt_price.setText("Rs. " + a.getpPrice() + "/mo");
        } else {
            holder.txt_price.setText("Rs. " + a.getpPrice() + "/" + a.getpName());
        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_weekly_menu, txt_buy_now, txt_name, txt_description, txt_price;

        public DataHolder(final View itemView) {
            super(itemView);
            txt_weekly_menu = itemView.findViewById(R.id.txt_weekly_menu);
            txt_buy_now = itemView.findViewById(R.id.txt_buy_now);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_price = itemView.findViewById(R.id.txt_price);

            txt_weekly_menu.setOnClickListener(this);
            txt_buy_now.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_weekly_menu) {
                ((MonthlyPackageListActivity) c).clickMenu(listdata.get(getLayoutPosition()));
            } else if (v == txt_buy_now) {
                ((MonthlyPackageListActivity) c).clickBuyNow(listdata.get(getLayoutPosition()));
            }
        }
    }
}


