package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private List<Vehiculo> coleccionVehiculos;
    private static Vehiculos instancia;
    private static final String FICHERO_VEHICULOS = String.format("%s%s%s", "ficheros", File.separator, "ficheroTexto.txt");
    private static final String RAIZ = "Vehículos";
    private static final String VEHICULO = "Vehículo";
    private static final String MARCA = "Marca";
    private static final String MODELO = "Modelo";
    private static final String MATRICULA = "Matricula";

    public Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    public static Vehiculos getInstancia(){
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        if (documentoXml != null) {
            procesarDocumentoXml(documentoXml);
            System.out.printf("Fichero %s leído correctamente.%n", FICHERO_VEHICULOS);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
        for (int i = 0; i < vehiculos.getLength(); i++) {
            Node cliente = vehiculos.item(i);
            try {
                if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                    insertar(getVehiculos((Element) cliente));
                }
            } catch (IllegalArgumentException | NullPointerException | TallerMecanicoExcepcion e) {
                System.out.printf("Error al leer el vehículo %d. --> %s%n", i, e.getMessage());
            }
        }
    }

    private Vehiculo getVehiculos(Element elemento){
        String matricula = elemento.getAttribute(MATRICULA);
        String marca = elemento.getAttribute(MARCA);
        String modelo = elemento.getAttribute(MODELO);
        return new Vehiculo(matricula, marca, modelo);
    }

    public void terminar(){
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_VEHICULOS);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null) {
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Vehiculo vehiculo : coleccionVehiculos) {
                Element elemento = getElemento(documentoXml, vehiculo);
                documentoXml.getDocumentElement().appendChild(elemento);
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
        Element elemento = documentoXml.createElement(MATRICULA);
        elemento.setAttribute(MARCA, vehiculo.marca());
        elemento.setAttribute(MODELO, vehiculo.modelo());
        return elemento;
    }

    @Override
    public List<Vehiculo> get(){
        return coleccionVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
        coleccionVehiculos.add(vehiculo);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculos.indexOf(vehiculo);
        if(indice != -1) {
            return coleccionVehiculos.get(indice);
        }
        return null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        if (!coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
        coleccionVehiculos.remove(vehiculo);
    }
}
