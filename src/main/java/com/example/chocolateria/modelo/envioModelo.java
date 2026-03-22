package com.example.chocolateria.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class envioModelo {

    private StringProperty codigo;
    private StringProperty cliente;
    private StringProperty fechaEnvio;
    private StringProperty fechaEntrega;
    private StringProperty transportista;
    private StringProperty temperatura;
    private StringProperty estado;
    private StringProperty numeroGuia;

    public envioModelo(String codigo, String cliente, String fechaEnvio,
                       String fechaEntrega, String transportista, String temperatura,
                       String estado, String numeroGuia) {

        this.codigo = new SimpleStringProperty(codigo);
        this.cliente = new SimpleStringProperty(cliente);
        this.fechaEnvio = new SimpleStringProperty(fechaEnvio);
        this.fechaEntrega = new SimpleStringProperty(fechaEntrega);
        this.transportista = new SimpleStringProperty(transportista);
        this.temperatura = new SimpleStringProperty(temperatura);
        this.estado = new SimpleStringProperty(estado);
        this.numeroGuia = new SimpleStringProperty(numeroGuia);
    }
}