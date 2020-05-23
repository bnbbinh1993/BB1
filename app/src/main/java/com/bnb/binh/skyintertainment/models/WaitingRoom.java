package com.bnb.binh.skyintertainment.models;

public class WaitingRoom {
    private String hisId;
    private String myId;
    private String timeKey;
    private boolean check;

    public WaitingRoom() {
    }

    public WaitingRoom(String hisId, String myId, String timeKey, boolean check) {
        this.hisId = hisId;
        this.myId = myId;
        this.timeKey = timeKey;
        this.check = check;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getMyid() {
        return myId;
    }

    public void setMyid(String myId) {
        this.myId = myId;
    }

    public String getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
