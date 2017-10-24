package com.localfriend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.AddressListActivity;
import com.localfriend.R;
import com.localfriend.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.DataHolder> {
    private List<Address> listdata;
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

    public AddressAdapter(List<Address> listdata, Context c) {
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
        Address a = listdata.get(position);
        holder.txt_address_type.setText(a.getAddType());
        holder.txt_name.setText(a.getAddName());
        String address = "";
        if (a.getAddDetails().length() > 0) {
            address += a.getAddDetails() + ", ";
        }
        if (a.getAddDetails1().length() > 0) {
            address += a.getAddDetails1() + ", ";
        }
        if (a.getAddDetails2().length() > 0) {
            address += a.getAddDetails2() + ", ";
        }
        if (a.getAddZipCode().length() > 0) {
            address += "(" + a.getAddZipCode() + ")\n";
        }
        if (a.getAddSatate().length() > 0) {
            address += a.getAddSatate() + "";
        }

        holder.txt_address.setText(address);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_edit, txt_delete, txt_address_type, txt_name, txt_address;


        public DataHolder(final View itemView) {
            super(itemView);
            txt_edit = itemView.findViewById(R.id.txt_edit);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_address = itemView.findViewById(R.id.txt_address);

            txt_address_type = itemView.findViewById(R.id.txt_address_type);

            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
            txt_address_type.getPaint().setShader(textShader);

            txt_edit.setOnClickListener(this);
            txt_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_edit) {
                ((AddressListActivity) c).setEditClick(listdata.get(getLayoutPosition()));
            } else if (v == txt_delete) {
                ((AddressListActivity) c).setDeleteClick(listdata.get(getLayoutPosition()));
            }

        }
    }

}


