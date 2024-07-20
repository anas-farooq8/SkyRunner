package com.example.skyrunner.database;

import com.example.skyrunner.managers.CarManager;
import com.example.skyrunner.managers.GameSettingsManager;
import com.example.skyrunner.managers.ScoreManager;

public class Database {
    // Single instance of the database
    private static Database instance;

    // Managers for handling data
    private final ScoreManager scoreManager;
    private final CarManager carManager;
    private final GameSettingsManager gameSettingsManager;

    // Private constructor to prevent instantiation
    private Database() {
        // Initialize the managers
        scoreManager = new ScoreManager();
        carManager = new CarManager();
        gameSettingsManager = new GameSettingsManager();
    }

    // Singleton pattern
    public synchronized static Database getInstance() {
        // Create a new instance if it doesn't exist
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public CarManager getCarManager() {
        return carManager;
    }

    public GameSettingsManager getGameSettings() {
        return gameSettingsManager;
    }

    // Reset all game data
    public void resetGameData() {
        scoreManager.resetScore();
        carManager.resetCarOwnership();
        gameSettingsManager.resetGameSettings();
    }
}
