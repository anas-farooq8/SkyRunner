module com.example.skyrunner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;
    requires java.logging;

    opens com.example.skyrunner to javafx.fxml;
    exports com.example.skyrunner;
    exports com.example.skyrunner.database;
    opens com.example.skyrunner.database to javafx.fxml;
    exports com.example.skyrunner.managers;
    opens com.example.skyrunner.managers to javafx.fxml;
    exports com.example.skyrunner.viewfactory;
    opens com.example.skyrunner.viewfactory to javafx.fxml;
    exports com.example.skyrunner.util;
    opens com.example.skyrunner.util to javafx.fxml;
    exports com.example.skyrunner.screens;
    opens com.example.skyrunner.screens to javafx.fxml;
}