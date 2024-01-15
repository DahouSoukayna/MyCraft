package com.example.hraftiproject.firestoremodels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Professionnel {

    private String nom;
    private String email;
    private String password;
    private String metier;
    private int numtel;
    private String ville;
    private String description;
    private byte[] image;

    // Constructeur par défaut requis pour Firestore
    public Professionnel() {
    }

    public Professionnel(String nom, String email, String password, String metier, int numtel, String ville, String description, byte[] image) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.metier = metier;
        this.numtel = numtel;
        this.ville = ville;
        this.description = description;
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMetier() {
        return metier;
    }

    public int getNumtel() {
        return numtel;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }



}
