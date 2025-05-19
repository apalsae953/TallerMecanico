package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

public class InsertarVehiculo extends Controlador {

    @FXML
    private TextField Marcatx;

    @FXML
    private TextField Matriculatx;

    @FXML
    private TextField Modelotx;

    public Vehiculo getVehiculo() {
        String matricula = Matriculatx.getText();
        String marca = Marcatx.getText();
        String modelo = Modelotx.getText();
        return new Vehiculo(marca, modelo, matricula);
    }

    @FXML
    void aceptarInsertarVehiculo(ActionEvent event) {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_VEHICULO);
        VistaVentanas.getInstancia().leerVehiculo();
    }

    @FXML
    void cerrarInsertarVehiculo(ActionEvent event) {
        getEscenario().close();
    }
}
