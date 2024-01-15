package com.example.hraftiproject.Model;

import android.graphics.Bitmap;

public class User {
    String Name,Email,Metier,ville,description;
    int phone,id;
    Bitmap image;



    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String name, String email, String metier, String ville, String description, int phone) {
        Name = name;
        Email = email;
        Metier = metier;
        this.ville = ville;
        this.description = description;
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public String getMetier() {
        return Metier;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }

    public int getPhone() {
        return phone;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setMetier(String metier) {
        Metier = metier;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public User(){}


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String name, String email, String metier, String ville, Bitmap image,int number) {
        Name = name;
        Email = email;
        Metier = metier;
        this.phone= number;
        this.ville = ville;
        this.image = image;
    }
}
