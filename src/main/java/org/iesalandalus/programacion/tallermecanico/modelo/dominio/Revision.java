package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 0;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {

    }

    public Revision(Revision revision) {

    }

    @Override
    public float getPrecioEspecifico() {
        return ;
    }


    @Override
    public String toString() {
        String cadenaFechaFin = fechaFin == null ? "" : fechaFin.format(FORMATO_FECHA);
        String cadenaPrecio = estaCerrada() ? String.format(", %.2f € total", getPrecioEspecifico()) : "";
        return String.format("%s - %s: (%s - %s), %d horas, %.2f € en material%s", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), cadenaFechaFin, horas, precioMaterial, cadenaPrecio);
    }
}