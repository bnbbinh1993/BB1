package com.bnb.binh.skyintertainment.models;

public class ModelUser  {
   private String address;
   private String age;
   private String avt;
   private String email;
   private String id;
   private String name;
   private String sdt;

    public ModelUser() {
    }

    public ModelUser(String address, String age, String avt, String email, String id, String name, String sdt) {
        this.address = address;
        this.age = age;
        this.avt = avt;
        this.email = email;
        this.id = id;
        this.name = name;
        this.sdt = sdt;
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

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
