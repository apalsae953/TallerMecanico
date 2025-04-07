package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    private static final String FICHEROS_TRABAJOS = "";
    private static final DateTimeFormatter FORMATO_FECHA = null;
    private static final String RAIZ = "";
    private static final String TRABAJO = "";
    private static final String CLIENTE = "";
    private static final String VEHICULO = "";
    private static final String FECHA_INICIO = "";
    private static final String FECHA_FIN = "";
    private static final String HORAS = "";
    private static final String PRECIO_MATERIAL = "";
    private static final String TIPO = "";
    private static final String REVISION = "";
    private static final String MECANICO = "";
    private Trabajos instancia;
    private List<Trabajo> coleccionTrabajos;

    private Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    public Trabajos getInstancia() {
        return instancia;
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosClientes = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosClientes.add(trabajo);
            }
        }
        return trabajosClientes;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculos = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculos.add(trabajo);
            }
        }
        return trabajosVehiculos;
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
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

    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> estadisticasMensuales = new HashMap<>();
        estadisticasMensuales.put(TipoTrabajo.MECANICO, 0);
        estadisticasMensuales.put(TipoTrabajo.REVISION, 0);
        return estadisticasMensuales;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : coleccionTrabajos) {
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
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.anadirHoras(horas);
        return trabajoEncontrado;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"El vehículo no puede ser nulo.");
        Trabajo trabajoEncontrado = null;
        Iterator<Trabajo> iteratorTrabajos = coleccionTrabajos.iterator();
        while (iteratorTrabajos.hasNext() && trabajoEncontrado == null) {
            Trabajo trabajo = iteratorTrabajos.next();
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrada()) {
                trabajoEncontrado = trabajo;
            }
        }
        if ( trabajoEncontrado == null ){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.cerrar(fechaFin);
        return trabajoEncontrado;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return coleccionTrabajos.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.contains(trabajo)) {
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
        coleccionTrabajos.remove(trabajo);
    }
}
