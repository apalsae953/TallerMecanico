package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;

public class ModeloCascada implements Modelo {
    private IVehiculos vehiculos;
    private IClientes clientes;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "La factoría de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        clientes = clientes.getInstancia();
        vehiculos = vehiculos.getInstancia();
        trabajos = trabajos.getInstancia();
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
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        return trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        return trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        List<Trabajo> trabajosCliente = trabajos.get(cliente);
        for (Trabajo trabajo : trabajosCliente) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        List<Trabajo> trabajosVehiculo = trabajos.get(vehiculo);
        for (Trabajo trabajo : trabajosVehiculo) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copiaClientes.add(new Cliente(cliente));
        }
        return copiaClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {return new ArrayList<>(vehiculos.get());}

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

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        List<Trabajo> coleccionTrabajos = new ArrayList<>(trabajos.get());
        Map<TipoTrabajo,Integer> estadisticasMensuales = new HashMap<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth())) {
                TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
                if (estadisticasMensuales.containsKey(tipoTrabajo)) {
                    estadisticasMensuales.put(tipoTrabajo, estadisticasMensuales.get(tipoTrabajo) + 1);
                } else {
                    estadisticasMensuales.put(tipoTrabajo, 1);
                }
            }
        }
        return estadisticasMensuales;
    }
}