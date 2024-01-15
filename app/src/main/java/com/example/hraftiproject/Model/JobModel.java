package com.example.hraftiproject.Model;

import android.graphics.Bitmap;

public class JobModel {

     private String name;
    private String number;
    private String city;
    private String email;
    private Bitmap image;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    private String job;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobModel(String job, String name, String number, String city, String email, Bitmap image) {
        this.job = job;
        this.name = name;
        this.number = number;
        this.city = city;
        this.email = email;
        this.image = image;
    }
}
