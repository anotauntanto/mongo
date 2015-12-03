/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vistas.Vista_busqueda;
import Modelo.DAO.ConsultasMongoDAO;
import com.mongodb.Block;
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

    }

    public void enviarConsultaSimple() {

        String valor = miVista.getCampoValorSimple().getText();
        String clave = miVista.getjComboBoxCamposPpales().getSelectedItem().toString();
        FindIterable<Document> find = ConsultasMongoDAO.consultarUnCampoPrincipal(clave, valor);

        vectorImagenes = new Vector<>();
        vectorRutas = new Vector<>();

        find.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                String nombre = (String) document.get("nombre_foto");
                String ruta = (String) document.get("nombre_ruta");
                String extension = (String) document.get("extension");
                String nombre_fichero = ruta + '/' + nombre + '.' + extension;
                //System.out.println(nombre_fichero);

                ImageIcon img = new ImageIcon(nombre_fichero);

                Icon icono = new ImageIcon(img.getImage().getScaledInstance(miVista.getjLabelFoto().getWidth(), miVista.getjLabelFoto().getHeight(), Image.SCALE_DEFAULT));
                vectorImagenes.add(icono);
                vectorRutas.add(nombre_fichero);

            }
        });

        tamano = vectorImagenes.size();
        origen = 0;
        //pinta el primero
        num_foto = 0;
        miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
        miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
    }

    public void siguienteFoto() {

        if (num_foto < tamano) {
            num_foto++;
            miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
            miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
        }

    }

    public void anteriorFoto() {

        if (num_foto > origen) {
            num_foto--;
            miVista.getjLabelFoto().setIcon(vectorImagenes.get(num_foto));
            miVista.getjLabelRutaFoto().setText(vectorRutas.get(num_foto));
        }
    }
}
