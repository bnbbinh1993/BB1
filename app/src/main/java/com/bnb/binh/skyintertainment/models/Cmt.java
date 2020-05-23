package com.bnb.binh.skyintertainment.models;

public class Cmt {
    private String hisId;
    private String Cmt;
    private String timeCmt;

    public Cmt() {
    }

    public Cmt(String hisId, String cmt, String timeCmt) {
        this.hisId = hisId;
        Cmt = cmt;
        this.timeCmt = timeCmt;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getCmt() {
        return Cmt;
    }

    public void setCmt(String cmt) {
        Cmt = cmt;
    }

    public String getTimeCmt() {
        return timeCmt;
    }

    public void setTimeCmt(String timeCmt) {
        this.timeCmt = timeCmt;
    }
}
