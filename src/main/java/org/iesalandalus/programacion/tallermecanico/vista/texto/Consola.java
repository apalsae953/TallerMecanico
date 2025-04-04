package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Consola {

    private static final String CADENA_FORMATO_FECHA ="dd/MM/yyyy";

    private Consola() {}

    public static void mostrarCabecera(String mensaje) {
        System.out.println(mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Menú de Opciones");
        for (Evento opcion : Evento.values()) {
            System.out.println(opcion);
        }
    }

    public static Evento elegirOpcion() {
        Evento opcion = null;
        do {
            try {
                opcion = Evento.get(leerEntero("\nElige un opción: "));
            } catch (IllegalArgumentException e) {
                System.out.printf("ERROR: %s%n", e.getMessage());
            }
        } while (opcion == null);
        return opcion;
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    public static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje) {
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        mensaje = String.format("%s (%s): ", mensaje, CADENA_FORMATO_FECHA);
        try {
            fecha = LocalDate.parse(leerCadena(mensaje), formatoFecha);
        } catch (DateTimeParseException e) {
            fecha = null;
        }
        return fecha;
    }

}