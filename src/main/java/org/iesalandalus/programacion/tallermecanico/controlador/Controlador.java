package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.VistaTexto;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

public class Controlador {

    private Modelo modelo;
    private VistaTexto vista;

    public Controlador(Modelo modelo, VistaTexto vista) {
        if (modelo == null || vista == null) {
            throw new NullPointerException("El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }

    public void actualizar(Evento evento) {
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> modelo.insertar(vista.obtenerCliente());
                case INSERTAR_VEHICULO -> modelo.insertar(vista.obtenerVehiculo());
                case INSERTAR_REVISION -> modelo.insertar(vista.obtenerTrabajo());
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.obtenerCliente()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.obtenerVehiculo()));
                case BUSCAR_REVISION -> vista.mostrarTrabajo(modelo.buscar(vista.obtenerTrabajo()));
                case BORRAR_CLIENTE -> modelo.borrar(vista.obtenerCliente());
                case BORRAR_VEHICULO -> modelo.borrar(vista.obtenerVehiculo());
                case BORRAR_REVISION -> modelo.borrar(vista.obtenerTrabajo());
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_REVISIONES -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_REVISIONES_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.obtenerCliente()));
                case LISTAR_REVISIONES_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.obtenerVehiculo()));
                case ANADIR_HORAS_REVISION -> modelo.anadirHoras(vista.obtenerTrabajo(), vista.obtenerHoras());
                case ANADIR_PRECIO_MATERIAL_REVISION ->
                        modelo.anadirPrecioMaterial(vista.obtenerTrabajo(), vista.obtenerPrecioMaterial());
                case MODIFICAR_CLIENTE -> {
                    Cliente cliente = vista.obtenerCliente();
                    String nombre = vista.obtenerNombre();
                    String telefono = vista.obtenerTelefono();
                    modelo.modificar(cliente, nombre, telefono);
                }
                case CERRAR_REVISION -> modelo.cerrar(vista.obtenerTrabajo(), vista.obtenerFechaFin());
                case SALIR -> {
                    terminar();
                    vista.mostrarMensaje("Aplicación finalizada.");
                }
                default -> vista.mostrarMensaje("Evento no reconocido.");
            }
        } catch (TallerMecanicoExcepcion e) {
            vista.mostrarMensaje("Error: " + e.getMessage());
        }
    }
}

