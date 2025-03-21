package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Objects;

public class Mecanico extends Trabajo{
    private static final float FACTOR_HORA = 0;
    private static final float FACTOR_PRECIO_MATERIAL = 0;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
        precioMaterial = 0;
    }


    public Mecanico(Mecanico mecanico) {
        super(mecanico);
    }

    public float getPrecioMaterial(){
        return FACTOR_PRECIO_MATERIAL * precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial){
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }

    public float getPrecioEspecifico(){
        return FACTOR_HORA *  + FACTOR_PRECIO_MATERIAL * getPrecioMaterial();
    }

    @Override
    public String toString() {
        return String.format("[precioMaterial=%s]", precioMaterial);
    }
}
