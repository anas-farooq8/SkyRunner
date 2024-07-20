package com.example.skyrunner.screens;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class PauseScreen
{
    private final Game game;
    private Scene scene;
    private Stage stage;
    private final UIManager uiManager;

    // Constructor
    public PauseScreen(Game game, UIManager uiManager) {
        this.game = game;
        this.uiManager = uiManager;
        initialize();
    }

    private void initialize() {
        
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER); // Center the elements

        // Create labels
        Text pauseLabel = new Text("PAUSE");
        pauseLabel.setFont(Font.font("Arial", 32));
        updatePauseLabel(pauseLabel); // Set gradient to label

        // Create buttons
        Button tryAgainButton = new Button("Continue");
        tryAgainButton.setPrefWidth(150);
        tryAgainButton.setPrefHeight(40);
        setButtonGradient(tryAgainButton, "blue");

        tryAgainButton.setOnAction(e -> {
            stage = (Stage) tryAgainButton.getScene().getWindow(); // Get the current stage
            stage.setScene(game.getScene()); // Return to the game scene
        });

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
        setButtonGradient(returnButton, "purple");

        returnButton.setOnAction(e -> {
            stage = (Stage) returnButton.getScene().getWindow(); // Get the current stage
            game.pauseButton.setText("Pause");
            uiManager.showMainMenu();
        });
     

        // Add labels and buttons to layout
        layout.getChildren().addAll(pauseLabel, tryAgainButton, returnButton);

        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/Background.jpg")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(background));

        // Create scene
        scene = new Scene(layout, 800, 600);
    }

    // Set gradient to button
    private void setButtonGradient(Button button, String colorCombination) {
        LinearGradient gradient = switch (colorCombination) {
            case "blue" -> new LinearGradient(0, 0, 1, 1, true, null,
                    new Stop(0, Color.BLUE), new Stop(1, Color.SKYBLUE));
            case "purple" -> new LinearGradient(1, 1, 0, 0, true, null,
                    new Stop(0, Color.PURPLE), new Stop(1, Color.VIOLET));
            case "red" -> new LinearGradient(1, 1, 0, 0, true, null,
                    new Stop(0, Color.RED), new Stop(1, Color.PINK));
            default -> new LinearGradient(1, 0, 1, 1, true, null,
                    new Stop(0, Color.DARKBLUE), new Stop(1, Color.SKYBLUE), new Stop(1, Color.DARKRED));
        };

        button.setStyle("-fx-background-color: " + toRgbString(gradient) + "; -fx-text-fill: white; -fx-font-size: 16;");
    }

    // Convert LinearGradient to RGB string
    private String toRgbString(Paint paint) {
        if (paint instanceof LinearGradient) {
            return "linear-gradient(from 0% 0% to 100% 100%, darkblue, skyblue)";
        }
        return paint.toString();
    }

    // Set gradient to label
    private void updatePauseLabel(Text pauseLabel) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.RED), new Stop(1, Color.PURPLE));
        pauseLabel.setFill(gradient);
    }

    public Scene getScene() {
        return scene;
    }
}
