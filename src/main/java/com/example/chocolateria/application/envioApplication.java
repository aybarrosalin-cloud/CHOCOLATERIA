package com.example.chocolateria.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class envioApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println(getClass().getResource("/vistas/vistaGestionDeEnvios.fxml"));

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vistas/vistaGestionDeEnvios.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Gestión de Envíos");
        stage.setScene(scene);
        stage.show();
    }
}