package com.bnb.binh.skyintertainment.models;

public class Friends {
    private String address;
    private String age;
    private String avtFirend;
    private String gmail;
    private String id;
    private String nameFriend;
    private String sdt;


    public Friends() {
    }

    public Friends(String address, String age, String avtFirend, String gmail, String id, String nameFriend, String sdt) {
        this.address = address;
        this.age = age;
        this.avtFirend = avtFirend;
        this.gmail = gmail;
        this.id = id;
        this.nameFriend = nameFriend;
        this.sdt = sdt;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    public void setNameFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public String getAvtFirend() {
        return avtFirend;
    }

    public void setAvtFirend(String avtFirend) {
        this.avtFirend = avtFirend;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
