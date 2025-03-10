package com.example;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/App.fxml"));
        VBox root = loader.load();

        

        // Find the label and button from the loaded FXML
        Label label = (Label) root.lookup("#messageLabel");
        Button button = (Button) root.lookup("#clickButton");

        // Add an event listener to the button
        if (button != null && label != null) {
            button.setOnAction(event -> label.setText("Text changed!"));
        } else {
            System.out.println("Label or button not found in FXML.");
        }

        // Create and configure the scene
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

        primaryStage.setTitle("Ma Première Application JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
