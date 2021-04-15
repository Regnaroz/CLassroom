package com.example.school_app;



public class VideoModel {


    String videoName;
    String videoUri;

    public VideoModel(String videoName, String videoUri) {
        this.videoName = videoName;
        this.videoUri = videoUri;
    }

    public VideoModel() {
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }
}
