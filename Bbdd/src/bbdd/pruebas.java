/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import Modelo.Clases.Foto;
import Modelo.DAO.ConexionMongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.text.MessageFormat.format;
import org.bson.Document;
import static java.util.Arrays.asList;

/**
 *
 * @author inftel08
 */
public class pruebas {

    
    public void insertarFotoenColeccion (Foto foto) {
        MongoDatabase db = ConexionMongo.obtenerDataBase();
        Document imagen = new Document();
      
        
        imagen.append("id_foto", foto.getIdFoto())
                .append("nombre_foto", foto.getNombreFoto())
                .append ("tamaño", foto.getTamano())
                .append ("extension", foto.getExtension())
                .append ("nomre_ruta", foto.getTamano()) //
                ));
        db.createCollection(null)
        
        
    }
    
    public static void main(String[] args) {
            
        
       /*
                .append("id_foto", "1")
                .append("nombreFoto", "prueba")
                .append("tamano", "123")
                .append("extension", "jpeg")
                .append("nombreRuta", "aqui")
                .append("Metadatos", asList(
                                new Document()
                                .append("blabla", asList(
                                                new Document()
                                                .append("ICC Profile Name", "ICC profile")
                                                .append("Compression Type", "JPEG")
                                        )))));
        System.out.println();
        //MongoDatabase db = new 
        //MongoDatabase db = mongoClient.listDatabases()
        //db.createCollection("prueba");
*/
    }

}
