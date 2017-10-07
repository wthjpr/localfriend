package com.localfriend.adapter;

/**
 * Created by DJ-PC on 3/16/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.localfriend.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<AllItem> listStorage;
    private Context context;

    public CustomAdapter(Context context, List<AllItem> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.all_items, parent, false);

            listViewHolder.img_item = (ImageView)convertView.findViewById(R.id.img_item);
            listViewHolder.tv_item_cost = (TextView)convertView.findViewById(R.id.tv_item_cost);
            listViewHolder.tv_item_name = (TextView)convertView.findViewById(R.id.tv_item_name);
            listViewHolder.tv_add_cart = (TextView)convertView.findViewById(R.id.tv_add_cart);
            listViewHolder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.img_item.setImageResource(listStorage.get(position).getItemImage());
        listViewHolder.tv_item_cost.setText(listStorage.get(position).getItemCost());
        listViewHolder.tv_item_name.setText(listStorage.get(position).getItemName());
        return convertView;
    }

    static class ViewHolder{
        ImageView img_item;
        TextView tv_item_cost,tv_item_name, tv_add_cart ;

    }

}
