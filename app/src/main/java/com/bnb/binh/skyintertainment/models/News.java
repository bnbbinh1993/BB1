package com.bnb.binh.skyintertainment.models;

public class News {
    private String name;
    private String news;
    private String urlImage;
    private String time;
    private String uId;
    private String urlAvt1;
    private String id;
    private String like;



    public News() {
    }

    public News(String name, String news, String urlImage, String time, String uId, String urlAvt1, String id, String like) {
        this.name = name;
        this.news = news;
        this.urlImage = urlImage;
        this.time = time;
        this.uId = uId;
        this.urlAvt1 = urlAvt1;
        this.id = id;
        this.like = like;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUrlAvt1() {
        return urlAvt1;
    }

    public void setUrlAvt1(String urlAvt1) {
        this.urlAvt1 = urlAvt1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
