package com.example.chocolateria.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class registroMaquinariaApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        System.out.println(getClass().getResource("/vistas/vistaRegistroMaquinaria.fxml"));

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/vistas/vistaRegistroMaquinaria.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Registro de Maquinaria");
        stage.setScene(scene);
        stage.show();
    }
}