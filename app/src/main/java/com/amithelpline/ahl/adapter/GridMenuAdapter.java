package com.amithelpline.ahl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.model.GridMenuModel;

import java.util.List;


/**
 * Created by Alisha
 */
public class GridMenuAdapter extends BaseAdapter {

    private List<GridMenuModel> mGridMenuModels;

    public GridMenuAdapter(List<GridMenuModel> mGridMenuModels) {
        this.mGridMenuModels = mGridMenuModels;
    }

    @Override
    public int getCount() {
        return mGridMenuModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridMenuAdapterHolder holder;
        View view = null;
        if (view != null) {
            holder = (GridMenuAdapterHolder) view.getTag();
        } else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_menu, parent, false);


            holder = new GridMenuAdapterHolder(view);
            view.setTag(holder);

        }

        holder.imgIcon.setImageResource(mGridMenuModels.get(position).getImgDrawableId());
        holder.txtTitle.setText(mGridMenuModels.get(position).getTitle());

        return view;
    }

    protected static class GridMenuAdapterHolder {

        ImageView imgIcon;

        TextView txtTitle;


        public GridMenuAdapterHolder(View view) {
            imgIcon=(ImageView)view.findViewById(R.id.image_view_icon);
            txtTitle=(TextView)view.findViewById(R.id.text_view_title);
        }
    }
}
