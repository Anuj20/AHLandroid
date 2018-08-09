package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;


import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.MainActivity;
import com.amithelpline.ahl.fragment.EditGeneralPolicyFragment;
import com.amithelpline.ahl.fragment.EditLICPolicyFragment;
import com.amithelpline.ahl.model.Policy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Policy> policyList;
    private List<Policy> mFilteredList;
    Policy policy;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPreimumDate, tvPreimumAmt, tvPolicyNo, tvPolicyMode;
        ImageView ivEdit, ivPdf;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPolicyNo = (TextView) view.findViewById(R.id.tvPolicyNo);
            tvPreimumDate = (TextView) view.findViewById(R.id.tvDate);
            tvPreimumAmt = (TextView) view.findViewById(R.id.tvAmount);
            ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
            ivPdf = (ImageView) view.findViewById(R.id.ivDownload);
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policy = policyList.get(getAdapterPosition());
                    EditLICPolicyFragment.Name=policy.getName();
                    EditLICPolicyFragment.PolicyNo = policy.getPolicyNo();
                    EditLICPolicyFragment.PremiumAmt = policy.getPreimumAmt();
                    EditLICPolicyFragment.PremiumDate = policy.getPreimumDate();
                    EditLICPolicyFragment.Policy_Id = policy.getPolicyId();
                    ((MainActivity) mContext).setFragment(new EditLICPolicyFragment());

                }
            });
            ivPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    policy = policyList.get(getAdapterPosition());
                    String Url = "http://stylenada.in/amit_helpline/admin/policiespdf/" + policy.getPolicyNo() + ".pdf";
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
                }
            });

        }
    }


    public PolicyAdapter(Context mContext, List<Policy> policyList) {
        this.mContext = mContext;
        this.policyList = policyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_policy, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        policy = policyList.get(position);


        holder.tvName.setText(policy.getName());
        holder.tvPolicyNo.setText(policy.getPolicyNo());
        holder.tvPreimumAmt.setText(policy.getPreimumAmt());
        holder.tvPreimumDate.setText(policy.getPreimumDate());


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

                    ArrayList<Policy> filteredList = new ArrayList<>();
                    filteredList.clear();
                    for (Policy androidVersion : policyList) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

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
                policyList.addAll((ArrayList<Policy>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
