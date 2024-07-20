package com.example.skyrunner.screens;
import com.example.skyrunner.database.Database;
import com.example.skyrunner.models.PlayerCar;
import com.example.skyrunner.models.TrafficCar;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button; // Import for the pause button
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer; // Import for the MediaPlayer
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Game {
    private final UIManager uiManager;
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private Image skyImage, cloudsImage, beamsImage, distantBuildings1Image, distantBuildings2Image, distantBuildings3Image, fenceImage, playerCarImage, trafficCarImage, trafficCarImage2, trafficCarImage3, trafficCarImage4, scoreUI;
    private double cloudsX, beamsX, distantBuildings1X, distantBuildings2X, distantBuildings3X, fenceX;
    private PlayerCar playerCar;
    private List<TrafficCar> trafficCars;
    static double score;
    private boolean running;
    private final AnimationTimer gameLoop;
    private double spawnRate;
    private double trafficCarSpeed;
    static double distance; // New: Distance variable
    //static int coins; // New: Coins variable
    public final Button pauseButton; // New: Pause button
    private boolean paused; // New: Pause state
    private MediaPlayer crashSoundPlayer; // New: MediaPlayer for crash sound
    private static int coins;
    private static String selectedCar;
    private static String selectedCarImage;

    private double distanceForCoin = 0; // Variable to track distance for coin calculation


    public Game(UIManager uiManager) {
        this.uiManager = uiManager;
        Pane root = new Pane();
        canvas = new Canvas(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.5, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.5);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        loadImages();
        // initializeMedia(); // Initialize the media for background music
        initializeGame();

        // Initialize pause button
        pauseButton = new Button("Pause");
        pauseButton.setLayoutX(20); // Set position
        pauseButton.setLayoutY(30); // Set position below distance and coins
        pauseButton.setPrefWidth(100); // Set button width
        pauseButton.setPrefHeight(40); // Set button height
        pauseButton.setStyle("-fx-background-color: #FF5833; -fx-text-fill: white;"); // Set background color and text color
        pauseButton.setOnAction(e -> togglePause()); // Handle button action
        root.getChildren().add(pauseButton); // Add button to the scene

        scene = new Scene(root, Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 1.5, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.5);
        scene.setOnKeyPressed(e -> {
            if (!paused) { // Check if not paused
                if ((e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) && (playerCar.getY() > 0)) playerCar.moveUp(identifySpeed());
                if ((e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) && playerCar.getY() < canvas.getHeight() - playerCarImage.getHeight()) playerCar.moveDown(identifySpeed());
            }
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (running && !paused) { // Skip if paused
                    update();
                    render();
                }
            }
        };
    }

    public void loadImages() {
        skyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sky.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sky.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/sky.png")))), false, false);
        cloudsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/clouds.png")))), false, false);
        beamsImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/beams.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/beams.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/beams.png")))), false, false);
        distantBuildings1Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings1.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings1.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings1.png")))), false, false);
        distantBuildings2Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings2.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings2.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings2.png")))), false, false);
        distantBuildings3Image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings3.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings3.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/distant_buildings3.png")))), false, false);
        fenceImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fence.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fence.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/fence.png")))), false, false);
        playerCarImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)))), false, false);
        trafficCarImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car1.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car1.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car1.png")))), false, false);
        trafficCarImage2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car2.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car2.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car2.png")))), false, false);
        trafficCarImage3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car3.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car3.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car3.png")))), false, false);
        trafficCarImage4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car4.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car4.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/trafficCars/car4.png")))), false, false);
        scoreUI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/scoreUI.png")), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/scoreUI.png")))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/scoreUI.png")))), false, false);
    }

    private double scaleWidth(Image image) {
        double perc = 3840 / canvas.getWidth();
        return image.getWidth() / perc;
    }

    private double scaleHeight(Image image)
    {
        double perc = 2160 / canvas.getHeight();
        return image.getHeight() / perc;
    }

    private void initializeCrashSound() {
        // Initialize crash sound
        Media crashSound = new Media(Objects.requireNonNull(getClass().getResource("/images/crash.wav")).toString());
        crashSoundPlayer = new MediaPlayer(crashSound);
        Database db = Database.getInstance();
        crashSoundPlayer.setVolume(db.getGameSettings().getSfxVolume()); // Adjust volume
    }

    public void initializeGame() {
        initializeCrashSound(); // Initialize crash sound
        playerCarImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)), scaleWidth(new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)))), scaleHeight(new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedCarImage)))), false, false);

        playerCar = new PlayerCar(playerCarImage, 60, canvas.getHeight() / 2 - playerCarImage.getHeight() / 2);
        trafficCars = new ArrayList<>();
        score = 0;
        distance = 0; // New: Initialize distance
        coins = 0; // New: Initialize coins
        cloudsX = 0;
        beamsX = 0;
        distantBuildings1X = 0;
        distantBuildings2X = 0;
        distantBuildings3X = 0;
        fenceX = 0;
        spawnRate = 0.01;
        trafficCarSpeed = 5;
        running = false;
        paused = false; // New: Initialize pause state
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static double getScore() {
        return score; // Getter for score
    }

    public static int getCoins() {
        return coins; // Getter for coins
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    public void start()
    {
        running = true;
        // mediaPlayer.play(); // Start playing the music
        gameLoop.start();
    }

    private void stop() {
        running = false;
        // mediaPlayer.stop(); // Stop the music when the game stops
        gameLoop.stop();
    }

    private void togglePause() {
        if (paused) {
            resumeGame();
        } else {
            pauseGame();
        }
    }

    private void pauseGame() {
        paused = true;
        // mediaPlayer.pause(); // Pause the music when the game is paused
        gameLoop.stop(); // Stop the game loop
        pauseButton.setText("Resume"); // Change button text

        // Show Pause Screen
        uiManager.pauseGame(this);
    }

    private void resumeGame() {
        paused = false;
        gameLoop.start(); // Resume the game loop
        pauseButton.setText("Pause"); // Change button text back
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    private int identifySpeed() {
        return switch (selectedCar) {
            case "HatNor" -> 8;
            case "RSA-111" -> 10;
            case "Tesla Model 3" -> 12;
            default -> 6; // Prius Gli
        };
    }

    public static void selectCar(String carName) {
        selectedCar = carName;
    }

    public static void setSelectedCarImage(String image) {
        selectedCarImage = image;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    private void update() {
        if (paused) return; // Skip update if paused

        cloudsX -= 0.2;
        beamsX -= 0.3;
        distantBuildings1X -= 0.4;
        distantBuildings2X -= 0.7;
        distantBuildings3X -= 1.1;
        fenceX -= 6.5;
        spawnRate += 0.0000001;

        if (cloudsX <= -cloudsImage.getWidth()) cloudsX = 0;
        if (beamsX <= -beamsImage.getWidth()) beamsX = 0;
        if (distantBuildings1X <= -distantBuildings1Image.getWidth()) distantBuildings1X = 0;
        if (distantBuildings2X <= -distantBuildings2Image.getWidth()) distantBuildings2X = 0;
        if (distantBuildings3X <= -distantBuildings3Image.getWidth()) distantBuildings3X = 0;
        if (fenceX <= -fenceImage.getWidth()) fenceX = 0;

        // Add new traffic cars
        if (Math.random() < spawnRate) {
            trafficCars.add(new TrafficCar(trafficCarImage, trafficCarImage2, trafficCarImage3, trafficCarImage4, canvas.getWidth(), Math.random() * canvas.getHeight()));
        }

        // Update traffic cars
        Iterator<TrafficCar> iterator = trafficCars.iterator();
        while (iterator.hasNext()) {
            TrafficCar trafficCar = iterator.next();
            trafficCar.update(trafficCarSpeed);
            if (trafficCar.getX() < -trafficCar.getWidth()) {
                iterator.remove();
            }
            if (trafficCar.collidesWith(playerCar)) {
                crashSoundPlayer.play(); // Play crash sound
                stop();
                // Show Game Over Screen
                uiManager.gameOver(this);

                System.out.println("Game Over");
                // print score and coins collected in the game
                System.out.println("Score: " + (int)score);
                System.out.println("Coins: " + coins);

                // Save the score and coins
                Database db = Database.getInstance();
                db.getScoreManager().updateScore(coins, (int) score);

            }
        }

        // Update score and distance
        score += 0.02;
        distance += 0.1;
        distanceForCoin += 0.1; // Increment distance for coin calculation

        // Check if enough distance has been traveled to earn coins
        if (distanceForCoin >= 20) {
            coins++; // Increment coins
            distanceForCoin = 0; // Reset distance for coin calculation
        }

        // Increment traffic car speed based on the score (Increasing Difficulty)
        if (score >= 50 && score < 100) {
            trafficCarSpeed = 6;
        } else if (score >= 100 && score < 150) {
            trafficCarSpeed = 7;
        } else if (score >= 150 && score < 200) {
            trafficCarSpeed = 8;
        } else if (score >= 200 && score < 250) {
            trafficCarSpeed = 9;
        } else if (score >= 250) {
            trafficCarSpeed = 10;
        }
    }


    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(skyImage, 0, 0);
        gc.drawImage(cloudsImage, cloudsX, 0);
        gc.drawImage(cloudsImage, cloudsX + cloudsImage.getWidth(), 0);
        gc.drawImage(beamsImage, beamsX, 0);
        gc.drawImage(beamsImage, beamsX + beamsImage.getWidth(), 0);
        gc.drawImage(distantBuildings1Image, distantBuildings1X, 0);
        gc.drawImage(distantBuildings1Image, distantBuildings1X + distantBuildings1Image.getWidth(), 0);
        gc.drawImage(distantBuildings2Image, distantBuildings2X, 0);
        gc.drawImage(distantBuildings2Image, distantBuildings2X + distantBuildings2Image.getWidth(), 0);
        gc.drawImage(distantBuildings3Image, distantBuildings3X, 0);
        gc.drawImage(distantBuildings3Image, distantBuildings3X + distantBuildings3Image.getWidth(), 0);
        gc.drawImage(fenceImage, fenceX, 0);
        gc.drawImage(fenceImage, fenceX + fenceImage.getWidth(), 0);
        gc.drawImage(scoreUI, canvas.getWidth() / 2 - (scoreUI.getWidth() / 2), 15);
        playerCar.render(gc);
        for (TrafficCar trafficCar : trafficCars) {
            trafficCar.render(gc);
        }

        // Draw score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Courier New", 30));
        gc.fillText("" + (int)score, canvas.getWidth() / 2 - 10 - ((score / 10)), 35);

        gc.fillText("Distance: " + (int)distance, canvas.getWidth() - 250, 30); // Position on the right side
        // Draw coins
        gc.fillText("Coins: " + coins, canvas.getWidth() - 250, 70); // Position on the right side

        // Optional: Indicate pause visually
        if (paused) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Courier New", 50));
            gc.fillText("PAUSED", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
