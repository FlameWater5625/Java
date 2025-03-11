package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurs {
    private Connexion link;

    public GestionUtilisateurs(Connexion connexion) {
        this.link = connexion;
    }

    // Insert User
    public void insererUtilisateur(String nom, String email) {
        String sql = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = link.connexion.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("Utilisateur insere avec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete User by Email
    public void supprimerUtilisateurByEmail(String email) {
        String sql = "DELETE FROM utilisateurs WHERE email = ?";
        try (PreparedStatement pstmt = link.connexion.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.executeUpdate();
            System.out.println("Utilisateur supprime avec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update User Email by Old Email
    public void modifierUtilisateurByEmail(String oldEmail, String newEmail) {
        String sql = "UPDATE utilisateurs SET email = ? WHERE email = ?";
        try (PreparedStatement pstmt = link.connexion.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, oldEmail);
            pstmt.executeUpdate();
            System.out.println("Utilisateur modifie avec succes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all users for CSV export
    public List<Utilisateur> getAllUsers() throws SQLException {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        try (PreparedStatement pstmt = link.connexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                users.add(new Utilisateur(id, nom, email, createdAt, updatedAt));
            }
        }
        return users;
    }
}
