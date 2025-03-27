package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto {

    private GestorEventos gestorEventos;

    public GestorEventos getGestorEventos() {return gestorEventos;}

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
        Consola.elegirOpcion();
    }

    public void terminar() {
        Consola.mostrarCabecera("¡Hasta pronto!");
    }

    public Cliente leerCliente(){
        String nombre = Consola.leerCadena("Dime tu nombre: ");
        String dni = Consola.leerCadena("Dime tu dni: ");
        String telefono = Consola.leerCadena("Dime tu teléfono: ");

        return new Cliente(nombre, dni, telefono);
    }

    public Cliente leerClienteDni(){
        return Cliente.get(Consola.leerCadena("Dime tu dni: "));
    }

    public String leerNuevoNombre(){
        return Consola.leerCadena("Dime el nuevo nombre: ");
    }

    public String leerNuevoTelefono(){
        return Consola.leerCadena("Dime tu nuevo número de teléfono: ");
    }

    public Vehiculo leerVehiculo(){
        String marca = Consola.leerCadena("Dime tu marca: ");
        String modelo = Consola.leerCadena("Dime tu modelo: ");
        String matricula = Consola.leerCadena("Dime tu matrícula: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    public Vehiculo leerVehiculoMatricula(){
        return Vehiculo.get(Consola.leerCadena("Dime tu nueva matrícula: "));
    }

    public Trabajo leerRevision(){
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = Consola.leerFecha("Dime la fecha de inicio: ");
        return  new Revision(cliente, vehiculo, fechaInicio);
    }

    public Trabajo leerMecanico(){
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = Consola.leerFecha("Dime la fecha de inicio: ");
        return new Mecanico(cliente, vehiculo, fechaInicio);
    }

    public int leerHoras(){
        return Consola.leerEntero("Dime las horas trabajadas: ");
    }

    public float leerPrecioMaterial(){
        return Consola.leerReal("Dime el precio del material: ");
    }

    public LocalDate leerFechaCierre(){
        return Consola.leerFecha("Dime la fecha de fin: ");
    }

    public void notificarResultados(Evento evento, String texto, boolean exito) {

    }

}
