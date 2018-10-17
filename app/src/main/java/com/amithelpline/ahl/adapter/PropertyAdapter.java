package com.amithelpline.ahl.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.model.Policy;
import com.amithelpline.ahl.model.Property;
import com.amithelpline.ahl.utils.Const;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.List;
import java.util.ResourceBundle;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alisha on 14-10-2018.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Property> propertyList;
    Property property;
    SharedPreferences sharedpreferences;
    String UserId, Name,Mobile,Email;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress, tvAddress1, tvCity, tvSize, tvRent;
        ImageView ivInterested;

        public MyViewHolder(View view) {
            super(view);
            sharedpreferences = mContext.getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
            UserId = sharedpreferences.getString(Const.UserId, "0");
            Mobile = sharedpreferences.getString(Const.Mobile, "0");
            Email = sharedpreferences.getString(Const.Email, "0");
            Name = sharedpreferences.getString(Const.Name,"0");
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvAddress1 = (TextView) view.findViewById(R.id.tvAddress1);
            tvCity = (TextView) view.findViewById(R.id.tvCity);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            tvRent = (TextView) view.findViewById(R.id.tvRent);
            ivInterested = (ImageView) view.findViewById(R.id.ivInterested);
            ivInterested.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    property = propertyList.get(getAdapterPosition());
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(mContext);
                    a_builder.setMessage("You are about to submit your response that you are interested in this rental property!!!")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String RegInterestParmeter = "api_add_interest_in_property.php?name="+Name+
                                            "&mobile="+Mobile+
                                            "&email="+Email.trim()+
                                            "&property_id="+property.getPropertyID()+
                                            "&user_id="+UserId;
                                    System.out.println("abssdfd"+RegInterestParmeter);
                                    String Url = ApiConfig.BASE_URL +RegInterestParmeter;
                                    RequestQueue queue = Volley.newRequestQueue(mContext);
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // Display the first 500 characters of the response string.
                                                   Toast.makeText(mContext,"Your response has been stored, we will get back to you shortly",Toast.LENGTH_LONG);
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(mContext,"Failed",Toast.LENGTH_LONG);
                                        }
                                    });
                                    queue.add(stringRequest);
                                  //  mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Url)));
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
