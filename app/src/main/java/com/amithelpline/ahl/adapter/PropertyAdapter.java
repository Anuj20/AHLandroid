package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.Policy;
import com.amithelpline.ahl.model.Property;

import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Property> propertyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress, tvAddress1, tvCity, tvSize, tvRent;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvAddress1 = (TextView) view.findViewById(R.id.tvAddress1);
            tvCity = (TextView) view.findViewById(R.id.tvCity);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            tvRent = (TextView) view.findViewById(R.id.tvRent);

        }
    }


    public PropertyAdapter(Context mContext, List<Property> propertyList) {
        this.mContext = mContext;
        this.propertyList = propertyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_property, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Property property = propertyList.get(position);

       /* if (TextUtils.equals(property.getPolicyType(), "Life")) {




        } else if (TextUtils.equals(policy.getPolicyType(), "General")) {

        }*/

        holder.tvName.setText(property.getName());
        holder.tvAddress.setText(property.getAddress());
        holder.tvAddress1.setText(property.getAddress1());
        holder.tvCity.setText(property.getCity());
        holder.tvSize.setText(property.getSize());
        holder.tvRent.setText(property.getRent());


    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }
}
