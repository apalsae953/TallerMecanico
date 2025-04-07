package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
    INSERTAR_CLIENTE(11, "Insertar cliente"),
    BUSCAR_CLIENTE(21, "Buscar cliente"),
    BORRAR_CLIENTE(31, "Borrar cliente"),
    LISTAR_CLIENTES(41, "Listar clientes"),
    MODIFICAR_CLIENTE(60, "Modificar cliente"),
    INSERTAR_VEHICULO(12, "Insertar vehiculo"),
    BUSCAR_VEHICULO(22, "Buscar vehiculo"),
    BORRAR_VEHICULO(32, "Borrar vehiculo"),
    LISTAR_VEHICULOS(42, "Listar vehiculos"),
    INSERTAR_REVISION(13, "Insertar revision"),
    INSERTAR_MECANICO(23, "Insertar mecánico"),
    BUSCAR_TRABAJO(24, "Buscar trabajo"),
    BORRAR_TRABAJO(33, "Borrar trabajo"),
    LISTAR_TRABAJOS(43, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(44, "Listar trabajos de clientes"),
    LISTAR_TRABAJOS_VEHICULO(45, "Listar trabajos de vehiculos"),
    ANADIR_HORAS_TRABAJO(51, "Añadir horas de revisión"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(52, "Añadir precio de material de trabajo"),
    CERRAR_TRABAJO(70, "Cerrar trabajo"),
    ESTADISTICAS_MENSUALES(80, "Estadísticas mensuales"),
    SALIR(0, "Salir");

    private int codigo;
    private String texto;
    private static Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : Evento.values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private Evento(int codigo, String texto) {
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
