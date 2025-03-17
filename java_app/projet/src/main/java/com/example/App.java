package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App extends Application {

    private Connexion connexion;
    private GestionUtilisateurs gestionUtilisateurs;
    private Stage primaryStage;

    private Scene mainScene;
    private Scene tableScene;

    private TableView<Utilisateur> tableView;
    private ObservableList<Utilisateur> userList;
    private FilteredList<Utilisateur> filteredList;

    private boolean isBackgroundActive = false; // Etat de l arriere plan
    private VBox mainRoot; // on garde le layout principal pr changer l arriere plan
    private VBox tableRoot; // on garde le layout de la table pr changer l arriere plan

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // connection a la bdd
        connexion = new Connexion();
        connexion.connect();
        gestionUtilisateurs = new GestionUtilisateurs(connexion);

        
        createTableViewScene();

        
        primaryStage.setTitle("JavaFX - Gestion Eleves");
        primaryStage.setScene(tableScene);
        primaryStage.show();
    }

    // Scene du tableau
    private void showTableView() {
        primaryStage.setScene(tableScene);
    }

    // Menu principal
    private void showMainMenu() {
        // Vbox pr le menu
        mainRoot = new VBox(15);
        mainRoot.setAlignment(Pos.CENTER);
        mainRoot.getStyleClass().add("default-background"); 
    
        // Boutons du menu
        Button button = new Button("Creer un utilisateur");
        Button button2 = new Button("Effacer un utilisateur");
        Button button3 = new Button("MAJ un utilisateur");
        Button showTableButton = new Button("Afficher la table");
        Button exportButton = new Button("Exporter (CSV)");
        Button toggleBackgroundButton = new Button("Arriere plan");
    
        // Caracteristiques des boutons
        double buttonWidth = 200;
        button.setMinWidth(buttonWidth);
        button2.setMinWidth(buttonWidth);
        button3.setMinWidth(buttonWidth);
        showTableButton.setMinWidth(buttonWidth);
        exportButton.setMinWidth(buttonWidth);
        toggleBackgroundButton.setMinWidth(buttonWidth);
    
        // Association de chaque bouton a une action specifique
        button.setOnAction(event -> showCreateUserPopup());
        button2.setOnAction(event -> showDeleteUserPopup());
        button3.setOnAction(event -> showUpdateUserPopup());
        showTableButton.setOnAction(event -> showTableView());
        exportButton.setOnAction(event -> exportToCSV());
        toggleBackgroundButton.setOnAction(event -> toggleBackground(mainRoot));
    
        // Addition des boutons au menu principal
        mainRoot.getChildren().addAll(button, button2, button3, showTableButton, exportButton, toggleBackgroundButton);
    
        // Creation d un nouvelle scene
        mainScene = new Scene(mainRoot, 400, 400);
        // reliage avec le css
        mainScene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm()); 
        primaryStage.setScene(mainScene);
    }
    

    // Activer l arriere plan
    private void toggleBackground(Pane root) {
        if (isBackgroundActive) {
            root.getStyleClass().remove("image-background");
            root.getStyleClass().add("default-background");
        } else {
            root.getStyleClass().remove("default-background");
            root.getStyleClass().add("image-background");
        }
        isBackgroundActive = !isBackgroundActive;
    }
    
    // mise en place du filtrage
    private void createTableViewScene() {
        tableRoot = new VBox(10);
        tableRoot.setAlignment(Pos.CENTER);
        tableRoot.getStyleClass().add("default-background"); // Appliquer style par efaut
    
        tableView = new TableView<>();// Creation de la table
    
        // Mise en page du tableview
        TableColumn<Utilisateur, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        TableColumn<Utilisateur, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    
        TableColumn<Utilisateur, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    
        TableColumn<Utilisateur, String> createdColumn = new TableColumn<>("creer le");
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
    
        TableColumn<Utilisateur, String> updatedColumn = new TableColumn<>("MAJ le");
        updatedColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
    
        tableView.getColumns().addAll(idColumn, nameColumn, emailColumn, createdColumn, updatedColumn);
    
        // Chager les donnees
        userList = FXCollections.observableArrayList();
        try {
            userList.addAll(gestionUtilisateurs.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // filtrage de la table 
        filteredList = new FilteredList<>(userList, p -> true); // le p correspond a chaque utilisateur
        tableView.setItems(filteredList);
    
        // Search Fields
        TextField nameFilterField = new TextField();
        nameFilterField.setPromptText("Filtrer par nom");
    
        TextField emailFilterField = new TextField();
        emailFilterField.setPromptText("Filter par email");
    
        Button filterButton = new Button("Filtrer");
        filterButton.setOnAction(event -> applyFilter(nameFilterField.getText(), emailFilterField.getText()));
    
        Button toggleBackgroundButton = new Button("Changer l arriere plan");
        toggleBackgroundButton.setOnAction(event -> toggleBackground(tableRoot));
    
        // bouton retour
        Button backButton = new Button("Retourner");
        backButton.setOnAction(event -> showMainMenu());
    
        // Centraer les boutons
        HBox filterBox = new HBox(10, nameFilterField, emailFilterField, filterButton);
        filterBox.setAlignment(Pos.CENTER);
    
        HBox buttonBox = new HBox(10, backButton, toggleBackgroundButton);
        buttonBox.setAlignment(Pos.CENTER);
    
        // Mise en page
        tableRoot.getChildren().addAll(filterBox, tableView, buttonBox);
    
        tableScene = new Scene(tableRoot, 700, 500);
        // reliage avec le css
        tableScene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
    }
    

    // Methodes principales
    private void showCreateUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
    
        Label nameLabel = new Label("Nom:");
        TextField nameField = new TextField();
    
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
    
        Button submitButton = new Button("Creer Utilisateur");
        submitButton.setOnAction(event -> {
            gestionUtilisateurs.insererUtilisateur(nameField.getText(), emailField.getText());
            refreshTableView(); // Rafraichir la table
            popupStage.close();
        });
    
        popupRoot.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, submitButton);
        popupStage.setScene(new Scene(popupRoot, 300, 200));
        popupStage.show();
    }
    
    private void showDeleteUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
    
        Label emailLabel = new Label("Inserer email a effacer:");
        TextField emailField = new TextField();
    
        Button submitButton = new Button("Effacer utilisateur");
        submitButton.setOnAction(event -> {
            gestionUtilisateurs.supprimerUtilisateurByEmail(emailField.getText());
            refreshTableView(); // rafraichir la table
            popupStage.close();
        });
    
        popupRoot.getChildren().addAll(emailLabel, emailField, submitButton);
        popupStage.setScene(new Scene(popupRoot, 300, 200));
        popupStage.show();
    }
    
    private void showUpdateUserPopup() {
        Stage popupStage = new Stage();
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
    
        Label searchLabel = new Label("Ancien Email:");
        TextField searchField = new TextField();
    
        Label modifyLabel = new Label("Nouvel Email:");
        TextField modifyField = new TextField();
    
        Button submitButton = new Button("MAJ utilisateur");
        submitButton.setOnAction(event -> {
            gestionUtilisateurs.modifierUtilisateurByEmail(searchField.getText(), modifyField.getText());
            refreshTableView(); // rafraichir la table
            popupStage.close();
        });
    
        popupRoot.getChildren().addAll(searchLabel, searchField, modifyLabel, modifyField, submitButton);
        popupStage.setScene(new Scene(popupRoot, 300, 200));
        popupStage.show();
    }
    
    // rfaraichir la table
    private void refreshTableView() {
        userList.clear();
        try {
            userList.addAll(gestionUtilisateurs.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // filtarge de la table
    private void applyFilter(String name, String email) {
        filteredList.setPredicate(user -> {
            boolean matchesName = name.isEmpty() || user.getNom().toLowerCase().contains(name.toLowerCase());
            boolean matchesEmail = email.isEmpty() || user.getEmail().toLowerCase().contains(email.toLowerCase());
            return matchesName && matchesEmail;
        });
    }

    // Exporter en csv
    private void exportToCSV() {
        try {
            List<Utilisateur> users = gestionUtilisateurs.getAllUsers();
            FileWriter fileWriter = new FileWriter("utilisateurs.csv");

            fileWriter.append("ID,Nom,Email,Ceer le,MAJ le\n");
            for (Utilisateur user : users) {
                fileWriter.append(user.getId() + "," + user.getNom() + "," + user.getEmail() + ","
                        + user.getCreatedAt() + "," + user.getUpdatedAt() + "\n");
            }

            fileWriter.flush();
            fileWriter.close();
            System.out.println("Donnees exportees vers utilisateurs.csv");

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
