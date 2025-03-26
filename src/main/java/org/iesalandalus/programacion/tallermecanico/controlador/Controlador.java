package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.VistaTexto;

public class Controlador {

    private Modelo modelo;
    private VistaTexto vista;

    public Controlador(Modelo modelo, VistaTexto vista) {
        if (modelo == null || vista == null) {
            throw new NullPointerException("El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }

    public void actualizar (Evento evento){

    }
}

