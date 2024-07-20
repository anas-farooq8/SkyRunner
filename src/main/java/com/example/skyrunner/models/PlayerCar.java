package com.example.skyrunner.models;

import javafx.scene.image.Image;

public class PlayerCar extends Car {

    public PlayerCar(Image image, double x, double y) {
        super(image, x, y);
    }

    @Override
    public void update(double speed) {
        // PlayerCar's update logic can be different if needed
    }

    public void moveUp(int speed) {
        y -= speed;
    }

    public void moveDown(int speed) {
        y += speed;
    }
}
