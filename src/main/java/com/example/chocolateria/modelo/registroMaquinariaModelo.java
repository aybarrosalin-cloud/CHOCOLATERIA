package com.example.chocolateria.modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class registroMaquinariaModelo {

    private StringProperty codigo;
    private StringProperty nombre;
    private StringProperty tipo;
    private StringProperty marca;
    private StringProperty serie;
    private StringProperty fecha;
    private StringProperty estado;
    private StringProperty responsable;

    public registroMaquinariaModelo (String codigo, String nombre, String tipo, String marca, String serie, String fecha, String estado, String responsable) {
        this.codigo = new SimpleStringProperty(codigo);
        this.nombre = new SimpleStringProperty(nombre);
        this.tipo = new SimpleStringProperty(tipo);
        this.marca = new SimpleStringProperty(marca);
        this.serie = new SimpleStringProperty(serie);
        this.fecha = new SimpleStringProperty(fecha);
        this.estado = new SimpleStringProperty(estado);
        this.responsable = new SimpleStringProperty(responsable);
    }

    public StringProperty codigoProperty() { return codigo; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty tipoProperty() { return tipo; }
    public StringProperty marcaProperty() { return marca; }
    public StringProperty serieProperty() { return serie; }
    public StringProperty fechaProperty() { return fecha; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty responsableProperty() { return responsable; }
}