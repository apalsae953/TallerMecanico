package org.iesalandalus.programacion.tallermecanico.vista.ventanas;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;


public class LanzadorVentanaPrincipal extends Application{
    @Override
    public void start(Stage stage) {
        VistaVentanas.getInstancia().inicializar();
        VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("/vistas/VentanaPrincipal.fxml", "Taller Mecánico",null);
        ventanaPrincipal.getEscenario().setOnCloseRequest(this::salir);
        ventanaPrincipal.inicializar();
        ventanaPrincipal.getEscenario().show();
        ventanaPrincipal.centrar();
    }

    public static void comenzar(){launch();}

    private void salir(WindowEvent e){
        Stage padre = (Stage)e.getSource();
        if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que quieres salir?", padre)) {
            padre.close();
            VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.SALIR);
        } else {
            e.consume();
        }
    }
}