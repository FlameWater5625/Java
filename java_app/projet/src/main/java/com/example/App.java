package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App extends Application {

    private Connexion connexion;
    private GestionUtilisateurs gestionUtilisateurs;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize database connection
        connexion = new Connexion();
        connexion.connect();  // Connect to the database
        gestionUtilisateurs = new GestionUtilisateurs(connexion);

        // Load the FXML file (for the main UI)
        VBox root = new VBox();

        // Create buttons for the actions
        Button button = new Button("Create User");
        Button button2 = new Button("Delete User");
        Button button3 = new Button("Update User");
        Button exportButton = new Button("Export");

        // Define button actions
        button.setOnAction(event -> showCreateUserPopup());
        button2.setOnAction(event -> showDeleteUserPopup());
        button3.setOnAction(event -> showUpdateUserPopup());
        exportButton.setOnAction(event -> exportToCSV());

        root.getChildren().addAll(button, button2, button3, exportButton);

        // Create and configure the main scene
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("JavaFX - Gestion Eleves");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create User Popup
    private void showCreateUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button submitButton = new Button("Create User");
        submitButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            createUserInDatabase(name, email);
            popupStage.close();
        });

        popupRoot.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, submitButton);

        Scene popupScene = new Scene(popupRoot, 300, 200);
        popupStage.setTitle("Create User");
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Create User in Database
    private void createUserInDatabase(String name, String email) {
        try {
            gestionUtilisateurs.insererUtilisateur(name, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete User Popup
    private void showDeleteUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);

        Label emailLabel = new Label("Enter Email to Delete:");
        TextField emailField = new TextField();

        Button submitButton = new Button("Delete User");
        submitButton.setOnAction(event -> {
            String email = emailField.getText();
            deleteUserByEmail(email);
            popupStage.close();
        });

        popupRoot.getChildren().addAll(emailLabel, emailField, submitButton);

        Scene popupScene = new Scene(popupRoot, 300, 200);
        popupStage.setTitle("Delete User");
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Delete User from Database
    private void deleteUserByEmail(String email) {
        try {
            gestionUtilisateurs.supprimerUtilisateurByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update User Popup
    private void showUpdateUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);

        Label searchLabel = new Label("Chercher (Ancien Email):");
        TextField searchField = new TextField();

        Label modifyLabel = new Label("Modifier (Nouveau Email):");
        TextField modifyField = new TextField();

        Button submitButton = new Button("Maj user");
        submitButton.setOnAction(event -> {
            String oldEmail = searchField.getText();
            String newEmail = modifyField.getText();
            updateUserEmail(oldEmail, newEmail);
            popupStage.close();
        });

        popupRoot.getChildren().addAll(searchLabel, searchField, modifyLabel, modifyField, submitButton);

        Scene popupScene = new Scene(popupRoot, 300, 200);
        popupStage.setTitle("Maj user");
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Update User Email in Database
    private void updateUserEmail(String oldEmail, String newEmail) {
        try {
            gestionUtilisateurs.modifierUtilisateurByEmail(oldEmail, newEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Export to CSV
    private void exportToCSV() {
        try {
            List<Utilisateur> users = gestionUtilisateurs.getAllUsers();
            FileWriter fileWriter = new FileWriter("utilisateurs.csv");

            // Write CSV headers
            fileWriter.append("ID,Nom,Email,Created At,Updated At\n");

            // Write user data
            for (Utilisateur user : users) {
                fileWriter.append(user.getId() + "," + user.getNom() + "," + user.getEmail() + ","
                        + user.getCreatedAt() + "," + user.getUpdatedAt() + "\n");
            }

            fileWriter.flush();
            fileWriter.close();
            System.out.println("Data exportee : utilisateurs.csv");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        if (connexion != null) {
            connexion.close();
        }
    }
}
