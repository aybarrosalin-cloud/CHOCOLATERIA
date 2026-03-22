package com.example.chocolateria.controller;

import com.example.chocolateria.baseDeDatos.conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import com.example.chocolateria.modelo.registroMaquinariaModelo;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registroMaquinariaController implements Initializable {
    private Connection con;

    //formulario
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtMarcaModelo;
    @FXML private TextField txtNumeroSerie;
    @FXML private DatePicker dpFecha;
    @FXML private ChoiceBox<String> chEstado;
    @FXML private ComboBox<String> cmbResponsable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion conexion = new conexion();
        con = conexion.establecerConexion();

        cmbTipo.setItems(llenarTipo());
        cmbResponsable.setItems(llenarResponsable());

        //este choicebox esta manual, no viene desde la base de datos porque es innecesario
        chEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));

        actualizarDatos();
    }

    public ObservableList<String> llenarTipo() {
        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = "SELECT nombre FROM tbl_tipo_maquinaria";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public ObservableList<String> llenarResponsable() {
        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = "SELECT nombre FROM tbl_responsable";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @FXML
    private void guardar(ActionEvent event) {

        try {

            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String tipo = cmbTipo.getValue();
            String marca = txtMarcaModelo.getText();
            String serie = txtNumeroSerie.getText();
            LocalDate fecha = dpFecha.getValue();
            String estado = chEstado.getValue();
            String responsable = cmbResponsable.getValue();

            String sql = "INSERT INTO tbl_maquinaria (codigo, nombre, tipo, marca_modelo, numero_serie, fecha_adquisicion, estado, responsable) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, tipo);
            ps.setString(4, marca);
            ps.setString(5, serie);
            ps.setDate(6, Date.valueOf(fecha));
            ps.setString(7, estado);
            ps.setString(8, responsable);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Maquinaria guardada");
            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDesdeBD() {

        try {
            String sql = "SELECT * FROM tbl_maquinaria";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                registroMaquinariaModelo m = new registroMaquinariaModelo(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca_modelo"),
                        rs.getString("numero_serie"),
                        rs.getString("fecha_adquisicion"),
                        rs.getString("estado"),
                        rs.getString("responsable")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fnbuscar(ActionEvent actionEvent) {
        String codigo = this.txtCodigo.getText().trim();
        String sql = "SELECT * FROM tbl_maquinaria WHERE codigo='" + codigo + "'";
        buscarDatos(sql);
    }

    private void buscarDatos(String sql) {

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                txtCodigo.setText(resultSet.getString("codigo"));
                txtNombre.setText(resultSet.getString("nombre"));
                cmbTipo.setValue(resultSet.getString("tipo"));
                txtMarcaModelo.setText(resultSet.getString("marca_modelo"));
                txtNumeroSerie.setText(resultSet.getString("numero_serie"));
                dpFecha.setValue(resultSet.getDate("fecha_adquisicion").toLocalDate());
                chEstado.setValue(resultSet.getString("estado"));
                cmbResponsable.setValue(resultSet.getString("responsable"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtMarcaModelo.clear();
        txtNumeroSerie.clear();
        dpFecha.setValue(null);
        cmbTipo.setValue(null);
        chEstado.setValue(null);
        cmbResponsable.setValue(null);
    }

    public void actualizarDatos() {
        cargarDesdeBD();
    }

    public void editar(ActionEvent actionEvent) {
            String codigo = this.txtCodigo.getText().trim();
            String nombre = this.txtNombre.getText().trim();
            String tipo = this.cmbTipo.getValue();
            String marca = this.txtMarcaModelo.getText().trim();
            String serie = this.txtNumeroSerie.getText().trim();
            String fecha = this.dpFecha.getValue().toString();
            String estado = this.chEstado.getValue().toString();
            String responsable = this.cmbResponsable.getValue();

            String sql = "UPDATE tbl_maquinaria SET " +
                    "nombre='" + nombre + "', " +
                    "tipo='" + tipo + "', " +
                    "marca_modelo='" + marca + "', " +
                    "numero_serie='" + serie + "', " +
                    "fecha_adquisicion='" + fecha + "', " +
                    "estado='" + estado + "', " +
                    "responsable='" + responsable + "' " +
                    "WHERE codigo='" + codigo + "'";

            System.out.println(sql);
            EjecutarSQL(sql);
            actualizarDatos();
        }

    public void EjecutarSQL(String sql){
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Accion realizada correctamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error" + e.toString());
        }
    }
}