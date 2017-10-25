package com.localfriend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localfriend.AddressListActivity;
import com.localfriend.R;
import com.localfriend.model.Address;
import com.localfriend.model.History;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.DataHolder> {
    private List<History> listdata;
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

    public CurrentAdapter(List<History> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_current, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        History a = listdata.get(position);
        holder.txt_order_id.setText("Order ID : LF" + a.getOrder_Id());
        holder.txt_date.setText(a.getOrder_date());
        String string = "";
        byte[] utf8 = null;
        try {
            utf8 = string.getBytes("UTF-8");
            string = new String(utf8, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        holder.txt_price.setText(string + a.getOrder_SubTotal());
        holder.txt_items_count.setText("No. of items: " + a.getOrder_TotalProduct());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_order_id, txt_date, txt_items_count, txt_price;


        public DataHolder(final View itemView) {
            super(itemView);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_items_count = itemView.findViewById(R.id.txt_items_count);
            txt_price = itemView.findViewById(R.id.txt_price);

            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
//            txt_address_type.getPaint().setShader(textShader);

//            txt_edit.setOnClickListener(this);
//            txt_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }

}


