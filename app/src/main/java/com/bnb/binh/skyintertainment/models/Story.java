package com.bnb.binh.skyintertainment.models;

public class Story {
    String id;
    String image;

    public Story() {
    }

    public Story(String id, String image) {
        this.id = id;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
