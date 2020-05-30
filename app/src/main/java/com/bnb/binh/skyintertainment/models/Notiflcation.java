package com.bnb.binh.skyintertainment.models;

public class Notiflcation {
    private String id;
    private String hisId;
    private String content;
    private boolean reply;
    private String key;

    public Notiflcation() {
    }

    public Notiflcation(String id, String hisId, String content, boolean reply, String key) {
        this.id = id;
        this.hisId = hisId;
        this.content = content;
        this.reply = reply;
        this.key = key;
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

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
