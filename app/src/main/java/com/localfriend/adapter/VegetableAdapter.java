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

public class VegetableAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<VeggetableItem> listStorage;
    private Context context;

    public VegetableAdapter(Context context, List<VeggetableItem> customizedListView) {
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
            convertView = layoutinflater.inflate(R.layout.vegetable_items, parent, false);
            listViewHolder.img_veg = (ImageView)convertView.findViewById(R.id.img_veg);
            listViewHolder.tv_cost = (TextView)convertView.findViewById(R.id.tv_cost);
            listViewHolder.tv_offer = (TextView)convertView.findViewById(R.id.tv_offer);
            listViewHolder.tv_veg_name = (TextView)convertView.findViewById(R.id.tv_veg_name);
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
        listViewHolder.img_veg.setImageResource(listStorage.get(position).getVegImage());
        listViewHolder.tv_cost.setText(listStorage.get(position).getVegCost());
        listViewHolder.tv_offer.setText(listStorage.get(position).getVegOffer());
        listViewHolder.tv_veg_name.setText(listStorage.get(position).getVegName());
        return convertView;
    }

    static class ViewHolder{
        ImageView img_veg;
        TextView tv_cost,tv_offer, tv_veg_name,tv_add_cart ;

    }

}
