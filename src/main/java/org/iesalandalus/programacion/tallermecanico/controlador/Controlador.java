package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;

public class Controlador implements IControlador {

    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null || vista == null) {
            throw new NullPointerException("El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
        vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> modelo.insertar(vista.leerCliente());
                case INSERTAR_VEHICULO -> modelo.insertar(vista.leerVehiculo());
                case INSERTAR_REVISION -> modelo.insertar(vista.leerRevision());
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerCliente()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculo()));
                case BUSCAR_TRABAJO -> vista.leerRevision(modelo.buscar(vista.leerRevision()));
                case BORRAR_CLIENTE -> modelo.borrar(vista.leerCliente());
                case BORRAR_VEHICULO -> modelo.borrar(vista.leerVehiculo());
                case BORRAR_TRABAJO -> modelo.borrar(vista.leerRevision());
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerCliente()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculo()));
                case ANADIR_HORAS_TRABAJO -> modelo.anadirHoras(vista.leerRevision(), vista.leerHoras());
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> modelo.anadirPrecioMaterial(vista.leerRevision(), vista.leerPrecioMaterial());
                case MODIFICAR_CLIENTE -> {
                    Cliente cliente = vista.leerCliente();
                    String nombre = vista.leerNuevoNombre();
                    String telefono = vista.leerNuevoTelefono();
                    modelo.modificar(cliente, nombre, telefono);
                }
                case CERRAR_TRABAJO -> modelo.cerrar(vista.leerRevision(), vista.leerFechaCierre());
                case ESTADISTICAS_MENSUALES -> modelo.getEstadisticasMensuales((vista.leerMes()));
                case SALIR -> terminar();
            }
        } catch (OperationNotSupportedException | TallerMecanicoExcepcion e) {
            throw new RuntimeException(e);
        }
    }
}
