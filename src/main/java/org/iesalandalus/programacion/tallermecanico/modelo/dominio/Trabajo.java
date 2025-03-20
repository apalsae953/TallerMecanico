package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Trabajo {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10.0F;
    protected Cliente cliente;
    protected Vehiculo vehiculo;
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected int horas;

    private Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    private Trabajo(Trabajo trabajo) {

    }
    public Trabajo copiar(Trabajo trabajo) {}
    public Trabajo get(Vehiculo vehiculo) {}

    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("El cliente no puede ser nulo.");
        }
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("El vehículo no puede ser nulo.");
        }
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new NullPointerException("La fecha de inicio no puede ser nula.");
        }
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean estaCerrada() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (fechaFin == null) {
            throw new NullPointerException("La fecha de fin no puede ser nula.");
        }
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("La revisión ya está cerrada.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        if (estaCerrada()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que la revisión está cerrada.");
        }
        this.horas += horas;
    }

    public abstract float getPrecioEspecifico();

    private int getDias() {
        if (fechaFin == null) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo) && Objects.equals(fechaInicio, revision.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fechaInicio);
    }
}