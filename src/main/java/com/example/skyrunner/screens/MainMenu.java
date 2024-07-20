package com.example.skyrunner.screens;

import com.example.skyrunner.viewfactory.UIManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import java.util.Objects;

public class MainMenu {
    private final Scene scene;

    public MainMenu(UIManager uiManager) {

        // Define button dimensions
        double buttonWidth = 200;
        double buttonHeight = 40;

        Text title = new Text("SKYRUNNER");
        title.setFont(Font.font("Century Gothic", 72));
        title.setStyle("-fx-font-weight: bold;");
        title.setFill(Color.WHITE);

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> uiManager.startGame());
        startButton.setPrefWidth(buttonWidth);
        startButton.setPrefHeight(buttonHeight);
        startButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;");

        Button shopButton = new Button("Shop");
        shopButton.setOnAction(e -> uiManager.showShopMenu());
        shopButton.setPrefWidth(buttonWidth);
        shopButton.setPrefHeight(buttonHeight);
        shopButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;");

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> uiManager.showSettingsMenu());
        settingsButton.setPrefWidth(buttonWidth);
        settingsButton.setPrefHeight(buttonHeight);
        settingsButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;");

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));
        exitButton.setPrefWidth(buttonWidth);
        exitButton.setPrefHeight(buttonHeight);
        exitButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;");

        VBox buttonLayout = new VBox(20); // 20 is the spacing between buttons
        buttonLayout.getChildren().addAll(title, startButton, shopButton, settingsButton, exitButton);
        buttonLayout.setAlignment(Pos.CENTER); // Center align the buttons

        // Create a StackPane to hold the background and buttons
        StackPane root = new StackPane();

        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/Background.jpg")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        root.setBackground(new Background(background));

        root.getChildren().add(buttonLayout);
        StackPane.setAlignment(buttonLayout, Pos.CENTER); // Center align the VBox within the StackPane

        scene = new Scene(root, 800, 600);
        // Set the scene to the primary stage (if applicable)
    }
    public Scene getScene() {
        return scene;
    }

}
