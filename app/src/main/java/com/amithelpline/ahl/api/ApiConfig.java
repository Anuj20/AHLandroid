package com.amithelpline.ahl.api;

/**
 * Created by Neeraj on 7/27/2016.
 */
public class ApiConfig {


    public static final String BASE_URL = "http://ec2-13-126-178-19.ap-south-1.compute.amazonaws.com/app/";

    public static final String GET_YOUTUBE_PLAYLIST = "https://www.googleapis.com/youtube/v3/channels?id=UCvdqhCRieXEGpu5TVQ69-3A&key=AIzaSyCjDrz6lE-E_UHm2dKHYBtuRmrB-djT__E&part=contentDetails";

    public static final String GET_YOUTUBE_VIDEOS = "https://www.googleapis.com/youtube/v3/playlistItems?playlistId=UUvdqhCRieXEGpu5TVQ69-3A&key=AIzaSyCjDrz6lE-E_UHm2dKHYBtuRmrB-djT__E&part=snippet&maxResults=50";
}
