package com.example.skyrunner.managers;

import java.io.*;
import java.util.logging.Logger;

public class ScoreManager {
    // Path to the file storing scores
    private static final String FILE_PATH = "scores.dat";
    private int totalCoins;
    private int highestCoins;
    private int highestScore;

    private static final Logger logger = Logger.getLogger(ScoreManager.class.getName());

    // Constructor
    public ScoreManager() {
        totalCoins = 0;
        highestCoins = 0;
        highestScore = 0;
        loadScores();
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
        saveScores();
    }

    public int getHighestCoins() {
        return highestCoins;
    }

    public void setHighestCoins(int highestCoins) {
        this.highestCoins = highestCoins;
        saveScores();
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
        saveScores();
    }

    // Load scores from the file
    public void loadScores() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                totalCoins = dis.readInt();
                highestCoins = dis.readInt();
                highestScore = dis.readInt();
            } catch (IOException e) {
                logger.severe("Failed to load score: " + e.getMessage());
            }
        } else {
            saveScores(); // Create the file with initial values if it doesn't exist
        }
    }

    // Save scores to the file
    public void saveScores() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH))) {
            dos.writeInt(totalCoins);
            dos.writeInt(highestCoins);
            dos.writeInt(highestScore);
        } catch (IOException e) {
            logger.severe("Failed to save score: " + e.getMessage());
        }
    }

    // Update the scores with the new values
    public void updateScore(int coins, int score) {
        totalCoins += coins;
        if (coins > highestCoins) {
            highestCoins = coins;
        }
        if (score > highestScore) {
            highestScore = score;
        }
        saveScores();
    }

    // Reset the scores to the initial values
    public void resetScore() {
        // Reset in-memory data
        totalCoins = 0;
        highestCoins = 0;
        highestScore = 0;
        saveScores();
    }
}
