package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Trabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModeloCascada implements Modelo {
    private IVehiculos vehiculos;
    private IClientes clientes;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        comenzar();
    }

    @Override
    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        trabajos = new Trabajos();
    }

    @Override
    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajo = new Revision(cliente, vehiculo, trabajo.getFechaInicio());
        } else if (trabajo instanceof Mecanico) {
            trabajo = new Mecanico(cliente, vehiculo, trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado != null) ? new Cliente(encontrado) : null;
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "No existe un trabajo igual.");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        return clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        return trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        return trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosCliente = trabajos.get(cliente);
        for (Trabajo trabajo : trabajosCliente) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : trabajosVehiculo) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        return clientes.get().stream().map(Cliente::new).collect(Collectors.toList());
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copiaTrabajos.add(Trabajo.copiar(trabajo));
        }
        return copiaTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            trabajosCliente.add(Trabajo.copiar(trabajo));
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            trabajosVehiculos.add(Trabajo.copiar(trabajo));
        }
        return trabajosVehiculos;
    }
}
