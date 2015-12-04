/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbdd;

import Modelo.DAO.ConexionMongo;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.Vector;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author inftel08
 */
public class Busquedas {

    public static void main(String[] args) {

        /*MongoClient mongoClient = ConexionMongo.obtenerConexion();
        MongoDatabase db = ConexionMongo.obtenerDataBase();*/
        
        


        /*CONSULTA BÁSICA
         FindIterable<Document> find = db.getCollection(nomColeccion).find(new Document("nombre_foto", "PEZ.JPG"));
         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         System.out.println(document);
         }
         });*/

        /*CONSULTA NIVEL 2
 FindIterable<Document> find = db.getCollection("prueba").distinct("Exif IFD0.Make", null);
         //FindIterable<Document> find = db.getCollection("prueba").find( new Document ("Exif IFD0.Make", new Document ("$ne", null)));
         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         System.out.println(document.getString("Exif IFD0.Make"));
         }
         });
        /*CONSULTA NIVEL 3
         DBObject query = new BasicDBObject("metadatos", new BasicDBObject("$all", "metadatos.File"));
         MongoCollection<Document> collection1 = db.getCollection(nomColeccion);
         FindIterable<Document> find = collection1.find((Bson) query);

         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         System.out.println(document);
         }
         });*/
        //DBObject query = new BasicDBObject("extension","JPG");
        
        MongoClient mongoClient = new MongoClient( "192.168.183.55" , 27017 );
        DB db = mongoClient.getDB("test");
        DBCollection collection = db.getCollection("prueba");
        
        DBObject etiqueta_valor = new BasicDBObject ("$ne", null);
        DBObject etiqueta = new BasicDBObject ("Make", etiqueta_valor);
        DBObject directorio = new BasicDBObject ("Exif IFD0", etiqueta);
        
        System.out.println("eeei");
        DBCursor find = collection.find(directorio);
               
        while(find.hasNext()) {
            DBObject obj = find.next();
            System.out.println(obj);
        }
        
        /*Vector<String> miVector = new Vector<>();
        
        
        //Get a new connection to the db assuming that it is running 
            MongoClient m1 = ConexionMongo.obtenerConexion();
             
            //use test as a database,use your database here
            DB db = m1.getDB("test");
             
             //fetch the collection object ,car is used here,use your own 
            DBCollection coll = db.getCollection("prueba");
             
            //call distinct method and store the result in list l1
            List cl1= coll.distinct("Exif IFD0.Make");
             
            //iterate through the list and print the elements
            for(int i=0;i<cl1.size();i++){
                System.out.println(cl1.get(i).toString());
                miVector.add(cl1.get(i).toString());
                
            }
            
            System.out.println(miVector);*/
    }

}
