package com.amithelpline.ahl.model;

/**
 * Created by Neeraj on 29-08-2017.
 */

public class Youtube {
    private String Title, Url, VideoId;


    public Youtube() {
    }

    public Youtube(String Title, String Url, String VideoId) {
        this.Title = Title;
        this.Url = Url;
        this.VideoId = VideoId;


    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String VideoId) {
        this.VideoId = VideoId;
    }
}
