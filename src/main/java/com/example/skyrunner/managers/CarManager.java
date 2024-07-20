package com.example.skyrunner.managers;

import com.example.skyrunner.util.CarStatus;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CarManager {
    // Path to the file storing car ownership data
    private static final String CAR_OWNERSHIP_FILE_PATH = "car_ownership.dat";
    // Map to store the owned cars and their status
    private final Map<String, CarStatus> ownedCars = new HashMap<>();

    private static final Logger logger = Logger.getLogger(CarManager.class.getName());

    public CarManager() {
        // Initialize the map with default values
        ownedCars.put("Prius GLI", new CarStatus(true, true)); // Default selected car
        loadCarOwnership();
    }

    public Map<String, CarStatus> getOwnedCars() {
        return ownedCars;
    }

    // Load car ownership data from the file
    public void loadCarOwnership() {
        File file = new File(CAR_OWNERSHIP_FILE_PATH);
        if (file.exists()) {
            // Clear the map before loading the data
            ownedCars.clear();
            try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                int size = dis.readInt();
                for (int i = 0; i < size; i++) {
                    String carName = dis.readUTF();
                    boolean isOwned = dis.readBoolean();
                    boolean isSelected = dis.readBoolean();
                    ownedCars.put(carName, new CarStatus(isOwned, isSelected));
                }
            } catch (IOException e) {
                logger.severe("Failed to load car ownership: " + e.getMessage());
            }
        } else {
            saveCarOwnership(); // Create the file with initial values if it doesn't exist
        }
    }

    // Save car ownership data to the file
    public void saveCarOwnership() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(CAR_OWNERSHIP_FILE_PATH))) {
            dos.writeInt(ownedCars.size());
            for (Map.Entry<String, CarStatus> entry : ownedCars.entrySet()) {
                dos.writeUTF(entry.getKey());
                dos.writeBoolean(entry.getValue().isOwned());
                dos.writeBoolean(entry.getValue().isSelected());
            }
        } catch (IOException e) {
            logger.severe("Failed to save car ownership: " + e.getMessage());
        }
    }

    // Select a car by name
    public void selectCar(String name) {
        ownedCars.forEach((carName, status) -> status.setSelected(false));
        if (ownedCars.containsKey(name)) {
            ownedCars.get(name).setSelected(true);
        } else {
            ownedCars.put(name, new CarStatus(true, true));
        }
        saveCarOwnership();
    }

    // Get the name of the selected car
    public String getSelectedCar() {
        return ownedCars.entrySet().stream()
                .filter(entry -> entry.getValue().isSelected())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("Prius GLI");
    }

    // Reset car ownership data to default values
    public void resetCarOwnership() {
        ownedCars.clear();
        ownedCars.put("Prius GLI", new CarStatus(true, true)); // Default selected car
        saveCarOwnership();
    }
}
