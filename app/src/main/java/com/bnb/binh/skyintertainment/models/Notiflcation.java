package com.bnb.binh.skyintertainment.models;

public class Notiflcation {
    private String id;
    private String hisId;
    private String content;

    public Notiflcation() {
    }

    public Notiflcation(String id, String hisId, String content) {
        this.id = id;
        this.hisId = hisId;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
