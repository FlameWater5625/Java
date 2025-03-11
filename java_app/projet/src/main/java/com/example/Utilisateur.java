package com.example;

import java.sql.Timestamp;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Utilisateur(int id, String nom, String email, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Nom: " + nom + ", Email: " + email;
    }
}
