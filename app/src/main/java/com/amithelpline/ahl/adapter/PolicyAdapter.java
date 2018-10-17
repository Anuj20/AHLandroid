package com.amithelpline.ahl.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;


import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.MainActivity;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.fragment.EditGeneralPolicyFragment;
import com.amithelpline.ahl.fragment.EditLICPolicyFragment;
import com.amithelpline.ahl.model.Policy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alisha on 26-07-2018.
 */

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<Policy> policyList;
    private List<Policy> mFilteredList;
    Policy policy;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPreimumDate, tvPreimumAmt, tvPolicyNo, tvPolicyMode,tvPremiumStartDate;
        ImageView ivEdit, ivPdf,ivDelete;
        Button ivPaid;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPolicyNo = (TextView) view.findViewById(R.id.tvPolicyNo);
            tvPremiumStartDate= (TextView) view.findViewById(R.id.tvStartDate);
            tvPreimumDate = (TextView) view.findViewById(R.id.tvDate);
            tvPreimumAmt = (TextView) view.findViewById(R.id.tvAmount);
            ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
            ivPdf = (ImageView) view.findViewById(R.id.ivDownload);
            ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
            ivPaid = (Button) view.findViewById(R.id.ivPaid);
            ivPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    policy = policyList.get(getAdapterPosition());
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(mContext);
                    a_builder.setMessage("Your due date will extend according to your plan!!!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String Url = "http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/assets/page/update.php?table="+"Policies_lic&id=" + policy.getPolicyId();
                                    //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
                                    RequestQueue queue = Volley.newRequestQueue(mContext);
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // Display the first 500 characters of the response string.
                                                    //Toast.makeText(mContext,"Your response has been stored, we will get back to you shortly",Toast.LENGTH_LONG);
                                                    notifyDataSetChanged();
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(mContext,"Failed",Toast.LENGTH_LONG);
                                        }
                                    });
                                    queue.add(stringRequest);
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }) ;
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Alert !!!");
                    alert.show();
                }
            });
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
                    String Url = "http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/policiespdf/" + policy.getPolicyNo() + ".pdf";
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder b_builder = new AlertDialog.Builder(mContext);
                    b_builder.setMessage("Are you sure you want to delete your record of this LIC policy!!!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            policy = policyList.get(getAdapterPosition());
                                            String Url1 = "http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/assets/page/disable.php?table=" + "Policies_lic&id=" + policy.getPolicyId();
                                            System.out.println("abc" + Url1);
                                            //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url1)));

                                            try {
                                                URL url = new URL(Url1);
                                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                                readStream(in);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            } finally {
                                            }
                                        }
                                    }).start();
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }) ;
                    AlertDialog alert = b_builder.create();
                    alert.setTitle("Alert !!!");
                    alert.show();
                   //PolicyAdapter mAdapter = new PolicyAdapter(mContext, policyList);
                    //mAdapter.publishResults(mFilteredList);
                }
            });


        }
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
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
        holder.tvPremiumStartDate.setText(policy.getPremiumStartDate());
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
