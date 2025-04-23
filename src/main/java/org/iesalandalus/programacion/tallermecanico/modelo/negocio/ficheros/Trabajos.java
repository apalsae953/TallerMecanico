package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    private static final String FICHERO_TRABAJOS = String.format("%s%s%s", "ficheros", File.separator, "ficheroTexto.txt");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "Trabajos";
    private static final String TRABAJO = "Trabajos";
    private static final String CLIENTE = "Cliente";
    private static final String VEHICULO = "Vehículo";
    private static final String FECHA_INICIO = "Fecha Inicio";
    private static final String FECHA_FIN = "Fecha Fin";
    private static final String HORAS = "Horas";
    private static final String PRECIO_MATERIAL = "Precio Material";
    private static final String TIPO = "Tipo";
    private static final String REVISION = "Revisión";
    private static final String MECANICO = "Mecánico";
    private static Trabajos instancia;
    private List<Trabajo> coleccionTrabajos;

    private Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    public static
    Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    public void comenzar(){
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_TRABAJOS);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList alquileres = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0; i < alquileres.getLength(); i++) {
            Node trabajo = alquileres.item(i);
            try {
                if (trabajo.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getTrabajo((Element) trabajo));
                }
            } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException |
                     TallerMecanicoExcepcion e) {
                System.out.printf("Error al leer el trabajo %d. --> %s%n", i, e.getMessage());
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) throws OperationNotSupportedException, TallerMecanicoExcepcion {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(REVISION)) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        } else if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            if (elemento.hasAttribute(PRECIO_MATERIAL)) {
                ((Mecanico) trabajo).anadirPrecioMaterial(Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL)));
            }
        }
        if (elemento.hasAttribute(HORAS) && trabajo != null) {
            int horas = Integer.parseInt(elemento.getAttribute(HORAS));
            trabajo.anadirHoras(horas);
        }
        if (elemento.hasAttribute(FECHA_FIN) && trabajo != null) {
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN), FORMATO_FECHA);
            trabajo.cerrar(fechaFin);
        }
        return trabajo;
    }

    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_TRABAJOS);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Trabajo trabajo : coleccionTrabajos) {
                Element elemento = getElemento(documentoXml, trabajo);
                documentoXml.getDocumentElement().appendChild(elemento);
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Trabajo trabajo) {
        Element elementoTrabajo = documentoXml.createElement(TRABAJO);
        elementoTrabajo.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        elementoTrabajo.setAttribute(VEHICULO, trabajo.getVehiculo().matricula());
        elementoTrabajo.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        if (trabajo.getFechaFin() != null) {
            elementoTrabajo.setAttribute(FECHA_FIN, trabajo.getFechaFin().format(FORMATO_FECHA));
        }
        if (trabajo.getHoras() != 0) {
            elementoTrabajo.setAttribute(HORAS, String.format("%d", trabajo.getHoras()));
        }
        if (trabajo instanceof Revision) {
            elementoTrabajo.setAttribute(TIPO, REVISION);
        } else if (trabajo instanceof Mecanico mecanico) {
            elementoTrabajo.setAttribute(TIPO, MECANICO);
            if (mecanico.getPrecioMaterial() != 0) {
                elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.format(Locale.US, "%f", mecanico.getPrecioMaterial()));
            }
        }
        return elementoTrabajo;
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosClientes = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosClientes.add(trabajo);
            }
        }
        return trabajosClientes;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculos = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculos.add(trabajo);
            }
        }
        return trabajosVehiculos;
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        Map<TipoTrabajo,Integer> estadisticasMensuales = new HashMap<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getFechaInicio().getMonth().equals(mes.getMonth())) {
                TipoTrabajo tipoTrabajo = TipoTrabajo.get(trabajo);
                if (estadisticasMensuales.containsKey(tipoTrabajo)) {
                    estadisticasMensuales.put(tipoTrabajo, estadisticasMensuales.get(tipoTrabajo) + 1);
                } else {
                    estadisticasMensuales.put(tipoTrabajo, 1);
                }
            }
        }
        return estadisticasMensuales;
    }

    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> estadisticasMensuales = new HashMap<>();
        estadisticasMensuales.put(TipoTrabajo.MECANICO, 0);
        estadisticasMensuales.put(TipoTrabajo.REVISION, 0);
        return estadisticasMensuales;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar una revisión nula.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente) && !trabajo.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
            }
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
            }
            if (trabajo.getCliente().equals(cliente) && trabajo.estaCerrada() && !trabajo.getFechaFin().isBefore(fechaInicio)) {
                throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
            }
            if (trabajo.getVehiculo().equals(vehiculo) && trabajo.estaCerrada() && !trabajo.getFechaFin().isBefore(fechaInicio)) {
                throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.anadirHoras(horas);
        return trabajoEncontrado;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"El vehículo no puede ser nulo.");
        Trabajo trabajoEncontrado = null;
        Iterator<Trabajo> iteratorTrabajos = coleccionTrabajos.iterator();
        while (iteratorTrabajos.hasNext() && trabajoEncontrado == null) {
            Trabajo trabajo = iteratorTrabajos.next();
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrada()) {
                trabajoEncontrado = trabajo;
            }
        }
        if ( trabajoEncontrado == null ){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new OperationNotSupportedException("No se puede añadir precio al material para este tipo de trabajos.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        trabajoEncontrado.cerrar(fechaFin);
        return trabajoEncontrado;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return coleccionTrabajos.get(indice);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion, OperationNotSupportedException {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.contains(trabajo)) {
            throw new OperationNotSupportedException("No existe ningún trabajo igual.");
        }
        coleccionTrabajos.remove(trabajo);
    }
}
