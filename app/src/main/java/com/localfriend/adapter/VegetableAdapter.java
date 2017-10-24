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
import com.localfriend.application.MyApp;
import com.localfriend.fragments.VegetableFragment;
import com.localfriend.model.ProductDetails;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class VegetableAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ProductDetails> productList;
    private Fragment context;

    public VegetableAdapter(Fragment context, List<ProductDetails> productList) {
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

        final ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();

            convertView = layoutinflater.inflate(R.layout.vegetable_items, parent, false);
            listViewHolder.img_veg = convertView.findViewById(R.id.img_veg);
            listViewHolder.img_anim = convertView.findViewById(R.id.img_anim);
            listViewHolder.tv_cost = convertView.findViewById(R.id.tv_cost);
            listViewHolder.tv_cost_old = convertView.findViewById(R.id.tv_cost_old);
            listViewHolder.tv_veg_name = convertView.findViewById(R.id.tv_veg_name);
            listViewHolder.txt_unit = convertView.findViewById(R.id.txt_unit);
            listViewHolder.img_wish = convertView.findViewById(R.id.img_wish);

            listViewHolder.tv_add_cart = convertView.findViewById(R.id.tv_add_cart);
            listViewHolder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listViewHolder.img_anim.setImageBitmap(listViewHolder.img_veg.getDrawingCache());
                    ((VegetableFragment) context).addToCart(productList.get(position), listViewHolder.img_veg);
                }
            });

            listViewHolder.img_wish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productList.get(position).setWished(true);
                    ((VegetableFragment) context).addToWish(productList.get(position));
                    notifyDataSetChanged();
                }
            });

            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Picasso.with(context.getContext()).load(productList.get(position).getpGalleryFileList().get(0)).into(listViewHolder.img_veg);
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

        if (productList.get(position).isWished()) {
            listViewHolder.img_wish.setImageResource(R.drawable.wish_active);
        } else {
            listViewHolder.img_wish.setImageResource(R.drawable.wish_deactive);
        }

        listViewHolder.tv_cost_old.setText(string + " " + productList.get(position).getPrice());
        MyApp.strikeThroughText(listViewHolder.tv_cost_old);
        listViewHolder.tv_cost.setText(string + " " + productList.get(position).getSellingPrice());
        listViewHolder.tv_veg_name.setText(productList.get(position).getName() + "");
        listViewHolder.txt_unit.setText(productList.get(position).getUnit() + " " + productList.get(position).getuType());
        return convertView;
    }

    public static class ViewHolder {
        ImageView img_veg, img_anim, img_wish;
        TextView tv_cost, tv_cost_old, txt_unit, tv_veg_name, tv_add_cart;

    }

}
