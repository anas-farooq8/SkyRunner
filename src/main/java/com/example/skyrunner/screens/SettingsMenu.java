package com.example.skyrunner.screens;

import com.example.skyrunner.database.Database;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class SettingsMenu {
    private final UIManager uiManager;
    private final Scene scene;
    private MediaPlayer mediaPlayer;

    // Labels for displaying values
    private final Label highestScoreLabel;
    private final Label highestCoinsLabel;
    private final Label totalCoinsLabel;

    Slider musicVolumeSlider;
    Slider sfxVolumeSlider;

    Database db;

    public SettingsMenu(UIManager uiManager) {
        this.uiManager = uiManager;
        db = Database.getInstance();

        // Load scores from Game class
        int highestScore = db.getScoreManager().getHighestScore();
        int highestCoins = db.getScoreManager().getHighestCoins();
        int totalCoins = db.getScoreManager().getTotalCoins();
        double musicVolume =db.getGameSettings().getMusicVolume();
        double sfxVolume = db.getGameSettings().getSfxVolume();

        // Create title label
        Label title = new Label("SETTINGS");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.PURPLE);

        // Music volume slider
        Label musicVolumeLabel = new Label("Music Volume:");
        musicVolumeLabel.setTextFill(Color.RED);
        musicVolumeSlider = new Slider(0, 100, musicVolume * 100);
        musicVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(newVal.doubleValue() / 100); // Set volume (0.0 to 1.0)
            db.getGameSettings().setMusicVolume(newVal.doubleValue() / 100); // Update volume in GameSettingsManager
        });


        // SFX volume slider
        Label sfxVolumeLabel = new Label("SFX Volume:");
        sfxVolumeLabel.setTextFill(Color.RED);
        sfxVolumeSlider = new Slider(0, 100, sfxVolume * 100);
        sfxVolumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> db.getGameSettings().setSfxVolume(newVal.doubleValue() / 100));

        // Highest score labels
        highestScoreLabel = new Label("Highest Score: " + highestScore);
        highestScoreLabel.setFont(new Font("Arial", 20));
        highestScoreLabel.setTextFill(Color.BLACK);

        highestCoinsLabel = new Label("Highest Coins in One Game: " + highestCoins);
        highestCoinsLabel.setFont(new Font("Arial", 20));
        highestCoinsLabel.setTextFill(Color.BLACK);

        totalCoinsLabel = new Label("Total Coins: " + totalCoins);
        totalCoinsLabel.setFont(new Font("Arial", 20));
        totalCoinsLabel.setTextFill(Color.BLACK);

        // Return button
        Button returnButton = new Button("Return");
        returnButton.setStyle("-fx-background-color:yellow; -fx-text-fill:red;");
        returnButton.setPrefSize(100, 40);
        returnButton.setOnAction(event -> {
            // Game.saveScores(); // Save scores when returning
            uiManager.showMainMenu();
        });

        // Reset Game button
        Button resetButton = new Button("Reset Game");
        resetButton.setStyle("-fx-background-color:orange; -fx-text-fill:white;");
        resetButton.setPrefSize(100, 40);
        resetButton.setOnAction(event -> showResetConfirmationDialog());


        // Layout
        VBox layout = new VBox(20, title, musicVolumeLabel, musicVolumeSlider,
                sfxVolumeLabel, sfxVolumeSlider, highestScoreLabel,
                highestCoinsLabel, totalCoinsLabel, resetButton, returnButton);
        layout.setAlignment(Pos.CENTER);

        layout.setAlignment(Pos.CENTER);

        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/Background.jpg")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(background));

        // Initialize MediaPlayer
        Media media = new Media(Objects.requireNonNull(getClass().getResource("/images/s1.mp3")).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        mediaPlayer.setVolume(musicVolume); // Set volume
        mediaPlayer.play(); // Start playing music

        // Scene
        scene = new Scene(layout, 1200, 650);
    }

    public Scene getScene() {
        return scene;
    }

    private void resetGame() {
        // Clear game data
        db.resetGameData();

        // Show main menu or any other relevant action after resetting
        uiManager.showMainMenu();
    }

    private void showResetConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Reset");
        alert.setHeaderText("Are you sure you want to reset the game?");
        alert.setContentText("This will clear all game data and delete the database files.");

        ButtonType confirmButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("No");
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == confirmButton) {
                resetGame();
            }
        });
    }

    // Method to update the displayed values
    public void updateData(double highestScore, double highestCoins, double totalCoins, double musicVolume, double sfxVolume) {
        highestScoreLabel.setText("Highest Score: " + (int) highestScore);
        highestCoinsLabel.setText("Highest Coins in One Game: " + (int) highestCoins);
        totalCoinsLabel.setText("Total Coins: " + (int) totalCoins);

        mediaPlayer.setVolume(musicVolume); // Update music volume
        musicVolumeSlider.setValue(musicVolume * 100);
        sfxVolumeSlider.setValue(sfxVolume * 100);
    }
}
