package com.example.skyrunner;

import com.example.skyrunner.database.Database;
import com.example.skyrunner.viewfactory.UIManager;
import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) {
        // Initialize the database for the first time to load all the data.
        // Singleton Pattern
        Database.getInstance();

        // View Factory
        UIManager uiManager = new UIManager(primaryStage);
        uiManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
