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
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author inftel08
 */
public class Busquedas {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient( "192.168.183.55" , 27017 );
        DB db = mongoClient.getDB("test");
        DBCollection collection = db.getCollection("prueba");


        /*CONSULTA BÁSICA
         FindIterable<Document> find = db.getCollection(nomColeccion).find(new Document("nombre_foto", "PEZ.JPG"));
         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         System.out.println(document);
         }
         });*/

        /*CONSULTA NIVEL 2
         FindIterable<Document> find = db.getCollection(nomColeccion).find(new Document("metadatos.JFIF.Version", "1.1"));
         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         System.out.println(document);
         }
         });*/
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
        DBObject query = new BasicDBObject("metadatos.JFIF", new BasicDBObject("$ne", null));
        DBCursor cursor = collection.find(query);
        while(cursor.hasNext()) {
            DBObject obj = cursor.next();
            System.out.println(obj);
        }
    }

}
