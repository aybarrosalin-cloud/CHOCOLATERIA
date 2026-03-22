module com.example.chocolateria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.chocolateria to javafx.fxml;
    opens com.example.chocolateria.controller to javafx.fxml;

    exports com.example.chocolateria;
    exports com.example.chocolateria.application;
}