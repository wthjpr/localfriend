package com.localfriend.adapter;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localfriend.R;
import com.localfriend.fragments.HistoryFragment;
import com.localfriend.fragments.HistorySubscriptionFragment;
import com.localfriend.model.History;
import com.localfriend.model.SubHistory;

import java.util.List;

/**
 * Created by HP on 7/26/2017.
 */

public class HistorySubscriptionAdapter extends RecyclerView.Adapter<HistorySubscriptionAdapter.DataHolder> {
    private List<SubHistory> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    private Fragment c;

    public interface ItemClickCallback {
        void onItemClick(int p);

        void onSecondaryIconClick(int p);

    }


    public void SetItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemclickcallback = itemClickCallback;
    }

    public HistorySubscriptionAdapter(List<SubHistory> listdata, Fragment c) {
        this.inflater = LayoutInflater.from(c.getContext());
        this.listdata = listdata;
        this.c = c;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new DataHolder(view);

    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        SubHistory a = listdata.get(position);
        holder.txt_order_id.setText("Order ID : LF_ " + a.getOrderID());
        holder.txt_date.setText(a.getCategoryname());
        String string = "Rs. ";
//        byte[] utf8 = null;
//        try {
//            utf8 = string.getBytes("UTF-8");
//            string = new String(utf8, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        holder.txt_price.setText(string + a.getTotalprice());
//        holder.txt_items_count.setText("No. of items: " + a.getOrder_TotalProduct());
        holder.txt_items_count.setText(a.getCashmethod());

        try{
            if(!holder.txt_items_count.getText().toString().equals("Cancelled")){
                holder.txt_items_count.setTextColor(Color.parseColor("#3CBEA3"));
            }
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_order_id, txt_date, txt_items_count, txt_price, txt_repeat;


        public DataHolder(final View itemView) {
            super(itemView);
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_items_count = itemView.findViewById(R.id.txt_items_count);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_repeat = itemView.findViewById(R.id.txt_repeat);

            Shader textShader = new LinearGradient(0, 0, 0, 50,
                    new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
//            txt_address_type.getPaint().setShader(textShader);

//            txt_edit.setOnClickListener(this);
//            txt_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
            txt_repeat.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == txt_repeat) {
//                ((HistorySubscriptionFragment) c).viewDetails(listdata.get(getLayoutPosition()).getorder);
            }
        }
    }

}


