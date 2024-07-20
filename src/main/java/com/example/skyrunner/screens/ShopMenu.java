package com.example.skyrunner.screens;

import com.example.skyrunner.database.Database;
import com.example.skyrunner.util.CarStatus;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class ShopMenu {

    private final UIManager uiManager;
    private final Scene scene;
    private String selectedCarName;
    VBox layout;
    private Label coinsLabel; // Label to display the total coins
    Database db;

    // Constructor
    public ShopMenu(UIManager uiManager) {
        this.uiManager = uiManager;
        db = Database.getInstance();
        this.selectedCarName = db.getCarManager().getSelectedCar(); // Load the selected car from the database

        layout = new VBox(20); // Spacing between elements
        layout.setAlignment(Pos.CENTER); // Center all elements in the VBox

        // Load the background image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/images/Background.jpg")).toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        layout.setBackground(new Background(background));

        // Create the title text
        Text title = new Text("SHOP");
        title.setFont(Font.font("Century Gothic", 72));
        title.setStyle("-fx-font-weight: bold;");
        title.setFill(Color.WHITE);

        // Create the back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;"); // Set same style as other buttons
        backButton.setPrefSize(100, 40); // Set width and height
        backButton.setOnAction(e -> uiManager.showMainMenu());

        // Create the coin display
        createCoinDisplay();

        // Add title to layout
        layout.getChildren().add(title);

        // Add car options
        addCarToShop(layout, "/Car1.png", "Prius GLI", 1000, "▮▯▯▯▯", "6");
        addCarToShop(layout, "/Car2.png", "HatNor", 900, "▮▮▮▯▯", "8");
        addCarToShop(layout, "/Car3.png", "RSA-111", 2000, "▮▮▮▮▯", "10");
        addCarToShop(layout, "/Car4.png", "Tesla Model 3", 35000, "▮▮▮▮▮", "12");

        // Add the back button to layout
        layout.getChildren().add(backButton);

        // Scene setup
        scene = new Scene(layout, 800, 650);
    }

    private void createCoinDisplay() {
        // Load the coin image
        Image coinImage = new Image(Objects.requireNonNull(getClass().getResource("/coins1.png")).toExternalForm());
        // ImageView for the coin image
        ImageView coinImageView = new ImageView(coinImage);
        coinImageView.setTranslateX(-8);
        coinImageView.setTranslateY(4);
        coinImageView.setFitWidth(20); // Set desired width for coin image
        coinImageView.setFitHeight(20); // Set desired height for coin image
        coinImageView.setPreserveRatio(true); // Preserve aspect ratio

        // Create the coins label
        coinsLabel = new Label("Coins: " + db.getScoreManager().getTotalCoins());
        coinsLabel.setTextFill(Color.WHITE); // Set text color to white
        coinsLabel.setFont(Font.font("Century Gothic", 18)); // Set font size and type
        coinsLabel.setPadding(new javafx.geometry.Insets(8)); // Add some padding

        // Create an HBox to align the coin image and label on the top right
        HBox coinDisplay = new HBox(5);
        coinDisplay.setAlignment(Pos.TOP_RIGHT);
        coinDisplay.getChildren().addAll(coinsLabel, coinImageView);

        // Add coinDisplay to the top of the VBox
        layout.getChildren().addFirst(coinDisplay); // Add it at the top of the layout
    }

    private void addCarToShop(VBox layout, String imagePath, String name, int price, String rating, String maneuverability) {
        HBox carBox = new HBox(10);
        carBox.setAlignment(Pos.CENTER); // Center the car details

        Image carImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        ImageView carImageView = new ImageView(carImage);
        carImageView.setFitWidth(200); // Set desired width
        carImageView.setFitHeight(100); // Set desired height
        carImageView.setPreserveRatio(true); // Preserve aspect ratio

        Text carDetails = new Text(name + " - $" + price + " - " + rating);
        carDetails.setFill(Color.WHITE); // Set text color to white

        Label maneuverabilityLabel = new Label("Maneuverability: " + maneuverability);
        maneuverabilityLabel.setTextFill(Color.WHITE); // Set text color for the label

        // Check if the car is already owned
        boolean owned = db.getCarManager().getOwnedCars().getOrDefault(name, new CarStatus(false, false)).isOwned();

        if (owned) {
            if (name.equals(selectedCarName)) {
                // Create a button to show the car is selected
                Button selectedButton = new Button("Selected");
                selectedButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                selectedButton.setPrefSize(100, 40);
                selectedButton.setDisable(true);
                Game.selectCar(name);
                Game.setSelectedCarImage("/images/playerCars" + imagePath.toLowerCase()); // Pass image path as String

                carBox.getChildren().addAll(carImageView, carDetails, maneuverabilityLabel, selectedButton);
            } else {
                Button selectButton = getButton(imagePath, name);

                carBox.getChildren().addAll(carImageView, carDetails, maneuverabilityLabel, selectButton);
            }
        } else {
            // Create the button (Buy)
            Button actionButton = new Button(Integer.toString(price));
            actionButton.setStyle("-fx-background-color: black; -fx-text-fill: white;"); // Black background, white text
            actionButton.setPrefSize(100, 40);

            // Load the coin image
            Image coinImage = new Image(Objects.requireNonNull(getClass().getResource("/coins1.png")).toExternalForm());
            ImageView coinImageView = new ImageView(coinImage);
            coinImageView.setFitWidth(20); // Set desired width for coin image
            coinImageView.setFitHeight(20); // Set desired height for coin image
            coinImageView.setPreserveRatio(true); // Preserve aspect ratio

            // Create an HBox to combine button and coin image
            HBox buttonWithIcon = new HBox(5);
            buttonWithIcon.setAlignment(Pos.CENTER);
            buttonWithIcon.getChildren().addAll(actionButton, coinImageView);

            actionButton.setOnAction(e -> {
                // Display confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Purchase Confirmation");
                alert.setHeaderText("Are you sure you want to buy this car?");
                alert.setContentText("Click OK to confirm or Cancel to go back.");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Check if the player has enough coins
                        if (db.getScoreManager().getTotalCoins() >= price) {
                            // Deduct the coins from the player's balance
                            db.getScoreManager().setTotalCoins(db.getScoreManager().getTotalCoins() - price);
                            db.getScoreManager().updateScore(0, 0);

                            // Logic for processing the purchase
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Purchase Successful");
                            successAlert.setContentText("Thank you for your purchase!");

                            successAlert.showAndWait();

                            // Update the button status
                            actionButton.setText("Owned");
                            actionButton.setDisable(true); // Disable the button after purchase

                            // Update the ownership status
                            db.getCarManager().getOwnedCars().put(name, new CarStatus(true, false));
                            db.getCarManager().saveCarOwnership();

                            // Update the coin display
                            coinsLabel.setText("Coins: " + db.getScoreManager().getTotalCoins());

                            // Create select button
                            Button selectButton = getSelectButton(imagePath, name);

                            carBox.getChildren().remove(buttonWithIcon);
                            carBox.getChildren().add(selectButton);
                        } else {
                            // Show an error alert if the player doesn't have enough coins
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Purchase Failed");
                            errorAlert.setContentText("You do not have enough coins to make this purchase.");

                            errorAlert.showAndWait();
                        }
                    }
                });
            });

            // Add image, details, maneuverability label, and the button with icon to the carBox HBox
            carBox.getChildren().addAll(carImageView, carDetails, maneuverabilityLabel, buttonWithIcon);
        }

        layout.getChildren().add(carBox);
    }

    private Button getSelectButton(String imagePath, String name) {
        Button selectButton = new Button("Select");
        selectButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        selectButton.setPrefSize(100, 40);
        selectButton.setOnAction(ev -> {
            Game.selectCar(name);
            Game.setSelectedCarImage("/images/playerCars" + imagePath.toLowerCase()); // Pass image path as String
            db.getCarManager().selectCar(name); // Save selected car to database
            selectedCarName = name;
            refreshShop();
        });
        return selectButton;
    }

    private Button getButton(String imagePath, String name) {
        Button selectButton = new Button("Select");
        selectButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green background, white text
        selectButton.setPrefSize(100, 40);
        selectButton.setOnAction(e -> {
            Game.selectCar(name);
            Game.setSelectedCarImage("/images/playerCars" + imagePath.toLowerCase()); // Pass image path as String
            db.getCarManager().selectCar(name); // Save selected car to database
            selectedCarName = name;
            refreshShop();
        });
        return selectButton;
    }

    public void refreshShop() {
        layout.getChildren().clear();
        Text title = new Text("SHOP");
        title.setFont(Font.font("Century Gothic", 72));
        title.setStyle("-fx-font-weight: bold;");
        title.setFill(Color.WHITE);
        layout.getChildren().add(title);
        addCarToShop(layout, "/Car1.png", "Prius GLI", 1000, "▮▯▯▯▯", "6");
        addCarToShop(layout, "/Car2.png", "HatNor", 900, "▮▮▮▯▯", "8");
        addCarToShop(layout, "/Car3.png", "RSA-111", 2000, "▮▮▮▮▯", "10");
        addCarToShop(layout, "/Car4.png", "Tesla Model 3", 35000, "▮▮▮▮▮", "12");

        // Load the coin
        createCoinDisplay();

        // Create the back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #A569BD; -fx-text-fill: white;"); // Set same style as other buttons
        backButton.setPrefSize(100, 40); // Set width and height
        backButton.setOnAction(e -> uiManager.showMainMenu());

        layout.getChildren().add(backButton);
    }

    public Scene getScene() {
        return scene;
    }
}
