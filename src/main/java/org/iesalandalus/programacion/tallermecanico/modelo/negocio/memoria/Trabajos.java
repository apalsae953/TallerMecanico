package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {

    private final List<Trabajo> trabajos;

    public Trabajos() {
        trabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(trabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosClientes = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosClientes.add(trabajo);
            }
        }
        return trabajosClientes;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculos = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculos.add(trabajo);
            }
        }
        return trabajosVehiculos;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        trabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getCliente().equals(cliente) && !trabajo.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
            }
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
            }
            if (trabajo.getCliente().equals(cliente) && trabajo.estaCerrada() && !trabajo.getFechaFin().isBefore(fechaInicio)) {
                throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
            }
            if (trabajo.getVehiculo().equals(vehiculo) && trabajo.estaCerrada() && !trabajo.getFechaFin().isBefore(fechaInicio)) {
                throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo operar sobre una revisión nula.");
        Trabajo trabajoExistente = getTrabajo(trabajo);
        if (trabajoExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        trabajoExistente.anadirHoras(horas);
        return trabajoExistente;
    }

    private Trabajo getTrabajo(Trabajo trabajo) {
        for (Trabajo tra : trabajos) {
            if (tra.equals(trabajo)) {
                return tra;
            }
        }
        return null;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo operar sobre una revisión nula.");
        Trabajo trabajoExistente = getTrabajo(trabajo);
        if (trabajoExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        return trabajoExistente;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo operar sobre una revisión nula.");
        Trabajo trabajoExistente = getTrabajo(trabajo);
        if (trabajoExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        trabajoExistente.cerrar(fechaFin);
        return trabajoExistente;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar una revisión nula.");
        return getTrabajo(trabajo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar una revisión nula.");
        Trabajo trabajoExistente = getTrabajo(trabajo);
        if (trabajoExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        trabajos.remove(trabajoExistente);
    }
}
