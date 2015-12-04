/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vistas.Vista_busqueda;
import Modelo.DAO.ConsultasMongoDAO;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import org.bson.Document;

/**
 *
 * @author inftel08
 */
public class ControladorBusquedas {

    private Vector<String> atributos_ppales;
    private Vista_busqueda miVista;
    private int num_foto;
    private Vector<Icon> vectorImagenes;
    private Vector<String> vectorRutas;
    private int tamano;
    private int origen;
    private String EtiquetaMarca = "Exif IFD0.Make";
    private String EtiquetaModelo = "Exif IFD0.Model";

    private static String nombre;
    private static String ruta;
    private static String extension;
    private static String nombre_fichero;

    public static String getNombre() {
        return nombre;
    }

    public static String getRuta() {
        return ruta;
    }

    public static String getExtension() {
        return extension;
    }

    public ControladorBusquedas(Vista_busqueda miVista) {

        this.miVista = miVista;
        atributos_ppales = new Vector<>();

        //añadir valores
        atributos_ppales.add("nombre_foto");
        atributos_ppales.add("tamaño");
        atributos_ppales.add("extension");
        atributos_ppales.add("nombre_ruta");

        DefaultComboBoxModel model = new DefaultComboBoxModel(atributos_ppales);
        miVista.getjComboBoxCamposPpales().setModel(model);

        DefaultComboBoxModel model2 = new DefaultComboBoxModel(ConsultasMongoDAO.consultarCampoMarcaEtiquetaMetadatos(EtiquetaMarca));
        miVista.getjComboMarca().setModel(model2);

    }

    public void pintar(DBCursor find) {

        vectorImagenes = new Vector<>();
        vectorRutas = new Vector<>();

        while (find.hasNext()) {
            
            System.out.println("holaaaaaa");
            DBObject next = find.next();

            nombre = (String) next.get("nombre_foto");
            ruta = (String) next.get("nombre_ruta");
            extension = (String) next.get("extension");
            nombre_fichero = ruta + '/' + nombre + '.' + extension;
            //System.out.println(nombre_fichero);

            ImageIcon img = new ImageIcon(nombre_fichero);

            Icon icono = new ImageIcon(img.getImage().getScaledInstance(miVista.getjLabelFoto().getWidth(), miVista.getjLabelFoto().getHeight(), Image.SCALE_DEFAULT));
            vectorImagenes.add(icono);
            vectorRutas.add(nombre_fichero);

        }

        tamano = vectorImagenes.size();
        origen = 0;
        //pinta el primero
        num_foto = 0;
        miVista.getjPosImagen().setText((num_foto + 1) + "/" + tamano);
        miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
        miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
    }

    public void enviarConsultaSimple() {

        String valor = miVista.getCampoValorSimple().getText().toUpperCase();
        String clave = miVista.getjComboBoxCamposPpales().getSelectedItem().toString();
        DBCursor find = null;

        switch (clave) {

            case "nombre_foto":
                find = ConsultasMongoDAO.consultarUnCampoParcial(clave, valor);
                break;

            case "tamaño":
                find = ConsultasMongoDAO.consultarValoresMayores(clave, valor);
                break;

            case "extension":
                find = ConsultasMongoDAO.consultarUnCampoPrincipal(clave, valor);
                break;

            case "nombre_ruta":

                find = ConsultasMongoDAO.consultarUnCampoParcial(clave, valor);
                break;
        }

        pintar(find);

    }

    public void siguienteFoto() {

        if (num_foto < tamano - 1) {
            num_foto++;
            miVista.getjPosImagen().setText((num_foto + 1) + "/" + tamano);
            miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
            miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
        }

    }

    public void anteriorFoto() {

        if (num_foto > origen) {
            num_foto--;
            miVista.getjPosImagen().setText((num_foto + 1) + "/" + tamano);
            miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
            miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
        }
    }

    public void recuperarModeloCamara() {

        Vector<String> pi = ConsultasMongoDAO.consultarCampoModeloEtiquetaMetadatos(EtiquetaMarca, miVista.getjComboMarca().getSelectedItem().toString());
        DefaultComboBoxModel model2 = new DefaultComboBoxModel(pi);
        miVista.getjComboModelo().setModel(model2);

    }

    public void ejecutarBusquedaFotoCamara() {

        DBCursor find = ConsultasMongoDAO.obtenerFotosCamara(EtiquetaMarca, miVista.getjComboMarca().getSelectedItem().toString(), EtiquetaModelo, miVista.getjComboModelo().getSelectedItem().toString());
        pintar(find);

    }

    public void busquedaMetadatos() {

        int num_campos = 0;
        String directorio = null, etiqueta = null, valor = null;
        DBCursor find = null;

        if (miVista.getCampoDirectorio().getText().length()!=0) {
            directorio = miVista.getCampoDirectorio().getText();
            System.out.println(directorio);
            num_campos++;
        }

        if (miVista.getCampoEtiqueta().getText().length()!=0) {
            etiqueta = miVista.getCampoEtiqueta().getText();
            System.out.println(etiqueta);
            num_campos++;
        }

        if (miVista.getCampoValor().getText().length()!=0) {
            valor = miVista.getCampoValor().getText();
            System.out.println(valor);
            num_campos++;
        }

        switch (num_campos) {

            case 1:
                System.out.println("eeeeei directorio");
                find = ConsultasMongoDAO.consultarPorClave(directorio);
                break;

            case 2:
                System.out.println("eeeeei etiqueta");
                String clave1 = directorio + '.' + etiqueta;
                find = ConsultasMongoDAO.consultarPorClave(clave1);
                break;

            case 3:
                System.out.println("eeeeei valor");
                String clave2 = directorio + '.' + etiqueta;
                find = ConsultasMongoDAO.consultarUnCampoPrincipal(clave2, valor);
                break;

        }
        
        if (find!=null) {
            pintar(find);
        }
        

    }
}
