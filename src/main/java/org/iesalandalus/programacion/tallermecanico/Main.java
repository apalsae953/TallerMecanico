package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.VistaTexto;

public class Main {
    public static void main(String[] args) {
        VistaTexto vista = new VistaTexto();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }
}