package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;


public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 0;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    @Override
    public float getPrecioEspecifico() {
        return FACTOR_HORA * getHoras();
    }


    @Override
    public String toString() {
        return "Revision{}";
    }
}