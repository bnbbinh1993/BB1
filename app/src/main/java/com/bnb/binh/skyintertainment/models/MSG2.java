package com.bnb.binh.skyintertainment.models;

public class MSG2 {
    private String nameSender;
    private String receiver;
    private String message;
    private String timestamp;
    private String sender;
    private String avtSender;
    private boolean isSeen;


    public MSG2() {
    }

    public MSG2(String nameSender, String receiver, String message, String timestamp, String sender, String avtSender, boolean isSeen) {
        this.nameSender = nameSender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
        this.sender = sender;
        this.avtSender = avtSender;
        this.isSeen = isSeen;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAvtSender() {
        return avtSender;
    }

    public void setAvtSender(String avtSender) {
        this.avtSender = avtSender;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
