package com.example.chocolateria.controller;

import com.example.chocolateria.baseDeDatos.conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import com.example.chocolateria.modelo.suplidorModelo;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class suplidorController implements Initializable {
    private Connection con;

    //formulario
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRNC;

    @FXML
    private TextField txtCel;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<String> cmbCiudad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion conexion = new conexion();
        con = conexion.establecerConexion();
        cmbCiudad.setItems(llenaCombo());
        actualizarDatos();
    }

    public ObservableList<String> llenaCombo() {
        ObservableList<String> ciudad = FXCollections.observableArrayList();

        String sql = "SELECT nombre FROM tbl_ciudad";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ciudad.add(resultSet.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ciudad;
    }

    @FXML
    private void guardarSuplidor(ActionEvent event) {

        try {

            String nombre = txtNombre.getText();
            String rnc = txtRNC.getText();
            String telefono = txtCel.getText();
            String correo = txtCorreo.getText();
            String ciudad = cmbCiudad.getValue();

            conexion conexion = new conexion();
            Connection con = conexion.establecerConexion();

            String sql = "INSERT INTO tbl_suplidor (nombre_apellido, rnc, telefono, correo, ciudad) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, rnc);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setString(5, ciudad);
            ps.executeUpdate();
            System.out.println(cmbCiudad.getValue().toString());
            System.out.println("Suplidor guardado en BD");

            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDesdeBD() {

        try {
            // conexion conexion = new conexion();
            String sql = "SELECT * FROM tbl_suplidor";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                suplidorModelo s = new suplidorModelo(
                        rs.getString("nombre_apellido"),
                        rs.getString("rnc"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("ciudad")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ObservableList<String> Ciudad = FXCollections.observableArrayList();

    public void MostrarCiudad(Event event) {
        cmbCiudad.setItems(llenaCombo());
        //System.out.println(cmbCiudad.getValue().toString());

        //esto es para que seleccione el que esta de primero como por default
        cmbCiudad.getSelectionModel().selectFirst();
//        cmbCiudad.valueProperty().addListener((ObservableValue observable, Object valorantiguo, Object nuevo)) -> ;
    }

    //esto es para consultar, para qu evaya a la bd, busque esa info y me la traiga
    public void fnbuscar(ActionEvent actionEvent) {
        String Nombre = this.txtNombre.getText().trim();
        String sql = "SELECT * FROM tbl_suplidor WHERE nombre_apellido='" + Nombre + "'";
        buscarDatos(sql); //para buscar la informacion en la base de datos
    }

    public void fnEditar(ActionEvent actionEvent){
        String Nombre=(this.txtNombre.getText().trim());
        String RNC=(this.txtRNC.getText().trim());
        String Telefono=(this.txtCel.getText().trim());
        String Correo=(this.txtCorreo.getText().trim());

        String sql="update tbl_Suplidor set nombre_apellido='" + Nombre + "',RNC='" + RNC + "',Telefono='" + Telefono +
                "',correo='" + Correo + "'where rnc='" + RNC + "'";
        System.out.println(sql);
        EjecutarSQL(sql);
        actualizarDatos();
    }

    public void actualizarDatos() {
//        // tener las columnas con el modelo
//        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
//        colRnc.setCellValueFactory(data -> data.getValue().rncProperty());
//        colTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
//        colCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
//        colCiudad.setCellValueFactory(data -> data.getValue().ciudadProperty());
//
//        // conectar tabla con la lista
//        tablaSuplidores.setItems(listaObservable);
        cargarDesdeBD();
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

    private void buscarDatos(String sql) {

        try {
            conexion conexion = new conexion();
            Connection con = conexion.establecerConexion();

            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                txtNombre.setText(resultSet.getString("nombre_apellido"));
                txtRNC.setText(resultSet.getString("rnc"));
                txtCel.setText(resultSet.getString("telefono"));
                txtCorreo.setText(resultSet.getString("correo"));
                String ciudad=resultSet.getString("ciudad");
                this.cmbCiudad.getSelectionModel().select(ciudad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fnlimpiar(ActionEvent actionEvent){
        limpiarCampos();
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtRNC.clear();
        txtCel.clear();
        txtCorreo.clear();
        cmbCiudad.getSelectionModel().selectFirst();
    }
}