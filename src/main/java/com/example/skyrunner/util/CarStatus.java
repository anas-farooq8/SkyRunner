package com.example.skyrunner.util;

public class CarStatus {
    private boolean owned;
    private boolean selected;

    public CarStatus(boolean owned, boolean selected) {
        this.owned = owned;
        this.selected = selected;
    }

    public boolean isOwned() {
        return owned;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CarStatus{" +
                "owned=" + owned +
                ", selected=" + selected +
                '}';
    }
}