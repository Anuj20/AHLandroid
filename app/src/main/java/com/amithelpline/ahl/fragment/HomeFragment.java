package com.amithelpline.ahl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.adapter.GridMenuAdapter;
import com.amithelpline.ahl.model.GridMenuModel;

/**
 * Created by Neeraj on 02-06-2017.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {


    GridView gridViewMenu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridViewMenu=(GridView)view.findViewById(R.id.grid_view_menu);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initGridView();

    }



    private void initGridView() {
        GridMenuAdapter mGridMenuAdapter = new GridMenuAdapter(new GridMenuModel().getGridMenuModels());
        gridViewMenu.setAdapter(mGridMenuAdapter);
        gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnGridMenuClickListener.onGridMenuClick(position);
    }

    public interface OnGridMenuClickListener {
        void onGridMenuClick(int position);
    }

    private OnGridMenuClickListener mOnGridMenuClickListener;

    public void setOnGridMenuClickListener(OnGridMenuClickListener mOnGridMenuClickListener) {
        this.mOnGridMenuClickListener = mOnGridMenuClickListener;
    }




}
