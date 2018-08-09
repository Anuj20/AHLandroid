package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.Ipo;
import com.amithelpline.ahl.model.Policy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class IpoAdapter extends RecyclerView.Adapter<IpoAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ipo> ipoList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMinAmount, tvOpenDate, tvLastDate, tvPriceBand, tvLotSize;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvMinAmount = (TextView) view.findViewById(R.id.tvMinAmount);
            tvOpenDate = (TextView) view.findViewById(R.id.tvOpenDate);
            tvLastDate = (TextView) view.findViewById(R.id.tvLastDate);
            tvPriceBand = (TextView) view.findViewById(R.id.tvPriceBand);
            tvLotSize = (TextView) view.findViewById(R.id.tvLotSize);

        }
    }


    public IpoAdapter(Context mContext, List<Ipo> ipoList) {
        this.mContext = mContext;
        this.ipoList = ipoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ipo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Ipo ipo = ipoList.get(position);


        holder.tvName.setText(ipo.getName());
        holder.tvMinAmount.setText("Minimum Amount on Cut Off : " + ipo.getMinAmount());
        holder.tvOpenDate.setText("Open Date : " + ipo.getOpenDate());
        holder.tvLastDate.setText("Closing Date : " + ipo.getLastDate());
        holder.tvPriceBand.setText("Price Band : " + ipo.getPriceBand());
        holder.tvLotSize.setText("Lot Size : " + ipo.getLotSize());


    }

    @Override
    public int getItemCount() {
        return ipoList.size();
    }

}
