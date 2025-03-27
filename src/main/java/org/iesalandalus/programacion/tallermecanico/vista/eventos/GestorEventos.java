package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private Map<Evento, List<ReceptorEventos>> receptores;

    public GestorEventos(Evento... eventos){
        Objects.requireNonNull(eventos, "Se debe gestionar algún evento.");
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(eventos, "Te debes suscribir a algún evento.");
        Objects.requireNonNull(receptor, "El receptor no puede ser nulo.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.add(receptor);
        }
    }


    public void desuscribir(ReceptorEventos receptor, Evento... eventos){
        Objects.requireNonNull(eventos, "Te debes suscribir a algún evento.");
        Objects.requireNonNull(receptor, "El receptor no puede ser nulo.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.remove(receptor);
        }
    }

    public void notificar(Evento evento){

    }

}
