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

        // Create HBox for buttons (horizontal row)
        HBox buttonBar = new HBox(10);  // 10px spacing between buttons
        buttonBar.setStyle("-fx-padding: 10;");  // Padding around the buttons
        buttonBar.getChildren().addAll(button, button2, button3, exportButton);

        // TextArea for displaying user data
        TextArea userDataArea = new TextArea();
        userDataArea.setEditable(false);  // Make it read-only
        userDataArea.setPrefHeight(400);  // Set height for better viewing
        userDataArea.setPrefWidth(400);   // Set width

        // Load all users into the TextArea on startup
        loadUserData(userDataArea);

        // Create a VBox for the layout (buttons on top, user data below)
        VBox root = new VBox(20);  // 20px spacing between rows
        root.setStyle("-fx-padding: 20;");  // Padding around the root
        root.getChildren().addAll(buttonBar, userDataArea);

        // Create and configure the scene
        Scene scene = new Scene(root, 800, 800);  // Adjust scene size
        primaryStage.setTitle("JavaFX - Gestion Eleves");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Load and display all users in the TextArea
    private void loadUserData(TextArea userDataArea) {
        try {
            List<Utilisateur> users = gestionUtilisateurs.getAllUsers();
            StringBuilder userText = new StringBuilder();

            // Add headers to the text area
            userText.append("ID\tNom\t\tEmail\t\t\tCreated At\t\tUpdated At\n");
            userText.append("---------------------------------------------------------------\n");

            // Add each user's data to the text area
            for (Utilisateur user : users) {
                userText.append(user.getId()).append("\t")
                        .append(user.getNom()).append("\t\t")
                        .append(user.getEmail()).append("\t")
                        .append(user.getCreatedAt()).append("\t")
                        .append(user.getUpdatedAt()).append("\n");
            }

            // Set the content of the TextArea
            userDataArea.setText(userText.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            loadUserData((TextArea) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(1));  // Refresh the user data
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
            loadUserData((TextArea) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(1));  // Refresh the user data
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update User Popup
    private void showUpdateUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);

        Label searchLabel = new Label("Chercher (Enter Old Email):");
        TextField searchField = new TextField();

        Label modifyLabel = new Label("Modifier (New Email):");
        TextField modifyField = new TextField();

        Button submitButton = new Button("Update User");
        submitButton.setOnAction(event -> {
            String oldEmail = searchField.getText();
            String newEmail = modifyField.getText();
            updateUserEmail(oldEmail, newEmail);
            popupStage.close();
        });

        popupRoot.getChildren().addAll(searchLabel, searchField, modifyLabel, modifyField, submitButton);

        Scene popupScene = new Scene(popupRoot, 300, 200);
        popupStage.setTitle("Update User");
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Update User Email in Database
    private void updateUserEmail(String oldEmail, String newEmail) {
        try {
            gestionUtilisateurs.modifierUtilisateurByEmail(oldEmail, newEmail);
            loadUserData((TextArea) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(1));  // Refresh the user data
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
            System.out.println("Data exported to utilisateurs.csv");

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
