package com.example.skyrunner.screens;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class TryAgainScreen {
    private final Game game;
    private Scene scene;
    private Stage stage;
    public UIManager uiManager;

    public TryAgainScreen(Game game, UIManager uiManager) {
        this.game = game;
        this.uiManager = uiManager; // Initialize mainMenu
        initialize();
    }

    private void initialize() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Create labels
        Text gameOverLabel = new Text("Game OVER");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        updateGameOverLabel(gameOverLabel);

        Label scoreLabel = new Label("Your Score: " + (int) Game.getScore());
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label coinsLabel = new Label("Your Coins: " + Game.getCoins());
        coinsLabel.setTextFill(Color.WHITE);
        coinsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Create buttons
        Button tryAgainButton = new Button("Try Again");
        tryAgainButton.setPrefWidth(150);
        tryAgainButton.setPrefHeight(40);
        setButtonGradient(tryAgainButton, "blue");

        tryAgainButton.setOnAction(e -> {
            // Start the game again
            game.initializeGame();
            game.start();
            stage = (Stage) tryAgainButton.getScene().getWindow();
            stage.setScene(game.getScene());
        });

        Button returnButton = new Button("Return");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(40);
        setButtonGradient(returnButton, "purple");

        returnButton.setOnAction(e -> {
            // Show the main menu
            stage = (Stage) returnButton.getScene().getWindow(); // Get the current stage
            uiManager.showMainMenu();
        
        });

        layout.getChildren().addAll(gameOverLabel, scoreLabel, coinsLabel, tryAgainButton, returnButton);

        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/Background.jpg")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(background));

        scene = new Scene(layout, 800, 600);
    }

    // Set the gradient to the button
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

    // Convert the LinearGradient to a string
    private String toRgbString(Paint paint) {
        if (paint instanceof LinearGradient) {
            return "linear-gradient(from 0% 0% to 100% 100%, darkblue, skyblue)";
        }
        return paint.toString();
    }

    // Update the game over label
    private void updateGameOverLabel(Text gameOverLabel) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.RED), new Stop(1, Color.PURPLE));
        gameOverLabel.setFill(gradient);
    }

    // Getter
    public Scene getScene() {
        return scene;
    }
}
