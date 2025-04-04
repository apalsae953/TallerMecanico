package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto implements Vista {

    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public GestorEventos getGestorEventos() {return gestorEventos;}

    @Override
    public void comenzar() {
        Evento opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Evento.SALIR);
    }

    private void ejecutar(Evento opcion) {
        Consola.mostrarCabecera(opcion.toString());
        gestorEventos.notificar(opcion);
    }

    @Override
    public void terminar() {
        Consola.mostrarCabecera("¡Hasta pronto!");
    }

    @Override
    public Cliente leerCliente(){
        String nombre = Consola.leerCadena("Dime tu nombre: ");
        String dni = Consola.leerCadena("Dime tu dni: ");
        String telefono = Consola.leerCadena("Dime tu teléfono: ");

        return new Cliente(nombre, dni, telefono);
    }

    @Override
    public Cliente leerClienteDni(){
        return Cliente.get(Consola.leerCadena("Dime tu dni: "));
    }

    @Override
    public String leerNuevoNombre(){
        return Consola.leerCadena("Dime el nuevo nombre: ");
    }

    @Override
    public String leerNuevoTelefono(){
        return Consola.leerCadena("Dime tu nuevo número de teléfono: ");
    }

    @Override
    public Vehiculo leerVehiculo(){
        String marca = Consola.leerCadena("Dime tu marca: ");
        String modelo = Consola.leerCadena("Dime tu modelo: ");
        String matricula = Consola.leerCadena("Dime tu matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    @Override
    public Vehiculo leerVehiculoMatricula(){
        return Vehiculo.get(Consola.leerCadena("Dime tu nueva matrícula: "));
    }

    @Override
    public Trabajo leerRevision(){
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = Consola.leerFecha("Dime la fecha de inicio: ");
        return  new Revision(cliente, vehiculo, fechaInicio);
    }

    @Override
    public Trabajo leerMecanico(){
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = Consola.leerFecha("Dime la fecha de inicio: ");
        return new Mecanico(cliente, vehiculo, fechaInicio);
    }

    @Override
    public int leerHoras(){
        return Consola.leerEntero("Dime las horas trabajadas: ");
    }

    @Override
    public float leerPrecioMaterial(){
        return Consola.leerReal("Dime el precio del material: ");
    }

    @Override
    public LocalDate leerFechaCierre(){
        return Consola.leerFecha("Dime la fecha de fin: ");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            Consola.mostrarCabecera("Operación exitosa: " + texto);
        } else {
            Consola.mostrarCabecera("Error: " + texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente){
        Consola.mostrarCabecera("Datos del cliente: " + cliente.toString());
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo){
        Consola.mostrarCabecera("Datos del vehículo: " + vehiculo.toString());
    }

    @Override
    public void leerRevision(Trabajo trabajo){
        Consola.mostrarCabecera("Datos del trabajo: " + trabajo.toString());
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes){
        Consola.mostrarCabecera("Listado de clientes:");
        for (Cliente cliente : clientes) {
            Consola.mostrarCabecera(cliente.toString());
        }
    }

    @Override
    public void mostrarVehiculo(List<Vehiculo> vehiculos){
        Consola.mostrarCabecera("Listado de vehículos:");
        for (Vehiculo vehiculo : vehiculos) {
            Consola.mostrarCabecera(vehiculo.toString());
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculo){
        Consola.mostrarCabecera("Listado de vehículos: ");
        for (Vehiculo vehiculo1 : vehiculo) {
            Consola.mostrarCabecera(vehiculo1.toString());
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos){
        Consola.mostrarCabecera("Listado de trabajos:");
        for (Trabajo trabajo : trabajos) {
            Consola.mostrarCabecera(trabajo.toString());
        }
    }
}
