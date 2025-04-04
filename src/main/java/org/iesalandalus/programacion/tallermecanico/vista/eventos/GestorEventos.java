package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private final Map<Evento, List<ReceptorEventos>> receptores  = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos){
        Objects.requireNonNull(eventos, "Se debe gestionar algún evento.");
        for (Evento evento : eventos) {
            receptores.put(evento,new ArrayList<>());
        }
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

    public void notificar(Evento evento) {
        List<ReceptorEventos> listaReceptores = receptores.get(evento);
        if (listaReceptores != null) {
            for (ReceptorEventos receptor : listaReceptores) {
                receptor.actualizar(evento);
            }
        }
    }

}
