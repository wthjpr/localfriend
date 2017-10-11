package com.localfriend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.AddressListActivity;
import com.localfriend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.DataHolder> {
    private List<DummyCartItem> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private int count = 0;
    private Context c;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public AddressAdapter(List<DummyCartItem> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.adderess_item, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
//        DummyCartItem item = listdata.get(position);
//        holder.tv_item_name.setText(item.getItemName());
//        holder.img_food.setImageResource(item.getItemImage());
//        holder.tv_item_cost.setText(item.getItemCost());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_edit, tv_item_cost, tv_plus, tv_counter, tv_minus;
        ImageButton img_btn_close;
        ImageView img_food;


        public DataHolder(final View itemView) {
            super(itemView);
            txt_edit = (TextView) itemView.findViewById(R.id.txt_edit);
            txt_edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_edit) {
                ((AddressListActivity) c).setEditClick(getLayoutPosition());
            }
        }
    }

    public void setListData(ArrayList<DummyCartItem> exerciseList) {
        this.listdata.clear();
        this.listdata.addAll(exerciseList);

    }
}


