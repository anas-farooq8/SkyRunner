package com.example.skyrunner.managers;

import java.io.*;
import java.util.logging.Logger;

public class GameSettingsManager {
    // Path to the file storing game settings
    private static final String SETTINGS_FILE_PATH = "settings.dat";
    private double musicVolume; // Volume level for music
    private double sfxVolume;   // Volume level for sound effects

    private static final Logger logger = Logger.getLogger(GameSettingsManager.class.getName());

    public GameSettingsManager() {
        // Default volume levels
        this.musicVolume = 0.5;
        this.sfxVolume = 0.5;
        loadSettings();
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(double musicVolume) {
        this.musicVolume = musicVolume;
        saveSettings();
    }

    public double getSfxVolume() {
        return sfxVolume;
    }

    public void setSfxVolume(double sfxVolume) {
        this.sfxVolume = sfxVolume;
        saveSettings();
    }

    // Load game settings from the file
    private void loadSettings() {
        File file = new File(SETTINGS_FILE_PATH);
        if (file.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                musicVolume = dis.readDouble();
                sfxVolume = dis.readDouble();
            } catch (IOException e) {
                logger.severe("Failed to load settings: " + e.getMessage());
            }
        } else {
            saveSettings(); // Create the file with default values if it doesn't exist
        }
    }

    // Save game settings to the file
    private void saveSettings() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(SETTINGS_FILE_PATH))) {
            dos.writeDouble(musicVolume);
            dos.writeDouble(sfxVolume);
        } catch (IOException e) {
            logger.severe("Failed to save settings: " + e.getMessage());
        }
    }

    // Reset game settings to default values
    public void resetGameSettings() {
        this.musicVolume = 0.5; // (0.0 - 1.0)
        this.sfxVolume = 0.5;   // (0.0 - 1.0)
        saveSettings();
    }
}
