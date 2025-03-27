package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;

public interface ReceptorEventos extends IControlador {
    public void actualizar(Evento evento);
}
