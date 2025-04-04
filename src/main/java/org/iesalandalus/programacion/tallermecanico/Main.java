package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VistaTexto vista = new VistaTexto();
        Modelo modelo = new Modelo() {
            @Override
            public void comenzar() {

            }

            @Override
            public void terminar() {

            }

            @Override
            public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {

            }

            @Override
            public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {

            }

            @Override
            public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {

            }

            @Override
            public Cliente buscar(Cliente cliente) {
                return null;
            }

            @Override
            public Vehiculo buscar(Vehiculo vehiculo) {
                return null;
            }

            @Override
            public Trabajo buscar(Trabajo trabajo) {
                return null;
            }

            @Override
            public boolean modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
                return false;
            }

            @Override
            public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion, OperationNotSupportedException {
                return null;
            }

            @Override
            public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
                return null;
            }

            @Override
            public void borrar(Cliente cliente) throws TallerMecanicoExcepcion, OperationNotSupportedException {

            }

            @Override
            public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion, OperationNotSupportedException {

            }

            @Override
            public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion, OperationNotSupportedException {

            }

            @Override
            public List<Cliente> getClientes() {
                return List.of();
            }

            @Override
            public List<Vehiculo> getVehiculos() {
                return List.of();
            }

            @Override
            public List<Trabajo> getTrabajos() {
                return List.of();
            }

            @Override
            public List<Trabajo> getTrabajos(Cliente cliente) {
                return List.of();
            }

            @Override
            public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
                return List.of();
            }
        };
        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
    }
}