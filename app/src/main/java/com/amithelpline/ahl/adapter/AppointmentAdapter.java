package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.Appointment;
import com.amithelpline.ahl.model.Property;

import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<Appointment> appointmentList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDate, tvTime;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvTime = (TextView) view.findViewById(R.id.tvTime);


        }
    }


    public AppointmentAdapter(Context mContext, List<Appointment> appointmentList) {
        this.mContext = mContext;
        this.appointmentList = appointmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

       /* if (TextUtils.equals(property.getPolicyType(), "Life")) {




        } else if (TextUtils.equals(policy.getPolicyType(), "General")) {

        }*/

        holder.tvName.setText(appointment.getName());
        holder.tvDate.setText(appointment.getAppDate());
        holder.tvTime.setText(appointment.getAppTime());


    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
