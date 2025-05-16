package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

public class ListarCliente extends Controlador {

    @FXML
    void cerrarVentana(ActionEvent event) {
        getEscenario().close();
    }

}
