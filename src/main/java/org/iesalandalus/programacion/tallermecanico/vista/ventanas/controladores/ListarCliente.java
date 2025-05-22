package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Clientes;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

public class ListarCliente extends Controlador {

    @FXML
    private ListView<String> lvClientes = new ListView<>();

    @FXML
    private ImageView imagen;

    @FXML
    void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
                Clientes.getInstancia().get().stream().map(Cliente::toString).toArray(String[]::new));
        lvClientes.setItems(items);
    }
}