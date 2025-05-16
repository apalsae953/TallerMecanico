package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.Vehiculos;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;

public class InsertarVehiculo extends Controlador {

    @FXML
    private TextField Marcatx;

    @FXML
    private TextField Matriculatx;

    @FXML
    private TextField Modelotx;

    @FXML
    void aceptarInsertarVehiculo(ActionEvent event) {
        Evento insertarVehiculo = Evento.INSERTAR_VEHICULO;

    }

    @FXML
    void cerrarInsertarVehiculo(ActionEvent event) {
        getEscenario().close();
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
        getEscenario().close();
    }

    @FXML
    void acercaDe(ActionEvent event) {
        AcercaDe acercaDe = (AcercaDe) Controladores.get("/vistas/AcercaDe.fxml", "Acerca De", getEscenario());
        acercaDe.getEscenario().show();
    }
}
