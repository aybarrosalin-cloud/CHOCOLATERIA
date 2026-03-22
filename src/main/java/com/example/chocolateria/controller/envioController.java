package com.example.chocolateria.controller;

import com.example.chocolateria.baseDeDatos.conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.chocolateria.modelo.envioModelo;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class envioController implements Initializable {

    private Connection con;

    // FORMULARIO
    @FXML private TextField txtCodigo;
    @FXML private ComboBox<String> cmbCliente;
    @FXML private DatePicker dpFechaEnvio;
    @FXML private DatePicker dpFechaEntrega;
    @FXML private ComboBox<String> cmbTransportista;
    @FXML private ComboBox<String> cmbTemperatura;
    @FXML private ChoiceBox<String> chEstado;
    @FXML private TextField txtNumeroGuia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion conexion = new conexion();
        con = conexion.establecerConexion();

        cmbCliente.setItems(llenarClientes());
        cmbTransportista.setItems(llenarTransportista());
        cmbTemperatura.setItems(llenarTemperatura());

        chEstado.setItems(FXCollections.observableArrayList("En proceso", "Enviado", "Entregado"));

        actualizarDatos();
    }

    // COMBOS DESDE BD

    public ObservableList<String> llenarClientes() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT nombre FROM tbl_cliente";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }

    public ObservableList<String> llenarTransportista() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT nombre FROM tbl_transportista";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nombre"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }

    public ObservableList<String> llenarTemperatura() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        lista.addAll("Ambiente", "Refrigerado", "Congelado");
        return lista;
    }

    // GUARDAR

    @FXML
    private void guardar(ActionEvent event) {

        try {

            String codigo = txtCodigo.getText();
            String cliente = cmbCliente.getValue();
            LocalDate fechaEnvio = dpFechaEnvio.getValue();
            LocalDate fechaEntrega = dpFechaEntrega.getValue();
            String transportista = cmbTransportista.getValue();
            String temperatura = cmbTemperatura.getValue();
            String estado = chEstado.getValue();
            String guia = txtNumeroGuia.getText();

            String sql = "INSERT INTO tbl_envios VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, cliente);
            ps.setDate(3, Date.valueOf(fechaEnvio));
            ps.setDate(4, Date.valueOf(fechaEntrega));
            ps.setString(5, transportista);
            ps.setString(6, temperatura);
            ps.setString(7, estado);
            ps.setString(8, guia);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Envío guardado");
            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EDITAR

    public void editar(ActionEvent actionEvent) {

        String codigo = txtCodigo.getText().trim();

        String sql = "UPDATE tbl_envios SET " +
                "cliente='" + cmbCliente.getValue() + "', " +
                "fecha_envio='" + dpFechaEnvio.getValue() + "', " +
                "fecha_entrega='" + dpFechaEntrega.getValue() + "', " +
                "transportista='" + cmbTransportista.getValue() + "', " +
                "temperatura='" + cmbTemperatura.getValue() + "', " +
                "estado='" + chEstado.getValue() + "', " +
                "numero_guia='" + txtNumeroGuia.getText() + "' " +
                "WHERE codigo='" + codigo + "'";

        EjecutarSQL(sql);
        actualizarDatos();
    }

    // LIMPIAR

    @FXML
    private void limpiarCampos() {
        txtCodigo.clear();
        txtNumeroGuia.clear();
        cmbCliente.setValue(null);
        cmbTransportista.setValue(null);
        cmbTemperatura.setValue(null);
        chEstado.setValue(null);
        dpFechaEnvio.setValue(null);
        dpFechaEntrega.setValue(null);
    }

    // BASE

    private void cargarDesdeBD() {
        try {
            String sql = "SELECT * FROM tbl_envios";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                envioModelo e = new envioModelo(
                        rs.getString("codigo"),
                        rs.getString("cliente"),
                        rs.getString("fecha_envio"),
                        rs.getString("fecha_entrega"),
                        rs.getString("transportista"),
                        rs.getString("temperatura"),
                        rs.getString("estado"),
                        rs.getString("numero_guia")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarDatos() {
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

    //BUSCAR
    private void buscarDatos(String sql) {

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                txtCodigo.setText(rs.getString("codigo"));
                cmbCliente.setValue(rs.getString("cliente"));
                dpFechaEnvio.setValue(rs.getDate("fecha_envio").toLocalDate());
                dpFechaEntrega.setValue(rs.getDate("fecha_entrega").toLocalDate());
                cmbTransportista.setValue(rs.getString("transportista"));
                cmbTemperatura.setValue(rs.getString("temperatura"));
                chEstado.setValue(rs.getString("estado"));
                txtNumeroGuia.setText(rs.getString("numero_guia"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void fnBuscar(ActionEvent event) {
        String codigo = this.txtCodigo.getText().trim();
        String sql = "SELECT * FROM tbl_envios WHERE codigo='" + codigo + "'";
        buscarDatos(sql);
    }
}