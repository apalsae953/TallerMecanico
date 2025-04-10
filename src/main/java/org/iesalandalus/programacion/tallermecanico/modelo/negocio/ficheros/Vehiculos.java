package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    public List<Vehiculo> vehiculos;

    public Vehiculos() {
        vehiculos = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get(){
        return vehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehículo nulo.");
        if (vehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
        vehiculos.add(vehiculo);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        int indice = vehiculos.indexOf(vehiculo);
        if(indice != -1) {
            return vehiculos.get(indice);
        }
        return null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        if (!vehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
        vehiculos.remove(vehiculo);
    }
}
