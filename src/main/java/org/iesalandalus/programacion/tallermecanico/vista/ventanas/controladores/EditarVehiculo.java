package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;

public class EditarVehiculo extends Controlador {

    @FXML
    void borrarVehiculo(ActionEvent event) {

    }

    @FXML
    void insertarVehiculo(ActionEvent event) {
        InsertarVehiculo insertarVehiculo = (InsertarVehiculo) Controladores.get("/vistas/InsertarVehiculo.fxml","Insertar Vehículo", getEscenario());
        insertarVehiculo.getEscenario().show();
    }

}
