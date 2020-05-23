package com.bnb.binh.skyintertainment.models;

import java.util.List;

public class ModelStory {
    private List<String> name;
    private List<String> avt;
    private List<String> images;
    private List<String> time;
    private List<String> title;


    public ModelStory() {
    }

    public ModelStory(List<String> name, List<String> avt, List<String> images, List<String> time, List<String> title) {
        this.name = name;
        this.avt = avt;
        this.images = images;
        this.time = time;
        this.title = title;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getAvt() {
        return avt;
    }

    public void setAvt(List<String> avt) {
        this.avt = avt;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
}

