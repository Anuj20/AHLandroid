package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.Currency;
import com.amithelpline.ahl.model.Pollution;

import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class PollutionAdapter extends RecyclerView.Adapter<PollutionAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pollution> pollutionList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVehicle, tvDate, tvDesc;

        public MyViewHolder(View view) {
            super(view);
            tvVehicle = (TextView) view.findViewById(R.id.tvVehicleNo);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvDesc = (TextView) view.findViewById(R.id.tvDescription);


        }
    }


    public PollutionAdapter(Context mContext, List<Pollution> pollutionList) {
        this.mContext = mContext;
        this.pollutionList = pollutionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pollution, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Pollution pollution = pollutionList.get(position);


        holder.tvVehicle.setText(pollution.getVehicleNo());
        holder.tvDate.setText(pollution.getDueDate());
        holder.tvDesc.setText(pollution.getDescription());


    }

    @Override
    public int getItemCount() {
        return pollutionList.size();
    }
}
