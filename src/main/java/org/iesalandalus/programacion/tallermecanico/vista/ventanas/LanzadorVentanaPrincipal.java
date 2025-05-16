package org.iesalandalus.programacion.tallermecanico.vista.ventanas;

import javafx.application.Application;
import javafx.stage.Stage;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;

public class LanzadorVentanaPrincipal extends Application{
    @Override
    public void start(Stage stage) {
        VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("/vistas/VentanaPrincipal.fxml", "Taller Mecánico",null);
        ventanaPrincipal.inicializar();
        ventanaPrincipal.getEscenario().show();
        ventanaPrincipal.centrar();
    }

    public static void comenzar(){launch();}
}