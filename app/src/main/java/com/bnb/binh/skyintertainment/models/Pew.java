package com.bnb.binh.skyintertainment.models;

public class Pew {
    private String id;
    private String title;
    private String image;
    private String time;

    public Pew() {
    }

    public Pew(String id, String title, String image, String time) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
