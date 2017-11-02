package com.localfriend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.localfriend.R;
import com.localfriend.application.MyApp;
import com.localfriend.model.Checkout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class ViewItemsAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private boolean isGrid;
    private List<Checkout.ChecklistItem> listData;

    public ViewItemsAdapter(Context context, boolean isGrid, List<Checkout.ChecklistItem> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.isGrid = isGrid;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {

            view = layoutInflater.inflate(R.layout.item_view_items, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.tv_item_name = view.findViewById(R.id.tv_item_name);
            viewHolder.tv_unit = view.findViewById(R.id.tv_unit);
            viewHolder.tv_item_cost = view.findViewById(R.id.tv_item_cost);
            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
//            viewHolder.textView.getPaint().setShader(textShader);
            viewHolder.img_food = view.findViewById(R.id.img_food);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();

        viewHolder.tv_item_name.setText(listData.get(position).getProductname());
        viewHolder.tv_unit.setText(listData.get(position).getVarient());
        viewHolder.tv_item_cost.setText(MyApp.getRupeeCurrency() + listData.get(position).getSellingprice());
        Glide.with(context).load(listData.get(position).getProductimage()).placeholder(R.drawable.place_holder).into(viewHolder.img_food);


        return view;
    }

    static class ViewHolder {
        TextView tv_item_name;
        TextView tv_item_cost;
        TextView tv_unit;
        ImageView img_food;
    }
}
