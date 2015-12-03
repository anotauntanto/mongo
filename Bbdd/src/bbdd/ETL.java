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
public class ETL {

    private static List<Document> datosFotos;
    
    public static void migrado_Oracle_Mongo(List<Foto> listaFotos) {
        datosFotos = new ArrayList<>();

        MongoDatabase db = ConexionMongo.obtenerDataBase();
        String nomColeccion = ConexionMongo.getnomColeccion();

        Etiqueta tag;
        Document imagen = new Document();
        Document directorios = new Document();
        Document etiqueta_valor = new Document();
        Document metadata;
        

        for (Foto f : listaFotos) {
            
            imagen = new Document();
            directorios = new Document();
            etiqueta_valor = new Document();
            f.setRuta(RutaDAO.getStringRuta(f.getIdRuta()));
            
            imagen.append("nombre_foto", f.getNombreFoto())
                    .append("tamaño", Integer.parseInt(f.getTamano()))
                    .append("extension", f.getExtension())
                    .append("nombre_ruta", f.getRuta());
            
            String dir = "", dir_ant = null;
            
            
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
                    imagen.append(dir_ant,(etiqueta_valor));
                    etiqueta_valor = new Document();
                    etiqueta_valor.append(j.getNombreEtiqueta(), j.getValor());
                    
                }
                
            }
            imagen.append(dir, (etiqueta_valor)); //guardo la última pq ya no puedo comparar con nada

             //
                    //.append(directorios);
            
            datosFotos.add(imagen);
        }
            
        
        db.getCollection("prueba").insertMany(datosFotos);

    }


    public static void main(String[] args) {

        List<Foto> allPhoto = FotoDAO.getAllPhoto();
        migrado_Oracle_Mongo(allPhoto);

    }
}
