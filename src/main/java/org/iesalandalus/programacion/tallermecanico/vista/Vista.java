package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Vista {
    GestorEventos getGestorEventos();

    void comenzar();

    void terminar();

    Cliente leerCliente();

    Cliente leerClienteDni();

    String leerNuevoNombre();

    String leerNuevoTelefono();

    Vehiculo leerVehiculo();

    Vehiculo leerVehiculoMatricula();

    Trabajo leerRevision();

    Trabajo leerMecanico();

    int leerHoras();

    float leerPrecioMaterial();

    LocalDate leerFechaCierre();

    void notificarResultado(Evento evento, String texto, boolean exito);

    void mostrarCliente(Cliente cliente);

    void mostrarVehiculo(Vehiculo vehiculo);

    void leerRevision(Trabajo trabajo);

    void mostrarClientes(List<Cliente> clientes);

    void mostrarVehiculo(List<Vehiculo> vehiculos);

    void mostrarVehiculos(List<Vehiculo> vehiculo);

    void mostrarTrabajos(List<Trabajo> trabajos);

    LocalDate leerMes();

    void mostrarEstadisticasMensuales(Map<TipoTrabajo,Integer> estadisticas);
}
