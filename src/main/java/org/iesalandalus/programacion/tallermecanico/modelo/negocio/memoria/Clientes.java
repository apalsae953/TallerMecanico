package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHEROS_CLIENTES = "";
    private static final String RAIZ = "";
    private static final String CLIENTE = "";
    private static final String NOMBRE = "";
    private static final String DNI = "";
    private static final String TELEFONO = "";
    public List<Cliente> clientes;

    public Clientes() {
        clientes = new ArrayList<>();
    }

    @Override
    public List<Cliente> get() {
        return clientes;
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"No se puede insertar un cliente nulo.");
        if (clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        clientes.add(cliente);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        if (cliente == null) {
            throw new NullPointerException("No se puede modificar un cliente nulo.");
        }
        boolean modificado = false;

        Cliente clienteExistente = buscar(cliente);
        if (clienteExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
        if (nombre != null) {
            if (nombre.isBlank()) {
                throw new TallerMecanicoExcepcion("El nombre no puede estar en blanco.");
            }
            if (!nombre.equals(cliente.getNombre())) {
                clienteExistente.setNombre(nombre);
                modificado = true;
            }
        }
        if (!clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        if (telefono != null) {
            if (telefono.isBlank()) {
                throw new TallerMecanicoExcepcion("El telefono no puede estar en blanco");
            }
            if (!telefono.equals(cliente.getTelefono())) {
                clienteExistente.setTelefono(telefono);
                modificado = true;
            }
        }
        return modificado;

    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente,"No se puede buscar un cliente nulo.");
        int indice = clientes.indexOf(cliente);
        if(indice != -1) {
            return clientes.get(indice);
        }
        return null;

    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente,"No se puede borrar un cliente nulo.");
        if (!clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
        clientes.remove(cliente);}
}
