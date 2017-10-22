package com.localfriend.adapter;

/**
 * Created by DJ-PC on 3/16/2016.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.localfriend.R;
import com.localfriend.fragments.AllFragment;
import com.localfriend.model.Product;
import com.localfriend.model.ProductDetails;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ProductDetails> productList;
    private Fragment context;

    public CustomAdapter(Fragment context, List<ProductDetails> productList) {
        this.context = context;
        layoutinflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = layoutinflater.inflate(R.layout.all_items, parent, false);

            listViewHolder.img_item =  convertView.findViewById(R.id.img_item);
            listViewHolder.tv_item_cost =  convertView.findViewById(R.id.tv_item_cost);
            listViewHolder.tv_item_name =  convertView.findViewById(R.id.tv_item_name);
            listViewHolder.tv_add_cart =  convertView.findViewById(R.id.tv_add_cart);
            listViewHolder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AllFragment)context).addToCart(productList.get(position));
                }
            });

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(context.getActivity()).load(productList.get(position).getpGalleryFileList().get(0)).into(listViewHolder.img_item);
        } catch (Exception e) {
            listViewHolder.img_item.setImageResource(R.drawable.bakery);
        }
        String string = "\u20B9";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        listViewHolder.tv_item_cost.setText(string +" "+ productList.get(position).getPrice());
        listViewHolder.tv_item_name.setText(productList.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        ImageView img_item;
        TextView tv_item_cost, tv_item_name, tv_add_cart;

    }

}
