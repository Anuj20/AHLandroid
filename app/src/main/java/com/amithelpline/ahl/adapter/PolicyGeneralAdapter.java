package com.amithelpline.ahl.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.MainActivity;
import com.amithelpline.ahl.fragment.EditGeneralPolicyFragment;
import com.amithelpline.ahl.model.Policy;
import com.amithelpline.ahl.model.PolicyGeneral;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class PolicyGeneralAdapter extends RecyclerView.Adapter<PolicyGeneralAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<PolicyGeneral> policyList;
    private List<PolicyGeneral> mFilteredList;
    PolicyGeneral policy;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvVehicleNo, tvPreimumDate, tvPreimumAmt, tvPolicyNo, tvPolicyType;
        ImageView ivEdit, ivPdf;

        public MyViewHolder(View view) {
            super(view);

            tvPolicyType = (TextView) view.findViewById(R.id.tvInsuranceType);
            tvVehicleNo = (TextView) view.findViewById(R.id.tvVehicleNo);
            tvPolicyNo = (TextView) view.findViewById(R.id.tvPolicyNo);
            tvPreimumDate = (TextView) view.findViewById(R.id.tvDate);
            tvPreimumAmt = (TextView) view.findViewById(R.id.tvAmount);
            ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
            ivPdf = (ImageView) view.findViewById(R.id.ivDownload);
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policy = policyList.get(getAdapterPosition());
                    EditGeneralPolicyFragment.PolicyNo = policy.getPolicyNo();
                    EditGeneralPolicyFragment.PremiumAmt = policy.getPreimumAmt();
                    EditGeneralPolicyFragment.PremiumDate = policy.getPreimumDate();
                    EditGeneralPolicyFragment.VehicleNumber = policy.getVehicleNo();
                    EditGeneralPolicyFragment.PolicyId = policy.getId();
                    ((MainActivity) mContext).setFragment(new EditGeneralPolicyFragment());

                }
            });
            ivPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policy = policyList.get(getAdapterPosition());
                    String Url = "http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/policiespdf/" + policy.getPolicyNo() + ".pdf";
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
                }
            });
        }
    }


    public PolicyGeneralAdapter(Context mContext, List<PolicyGeneral> policyList) {
        this.mContext = mContext;
        this.policyList = policyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_general_policy, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        policy = policyList.get(position);


        holder.tvVehicleNo.setText(policy.getVehicleNo());


        holder.tvPolicyNo.setText(policy.getPolicyNo());
        holder.tvPreimumAmt.setText(policy.getPreimumAmt());
        holder.tvPreimumDate.setText(policy.getPreimumDate());
        holder.tvPolicyType.setText(policy.getPolicyType());
    }

    @Override
    public int getItemCount() {
        return policyList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = policyList;
                } else {

                    ArrayList<PolicyGeneral> filteredList = new ArrayList<>();
                    filteredList.clear();
                    for (PolicyGeneral androidVersion : policyList) {

                        if (androidVersion.getVehicleNo().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                policyList.clear();
                policyList.addAll((ArrayList<PolicyGeneral>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
