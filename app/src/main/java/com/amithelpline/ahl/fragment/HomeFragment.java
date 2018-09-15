package com.amithelpline.ahl.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.MainActivity;
import com.amithelpline.ahl.adapter.GridMenuAdapter;
import com.amithelpline.ahl.model.GridMenuModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Alisha on 02-06-2017.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    public ViewFlipper viewFlipper;
    ArrayList<String> imgFlipper;
    GridView gridViewMenu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridViewMenu=(GridView)view.findViewById(R.id.grid_view_menu);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        imgFlipper = new ArrayList<String>();
        new JSONAsyncTask().execute();
        //new JSONAsyncTask().execute("http://www.anconacontrols.com/manpreet/ticketbooking/serv/getoffer.php");
        // Create runnable for posting

        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                AnimateandSlideShow();
            }
        };
        final Handler mHandler= new Handler();
        int delay = 500;
        int period = 4000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                mHandler.post(mUpdateResults);
            }
        }, delay, period);
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


    class JSONAsyncTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integer) {
            String data = "";
            int tmp;

            try {
                URL url = new URL("http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/getbanner.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while ((tmp = is.read()) != -1) {
                    data += (char) tmp;
                }
                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("ListData");
                System.out.println(jarray);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject object = jarray.getJSONObject(i);
                    Log.d("image: ", object.getString("ImagePath"));
                    imgFlipper.add(object.getString("ImagePath"));

                }
                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {

            if(s == null){
                Toast.makeText(getContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
            else{
                setFlipperImage(imgFlipper);
            }
        }
    }

    private void setFlipperImage(final ArrayList<String> imgFlipper) {

        for (int i = 0; i < imgFlipper.size(); i++) {
            Log.i("Set Filpper Called", imgFlipper.get(i).toString() + "");
            final ImageView image = new ImageView(getContext());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext())
                    .load(imgFlipper.get(i).toString())
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(image);
            viewFlipper.addView(image);
        }
    }


    // method to show slide show
    private void AnimateandSlideShow() {
        viewFlipper.showNext();
    }

}
