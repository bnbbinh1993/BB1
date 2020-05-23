package com.bnb.binh.skyintertainment.models;

import java.math.BigDecimal;

public class Hat {
    private String title;
    private int image;
    private BigDecimal prince;
    private int serial_number;

    public Hat(String title, int image, BigDecimal prince, int serial_number) {
        this.title = title;
        this.image = image;
        this.prince = prince;
        this.serial_number = serial_number;
    }

    public Hat() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public BigDecimal getPrince() {
        return prince;
    }

    public void setPrince(BigDecimal prince) {
        this.prince = prince;
    }

    public int getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(int serial_number) {
        this.serial_number = serial_number;
    }
}
