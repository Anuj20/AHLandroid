package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.Currency;
import com.amithelpline.ahl.model.Property;

import java.util.List;

/**
 * Created by Neeraj on 26-04-2017.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Currency> currencyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvRate;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvCurrency);
            tvRate = (TextView) view.findViewById(R.id.tvRate);


        }
    }


    public CurrencyAdapter(Context mContext, List<Currency> currencyList) {
        this.mContext = mContext;
        this.currencyList = currencyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Currency currency = currencyList.get(position);

       /* if (TextUtils.equals(property.getPolicyType(), "Life")) {




        } else if (TextUtils.equals(policy.getPolicyType(), "General")) {

        }*/

        holder.tvName.setText(currency.getName());
        holder.tvRate.setText(currency.getRate());



    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
