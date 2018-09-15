package com.amithelpline.ahl.model;

/**
 * Created by Alisha on 9/9/2018.
 */
public class MasterCls {

    private int id;
    private String category_name, image_link;

    public MasterCls(int id, String category_name, String image_link) {
        this.id = id;
        this.category_name = category_name;
        this.image_link = image_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }


}
