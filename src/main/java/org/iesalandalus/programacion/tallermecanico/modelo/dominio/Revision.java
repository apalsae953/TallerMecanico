package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision extends Trabajo {

    private static final float PRECIO_HORA = 30.0F;
    private static final float PRECIO_MATERIAL = 1.5F;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Revision(Revision revision) {

        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = new Cliente (revision.cliente);
        this.vehiculo = revision.vehiculo;
        this.fechaInicio = revision.fechaInicio;
        this.fechaFin = revision.fechaFin;
        this.horas = revision.horas;
        this.precioMaterial = revision.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return horas * PRECIO_HORA + PRECIO_DIA()  + getPrecioMaterial() * PRECIO_MATERIAL;
    }


    @Override
    public String toString() {
        String cadenaFechaFin = fechaFin == null ? "" : fechaFin.format(FORMATO_FECHA);
        String cadenaPrecio = estaCerrada() ? String.format(", %.2f € total", getPrecioEspecifico()) : "";
        return String.format("%s - %s: (%s - %s), %d horas, %.2f € en material%s", cliente, vehiculo, fechaInicio.format(FORMATO_FECHA), cadenaFechaFin, horas, precioMaterial, cadenaPrecio);
    }
}