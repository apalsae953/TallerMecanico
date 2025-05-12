package org.iesalandalus.programacion.tallermecanico.vista.ventanas;

import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class LanzadorVentanaPrincipal extends Aplication{
    @Override
    public void start(Stage stage) {
        VentanaPrincipal ventanaPrincipal = (VentanaPrincipal) Controladores.get("/vistas/VentanaPrincipal.fxml", "Taller Mecánico",null);
        ventanaPrincipal.inicializar();
        ventanaPrincipal.getEscenario().show;
        ventnaPrincipal.centrar();
    }

    public static void comenzar(){launch();}
}
