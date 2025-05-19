package org.iesalandalus.programacion.tallermecanico.vista.ventanas;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores.InsertarVehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VistaVentanas implements Vista {

    private VentanaPrincipal ventanaPrincipal;

    private static VistaVentanas instancia;
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    private VistaVentanas(){}

    public static VistaVentanas getInstancia() {
        if (instancia == null) {
            instancia = new VistaVentanas();
        }
        return instancia;
    }

    public void inicializar() {
        ventanaPrincipal = (VentanaPrincipal) Controladores.get("/vistas/VentanaPrincipal.fxml", "Taller Mecánico",null);
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    @Override
    public void terminar() {}

    @Override
    public Cliente leerCliente() {
        return null;
    }

    @Override
    public Cliente leerClienteDni() {
        return null;
    }

    @Override
    public String leerNuevoNombre() {
        return "";
    }

    @Override
    public String leerNuevoTelefono() {
        return "";
    }

    @Override
    public Vehiculo leerVehiculo() {
        InsertarVehiculo insertarVehiculo = (InsertarVehiculo) Controladores.get("/vistas/InsertarVehiculo.fxml","Insertar Vehículo", ventanaPrincipal.getEscenario());
        return insertarVehiculo.getVehiculo();
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return null;
    }

    @Override
    public Trabajo leerRevision() {
        return null;
    }

    @Override
    public Trabajo leerMecanico() {
        return null;
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return null;
    }

    @Override
    public int leerHoras() {
        return 0;
    }

    @Override
    public float leerPrecioMaterial() {
        return 0;
    }

    @Override
    public LocalDate leerFechaCierre() {
        return null;
    }

    @Override
    public LocalDate leerMes() {
        return null;
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        if (exito) {
            Dialogos.mostrarDialogoInformacion("Correcto", "Ha sido todo un éxito", ventanaPrincipal.getEscenario());
        } else {
            Dialogos.mostrarDialogoError("Error", "No has introducido los datos correctamente", ventanaPrincipal.getEscenario());
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {

    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {

    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {

    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {

    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {

    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajoes) {

    }

    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {

    }
}
