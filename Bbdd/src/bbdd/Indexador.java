/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import Modelo.Clases.Foto;
import Modelo.DAO.FotoDAO;
import Modelo.DAO.ValorDAO;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
//import com.adobe.xmp.*;
import com.drew.metadata.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import sun.applet.Main;

/**
 *
 * @author inftel08
 */
public class Indexador {

    public Indexador() {
    }

    public void listarDirectorio(File f) {

        File[] ficheros = f.listFiles();

        for (int i = 0; i < ficheros.length; i++) {
            if (ficheros[i].isDirectory()) {
                listarDirectorio(ficheros[i]);

            } else {
                try { //intenta abrir el fichero como una imagen

                    Image img = ImageIO.read(ficheros[i]);
                    if (img != null) {

                        String extension = null, tamano, nombre;
                        System.out.println("\n");
                        System.out.println("Foto " + ficheros[i]);

                        int pos_ext = (ficheros[i].getName()).lastIndexOf(".");
                        //if (pos_ext > 0) {
                        extension = ficheros[i].getName().substring(pos_ext + 1);
                        nombre = ficheros[i].getName().substring(0, pos_ext);
                        System.out.println("Nombre foto " + nombre);
                        //}
                        tamano = Long.toString(ficheros[i].length()) + 'B';
                        Foto photo = new Foto(nombre, tamano, extension);
                        FotoDAO.insertPhoto(photo, ficheros[i].getParent());

                        metadataImage(ficheros[i], photo.getIdFoto());
                    }

                } catch (IOException ex) { //sino salta la excepción
                    System.out.println("Entro alguna vez?");
                    Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void metadataImage(File file, int id_foto) {

        Metadata metadata = null;

        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //System.out.println(tag.getTagName());

                if (tag.getDescription().length() < 400) {
                    ValorDAO.insertMetadata(id_foto, tag.getDirectoryName(), tag.getTagName(), tag.getDescription());
                }

                System.out.println("Etiqueta completa: \n" + tag + "\n");/*
                 System.out.println ("Etiqueta: \n" + "directorio " +  tag.getDirectoryName() + " tag " + tag.getTagName() + " valor " + tag.getDescription()+ "\n");
                 */

            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Properties pr1 = new Properties();
        FileInputStream config = null;
        String name = "config.properties";

        //InputStream f = ClassLoader.getSystemResourceAsStream(Indexador.class.getPackage().getName() + "/" + name);
        InputStream f = ClassLoader.getSystemResourceAsStream("mgbproperties" + "/" + name);
        try {
            pr1.load(f);
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
        }

        File file = null;
        String value;
        Indexador x = new Indexador();
        Enumeration<String> propertyNames = (Enumeration<String>) pr1.propertyNames();

        while (propertyNames.hasMoreElements()) {
            value = propertyNames.nextElement();
            //System.out.println(value + " " + );
            file = new File(pr1.getProperty(value));
            x.listarDirectorio(file);

        }

        /*for (int i = 0; i<propertyNames.) {
            
         }
         while (pr1. {
         value = pr1.getProperty("directorio1");
         System.out.println(value);
         }
        
        

         /*try {
         config = new FileInputStream("./config.properties");
         } catch (FileNotFoundException ex) {
         Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         pr1.load(config);
         config.close();
         } catch (IOException ex) {
         Logger.getLogger(Indexador.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        //File file = new File(pr1.getProperty("directorio1"));
        /*
        

         ;*/
    }

}
