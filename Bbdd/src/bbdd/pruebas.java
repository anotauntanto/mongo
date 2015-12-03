/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import Modelo.Clases.Directorio;
import Modelo.Clases.Etiqueta;
import Modelo.Clases.Foto;
import Modelo.Clases.Jam;
import Modelo.Clases.Valor;
import Modelo.DAO.ConexionMongo;
import Modelo.DAO.DirectorioDAO;
import Modelo.DAO.EtiquetaDAO;
import Modelo.DAO.FotoDAO;
import Modelo.DAO.JamDAO;
import Modelo.DAO.RutaDAO;
import Modelo.DAO.ValorDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.text.MessageFormat.format;
import java.util.ArrayList;
import org.bson.Document;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author inftel08
 */
public class pruebas {

    private static List<Document> datosFotos;
    
    public static void insertarFotitos(List<Foto> listaFotos) {
        datosFotos = new ArrayList<>();

        MongoDatabase db = ConexionMongo.obtenerDataBase();
        String nomColeccion = ConexionMongo.getnomColeccion();
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

        Etiqueta tag;
        Document imagen = new Document();
        Document directorios = new Document();
        Document etiqueta_valor = new Document();
        Document metadata;
        

        for (Foto f : listaFotos) {
            
            imagen = new Document();
            directorios = new Document();
            etiqueta_valor = new Document();
            
            String dir = "", dir_ant = null;
            
            f.setRuta(RutaDAO.getStringRuta(f.getIdRuta()));
            List<Jam> listJam = JamDAO.listJamByIdPhoto(f.getIdFoto());
            
            
            for (Jam j: listJam) {
                dir_ant = dir;
                dir = j.getNombreDirectorio();
                
                System.out.println();
                if (dir_ant.equals(dir) || dir_ant.equals("")) {
                    System.out.println("Nombre etiqueta " + j.getNombreEtiqueta());
                    etiqueta_valor.append(j.getNombreEtiqueta(), j.getValor());
                    //dir_ant
                        
                } else {
                    System.out.println("entrooo" + dir_ant + " " + f.getNombreFoto());
                    directorios.append(dir_ant, asList(etiqueta_valor));
                    etiqueta_valor = new Document();
                    etiqueta_valor.append(j.getNombreEtiqueta(), j.getValor());
                    
                }
                
            }
            directorios.append(dir, asList(etiqueta_valor)); //guardo la última pq ya no puedo comparar con nada
            //System.out.println(dir);
            imagen.append("nombre_foto", f.getNombreFoto())
                    .append("tamaño", f.getTamano())
                    .append("extension", f.getExtension())
                    .append("nombre_ruta", f.getRuta()) //
                    .append("metadatos", asList(directorios));
            
            datosFotos.add(imagen);
        }
            
        
        db.getCollection("prueba").insertMany(datosFotos);

    }

    public static void insertarFotos(List<Foto> listaFotos, Map<Integer, Etiqueta> mapEtiquetas, Map<Integer, String> mapDirectorios, List<Valor> listaValores) {
        datosFotos = new ArrayList<>();

        MongoDatabase db = ConexionMongo.obtenerDataBase();
        String nomColeccion = ConexionMongo.getnomColeccion();
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

        Etiqueta tag;
        String dir = "", dir_ant = null;

        for (Foto f : listaFotos) {

            List<Integer> ld = DirectorioDAO.getAllIdDirectoriesByPhoto(f.getIdFoto());

            f.setRuta(RutaDAO.getStringRuta(f.getIdRuta()));
            Document imagen = new Document();
            //Document metadata = new Document();
            Document directorios = new Document();
            Document etiqueta_valor = new Document();

            for (Valor v : listaValores) {
                
                System.out.println("Id foto " + f.getIdFoto());

                if (f.getIdFoto() == v.getIdFoto()) {
                    dir_ant = dir;//mapDirectorios.get(tag.getIdDirectorio());

                    v.getIdEtiqueta();
                    tag = mapEtiquetas.get(v.getIdEtiqueta());
                    System.out.println(ld);
                    System.out.println(v.getIdEtiqueta());
                    
                    for (Integer i : ld) {
                        System.out.println("LLegaaa" + i);
                        if (i == tag.getIdDirectorio()) {
                            System.out.println("LLegaaa");
                            dir = mapDirectorios.get(tag.getIdDirectorio());
                            System.out.println("DIREEC "+dir);
                            etiqueta_valor.append(tag.getNombreEtiqueta(), v.getValor());
                        }
                    }

                    //System.out.println("Dir_actual " + dir + " Dir_anterior " + dir_ant);
                    if (!(dir_ant.equals(dir)) && dir_ant != null) {
                        directorios = new Document();
                        directorios.append(dir, asList(etiqueta_valor));
                    }
                }
            }
            //metadata.append("nombre_directorio", asList(directorios));

            imagen.append("nombre_foto", f.getNombreFoto())
                    .append("tamaño", f.getTamano())
                    .append("extension", f.getExtension())
                    .append("nombre_ruta", f.getRuta()) //
                    .append("metadatos", asList(directorios));

            datosFotos.add(imagen);
        }

        db.getCollection("prueba").insertMany(datosFotos);

    }

    public static void main(String[] args) {

        List<Foto> allPhoto = FotoDAO.getAllPhoto();
        List<Valor> allValor = ValorDAO.getAllValor();
        Map<Integer, String> allDirectories = DirectorioDAO.getAllDirectories();
        Map<Integer, Etiqueta> allTags = EtiquetaDAO.getAllTags();

        //insertarFotos(allPhoto, allTags, allDirectories, allValor);
        insertarFotitos(allPhoto);
        
        //List<Integer> ld = DirectorioDAO.getAllIdDirectoriesByPhoto(41);
        
        //System.out.println("Holaaaa " + ld);
        
        //List<Jam> listJam = JamDAO.listJamByIdPhoto(46);
        
        //System.out.println(listJam);

    }
}
