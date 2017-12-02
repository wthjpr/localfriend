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

import com.localfriend.R;

import java.util.List;

/**
 * Created by SONI on 10/14/2017.
 */

public class SimpleAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private boolean isGrid;
    private List<String> listData;

    public SimpleAdapter(Context context, boolean isGrid, List<String> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.isGrid = isGrid;
        this.listData = listData;
        listData.add("Cancel");
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
//            if (isGrid) {
//                view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
//            } else {
//                view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);
//            }

            view = layoutInflater.inflate(R.layout.item_store, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
            viewHolder.textView.getPaint().setShader(textShader);
//            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();
//        switch (position) {
//            case 0:
                viewHolder.textView.setText(listData.get(position));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_plus_icon);
//                break;
//            case 1:
//                viewHolder.textView.setText(context.getString(R.string.google_maps_title));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_maps_icon);
//                break;
//            default:
//                viewHolder.textView.setText(context.getString(R.string.google_messenger_title));
//                viewHolder.imageView.setImageResource(R.drawable.ic_google_messenger_icon);
//                break;
//        }

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
