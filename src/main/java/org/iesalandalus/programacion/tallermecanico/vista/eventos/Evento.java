package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(11, "Insertar cliente"),
    INSERTAR_VEHICULO(12, "Insertar vehiculo"),
    INSERTAR_REVISION(13, "Insertar revision"),
    BUSCAR_CLIENTE(21, "Buscar cliente"),
    BUSCAR_VEHICULO(22, "Buscar vehiculo"),
    BUSCAR_REVISION(23, "Buscar revision"),
    BORRAR_CLIENTE(31, "Borrar cliente"),
    BORRAR_VEHICULO(32, "Borrar vehiculo"),
    BORRAR_REVISION(33, "Borrar revision"),
    LISTAR_CLIENTES(41, "Listar clientes"),
    LISTAR_VEHICULOS(42, "Listar vehiculos"),
    LISTAR_REVISIONES(43, "Listar revisiones"),
    LISTAR_REVISIONES_CLIENTE(44, "Listar revisiones de clientes"),
    LISTAR_REVISIONES_VEHICULO(45, "Listar revisiones de vehiculos"),
    ANADIR_HORAS_REVISION(51, "Añadir horas de revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(52, "Añadir precio de material de revisión"),
    MODIFICAR_CLIENTE(60, "Modificar cliente"),
    CERRAR_REVISION(70, "Cerrar revisión"),
    SALIR(0, "Salir");

    private int codigo;
    private String texto;
    private static Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : Evento.values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo){
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("El código no es correcto.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return String.format("%d.- %s", codigo, texto);
    }
}
