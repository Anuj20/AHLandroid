package com.amithelpline.ahl.model;



import com.amithelpline.ahl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALISHA on 12/9/2018.
 */
public class GridMenuModel {
    public static final String DAILY_BONUS="Policy";
    public static final String EARN_POINTS="Property";
    public static final String REDEEM_POINTS="IPO";
    public static final String FREE_SMS="Youtube";
    public static final String REFER_FRIENDS="Apply";
    public static final String MY_PROFILE="Contact Us";

    private String title;
    private int imgDrawableId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgDrawableId() {
        return imgDrawableId;
    }

    public void setImgDrawableId(int imgDrawableId) {
        this.imgDrawableId = imgDrawableId;
    }

    public List<GridMenuModel> getGridMenuModels(){
        List<GridMenuModel> gridMenuModels=new ArrayList<>();

        String[] menuTitles = {DAILY_BONUS,
                EARN_POINTS,
                REDEEM_POINTS,
                FREE_SMS,
                REFER_FRIENDS,
                MY_PROFILE};


        /* icon change

       /* int[] drawableIds = {
                R.drawable.ic_install_apps_24dp,
                R.drawable.ic_earn_points_24dp,
                R.drawable.ic_redeem_points_24dp,
                R.drawable.ic_free_sms_24dp,
                R.drawable.ic_refer_friends_24dp,
                R.drawable.ic_profile_24dp
        };*/
        int[] drawableIds = {
                R.drawable.icon_installapps,
                R.drawable.icon_earnpoints,
                R.drawable.icon_redeem,
                R.drawable.icon_rate,
                R.drawable.icon_refer,
                R.drawable.icon_profile
        };

        for (int i = 0; i < menuTitles.length; i++) {
            GridMenuModel gridMenuModel = new GridMenuModel();
            gridMenuModel.setImgDrawableId(drawableIds[i]);
            gridMenuModel.setTitle(menuTitles[i]);
            gridMenuModels.add(gridMenuModel);
        }

        return gridMenuModels;
    }
}
