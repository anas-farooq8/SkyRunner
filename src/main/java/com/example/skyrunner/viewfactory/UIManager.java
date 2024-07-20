package com.example.skyrunner.viewfactory;

import com.example.skyrunner.database.Database;
import com.example.skyrunner.screens.*;
import javafx.stage.Stage;

public class UIManager {
    private final Stage primaryStage;

    // Initialize all the Views
    private final MainMenu mainMenu;
    private final SettingsMenu settingsMenu;
    private final Game game;

    // Constructor
    public UIManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mainMenu = new MainMenu(this);
        ShopMenu shopMenu = new ShopMenu(this);
        this.game = new Game(this);
        this.settingsMenu = new SettingsMenu(this);
    }

    // Methods to show different views
    public void showMainMenu() {
        primaryStage.setScene(mainMenu.getScene());
        primaryStage.show();
    }

    // Show settings menu
    public void showSettingsMenu() {
        Database db = Database.getInstance();
        double highestScore = db.getScoreManager().getHighestScore();
        double highestCoins = db.getScoreManager().getHighestCoins();
        double totalCoins = db.getScoreManager().getTotalCoins();
        double musicVolume = db.getGameSettings().getMusicVolume();
        double sfxVolume = db.getGameSettings().getSfxVolume();

        // Update data in settings menu
        settingsMenu.updateData(highestScore, highestCoins, totalCoins, musicVolume, sfxVolume);

        primaryStage.setScene(settingsMenu.getScene());
        primaryStage.show();
    }

    // Show shop menu
    public void showShopMenu() {
        ShopMenu shopMenu = new ShopMenu(this);
        primaryStage.setScene(shopMenu.getScene());
        primaryStage.show();
    }

    // Start the game
    public void startGame() {
        primaryStage.setScene(game.getScene());
        primaryStage.show();
        game.initializeGame();
        game.start();
    }

    // Show game over screen
    public void gameOver(Game game) {
        TryAgainScreen tryAgainScreen = new TryAgainScreen(game, this);
        primaryStage.setScene(tryAgainScreen.getScene());
        primaryStage.show();
    }

    public void pauseGame(Game game) {
        PauseScreen pauseScreen = new PauseScreen(game, this);
        primaryStage.setScene(pauseScreen.getScene());
        primaryStage.show();
    }

}
