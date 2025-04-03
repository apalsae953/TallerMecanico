package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.VistaTexto;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;

public class Controlador {

    private Modelo modelo;
    private VistaTexto vista;

    public Controlador(Modelo modelo, VistaTexto vista) {
        if (modelo == null || vista == null) {
            throw new NullPointerException("El modelo y la vista no pueden ser nulos.");
        }
        this.modelo = modelo;
        this.vista = vista;
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
                case INSERTAR_CLIENTE -> modelo.insertar(vista.leerCliente());
                case INSERTAR_VEHICULO -> modelo.insertar(vista.leerVehiculo());
                case INSERTAR_REVISION -> modelo.insertar(vista.leerRevision());
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerCliente()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculo()));
                case BUSCAR_REVISION -> vista.leerRevision(modelo.buscar(vista.leerRevision()));
                case BORRAR_CLIENTE -> modelo.borrar(vista.leerCliente());
                case BORRAR_VEHICULO -> modelo.borrar(vista.leerVehiculo());
                case BORRAR_REVISION -> modelo.borrar(vista.leerRevision());
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_REVISIONES -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_REVISIONES_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerCliente()));
                case LISTAR_REVISIONES_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculo()));
                case ANADIR_HORAS_REVISION -> modelo.anadirHoras(vista.leerRevision(), vista.leerHoras());
                case ANADIR_PRECIO_MATERIAL_REVISION -> modelo.anadirPrecioMaterial(vista.leerRevision(), vista.leerPrecioMaterial());
                case MODIFICAR_CLIENTE -> {
                    Cliente cliente = vista.leerCliente();
                    String nombre = vista.leerNuevoNombre();
                    String telefono = vista.leerNuevoTelefono();
                    modelo.modificar(cliente, nombre, telefono);
                }
                case CERRAR_REVISION -> modelo.cerrar(vista.leerRevision(), vista.leerFechaCierre());
                case SALIR -> terminar();

        }
    } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        } catch (TallerMecanicoExcepcion e) {
            throw new RuntimeException(e);
        }
    }
}
