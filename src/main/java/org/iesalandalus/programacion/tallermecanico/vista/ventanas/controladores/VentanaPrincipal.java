package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;

public class VentanaPrincipal extends Controlador {

    @FXML
    void buscarCliente(ActionEvent event) {
        BuscarCliente buscarCliente = (BuscarCliente) Controladores.get("/vistas/BuscarCliente.fxml", "Buscar Cliente", getEscenario());
        buscarCliente.getEscenario().show();
    }

    @FXML
    void editarCliente(ActionEvent event) {

    }

    @FXML
    void listarCliente(ActionEvent event) {
        ListarCliente listarCliente = (ListarCliente) Controladores.get("/vistas/ListarCliente.fxml", "Listar Cliente", getEscenario());
        listarCliente.getEscenario().show();
    }

    @FXML
    void editarVehiculo(ActionEvent event) {
        EditarVehiculo editarVehiculo = (EditarVehiculo) Controladores.get("/vistas/EditarVehículo.fxml","Editar Vehículo", getEscenario());
        editarVehiculo.getEscenario().show();
    }

    public void cerrarVentana(ActionEvent actionEvent) {
        if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que quieres salir de la página?", getEscenario())) {
            getEscenario().close();
            VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.SALIR);
        }
    }

    @FXML
    void acercaDe(ActionEvent event) {
        AcercaDe acercaDe = (AcercaDe) Controladores.get("/vistas/AcercaDe.fxml", "Acerca De", getEscenario());
        acercaDe.getEscenario().show();
    }
}