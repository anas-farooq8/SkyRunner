package com.example.skyrunner.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Car {
    protected Image image;
    protected double x;
    protected double y;

    public Car(Image image, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public abstract void update(double speed);

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return image.getWidth();
    }

    public double getHeight() {
        return image.getHeight();
    }
}
