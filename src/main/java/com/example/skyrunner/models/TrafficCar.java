package com.example.skyrunner.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TrafficCar extends Car {
    private final Image image2;
    private final Image image3;
    private final Image image4;
    private final int i = (int) (Math.random() * 10);

    public TrafficCar(Image image, Image image2, Image image3, Image image4, double x, double y) {
        super(image, x, y);
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    @Override
    public void update(double speed) {
        x -= speed;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (i < 2)
            gc.drawImage(image, x, y);
        else if (i < 4)
            gc.drawImage(image2, x, y);
        else if (i < 6)
            gc.drawImage(image3, x, y);
        else
            gc.drawImage(image4, x, y);
    }

    public boolean collidesWith(PlayerCar playerCar) {
        return x < playerCar.getX() + playerCar.getWidth() &&
                x + getWidth() > playerCar.getX() &&
                y < playerCar.getY() + playerCar.getHeight() &&
                y + getHeight() > playerCar.getY();
    }
}
