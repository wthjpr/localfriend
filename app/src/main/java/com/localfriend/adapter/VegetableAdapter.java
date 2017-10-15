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
import com.localfriend.model.ProductDetails;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class VegetableAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ProductDetails> productList;
    private Context context;

    public VegetableAdapter(Context context, List<ProductDetails> productList) {
        this.context = context;
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
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
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.vegetable_items, parent, false);

            listViewHolder.img_veg = (ImageView) convertView.findViewById(R.id.img_veg);
            listViewHolder.tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
            listViewHolder.tv_veg_name = (TextView) convertView.findViewById(R.id.tv_veg_name);

            listViewHolder.tv_add_cart = (TextView) convertView.findViewById(R.id.tv_add_cart);
            listViewHolder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(context).load(productList.get(position).getpGalleryFileList().get(0)).into(listViewHolder.img_veg);
        } catch (Exception e) {
            listViewHolder.img_veg.setImageResource(R.drawable.apple);
        }
        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        listViewHolder.tv_cost.setText(string + " " + productList.get(position).getPrice());
        listViewHolder.tv_veg_name.setText(productList.get(position).getName() + "" +
                "\n" + productList.get(position).getUnit() + " " + productList.get(position).getuType());
        return convertView;
    }

    static class ViewHolder {
        ImageView img_veg;
        TextView tv_cost, tv_offer, tv_veg_name, tv_add_cart;

    }

}
