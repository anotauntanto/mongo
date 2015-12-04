/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Clases.CoordenadaUtils;
import Vistas.Vista_busqueda;
import Modelo.DAO.ConsultasMongoDAO;
import Vistas.VistaMapa;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import maps.java.Geocoding;
import maps.java.StaticMaps;
import org.bson.Document;

/**
 *
 * @author inftel08
 */
public class ControladorBusquedas {

    private Vector<String> atributos_ppales;
    private Vista_busqueda miVista;
    private VistaMapa miVistaMapa;

    private int num_foto;
    private Vector<Icon> vectorImagenes;
    private Vector<String> vectorRutas;
    private Vector<DBObject> vectorResultados;
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

    public ControladorBusquedas(Vista_busqueda miVista, VistaMapa mapa) {
        this.miVistaMapa = mapa;
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
        vectorResultados = new Vector<>();

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
            vectorResultados.add(next);

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

    public DBObject getFotoActual() {
        return vectorResultados.get(num_foto);
    }

    public void comprobarMapa() throws MalformedURLException {

        DBObject fotoActual = getFotoActual();
        String id_foto = fotoActual.get("_id").toString();

        System.out.println(id_foto);
        Vector<String> obtenerCoordenadas = ConsultasMongoDAO.obtenerCoordenadas(id_foto);

        if (obtenerCoordenadas.size() != 0) {
            miVistaMapa.setVisible(true);
            verMapa(obtenerCoordenadas.get(1), obtenerCoordenadas.get(0));
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

        if (miVista.getCampoDirectorio().getText().length() != 0) {
            directorio = miVista.getCampoDirectorio().getText();
            System.out.println(directorio);
            num_campos++;
        }

        if (miVista.getCampoEtiqueta().getText().length() != 0) {
            etiqueta = miVista.getCampoEtiqueta().getText();
            System.out.println(etiqueta);
            num_campos++;
        }

        if (miVista.getCampoValor().getText().length() != 0) {
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

        if (find != null) {
            pintar(find);
        }

    }

    public void obtenerFotoporFecha(String fecha) {
        DBCursor consultarUnCampoParcial = ConsultasMongoDAO.consultarFecha(fecha);
        pintar(consultarUnCampoParcial);
    }

    public void verMapa(String lon, String lat) throws MalformedURLException {
        StaticMaps ObjStatMap = new StaticMaps();

        //Cambiar a direcciones  "Madrid, Puerta del Sol"
        Geocoding ObjGeocod = new Geocoding();
        //ArrayList<String> resultadoCI=
        //57° 46' 32,57 lat
        //-5° 35' 59,65 lon
        String[] horaLat = lat.split("° ");
        String[] minutosLat = horaLat[1].split("' ");
        String[] segundosLat = minutosLat[1].split(",");
        String[] latitud = new String[3];
        latitud[0] = horaLat[0];
        latitud[1] = minutosLat[0];
        latitud[2] = segundosLat[0];

        String[] horaLon = lon.split("° ");
        String[] minutosLon = horaLon[1].split("' ");
        String[] segundosLon = minutosLon[1].split(",");
        String[] longitud = new String[3];
        longitud[0] = horaLon[0];
        longitud[1] = minutosLon[0];
        longitud[2] = segundosLon[0];

       // System.out.println("Lon: "+Integer.parseInt(horaLat[0])+" "+Integer.parseInt(minutosLat[0])+" "+Integer.parseInt(segundosLat[0]));
         /*
         double gradLat=Integer.parseInt(horaLat[0]);
         double minLat=Double.parseDouble(minutosLat[0])/60;
         double segLat;
         if (segundosLat.length > 1) {
         segLat=Double.parseDouble(segundosLat[0])/60;
         } else {
         segLat = 0.0;
         }
        
        
         double gradLon=Integer.parseInt(horaLon[0]);
         double minLon=Double.parseDouble(minutosLon[0])/60;
         double segLon;
         if (segundosLon.length > 1) {
         segLon=Double.parseDouble(segundosLon[0])/60;
         } else {
         segLon = 0.0;
         }*/
        //System.out.println("Lat: "+gradLat+" "+minutosLat+" "+segundosLat);
        //double LatResult= (double) Integer.parseInt(horaLat[0]) + 
        //        Integer.parseInt(minutosLat[0]) / 60 + 
        //        Integer.parseInt(segundosLat[0]) / 3600;
        /*double LatResult=gradLat+minLat+segLat;
         double LonResult=gradLon+minLon+segLon;*/
        double LatResult = CoordenadaUtils.toDecimalaUsar(latitud);
        double LonResult = CoordenadaUtils.toDecimalaUsar(longitud);

        System.out.println("Lat: " + LatResult);
        System.out.println("Lon: " + LonResult);

        Image resultadoMapa = null;
        try {
            resultadoMapa = ObjStatMap.getStaticMap(ObjGeocod.getAddress(LonResult, LatResult).get(0),
                    8, new Dimension(400, 400), 1, StaticMaps.Format.png, StaticMaps.Maptype.terrain);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ControladorMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Icon icono = new ImageIcon(resultadoMapa.getStatMap().getScaledInstance(miVista.getjLabel1().getWidth(), miVista.getjLabel1().getHeight(), Image.SCALE_DEFAULT));
        ImageIcon icon = new ImageIcon(resultadoMapa);
        miVistaMapa.getjLabelMapa().setIcon(icon);

         //ImageIcon img = new ImageIcon(nombre_fichero);
    }
}
