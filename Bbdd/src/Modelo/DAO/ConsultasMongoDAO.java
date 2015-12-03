/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import Modelo.DAO.ConexionMongo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.bson.Document;

/**
 *
 * @author inftel08
 */
public class ConsultasMongoDAO {

    private static MongoDatabase db;
    private static MongoClient mongoClient;

    public static FindIterable<Document> consultarUnCampoPrincipal(String clave, String valor) {

        db = ConexionMongo.obtenerDataBase();
        FindIterable<Document> find = db.getCollection(ConexionMongo.getnomColeccion()).find(new Document(clave, valor));

        return find;

    }

    public static Vector<DBObject> consultarModeloCamara(String clave) {

        Vector<DBObject> vectorC = new Vector<>();
        mongoClient = ConexionMongo.obtenerConexion();
        DB db = mongoClient.getDB(ConexionMongo.getnomDB());
        DBCollection table = db.getCollection(ConexionMongo.getnomColeccion());

        /*
        
         

         DBObject query = new BasicDBObject(clave, new BasicDBObject("$ne", null));
         DBCursor cursor = collection.find(query);*/
        /*db = ConexionMongo.obtenerDataBase();
         FindIterable<Document> find = db.getCollection(ConexionMongo.getnomColeccion()).find(new Document(clave, new Document("$ne", null)));
        
         find.forEach(new Block<Document>() {
         @Override
         public void apply(final Document document) {
         //System.out.println(document);
         System.out.println();
         Object get = document.get("metadatos");
         Document t = new Document(get);
         System.out.println(get);
         //System.out.println(todosMetadatos.split("Make="));
                    
                    
         //document.
                
         }
         });*/
        /*while (cursor.hasNext()) {
         DBObject obj = cursor.next();

         BasicDBList metadataList = (BasicDBList) obj.get("Exif IFD0");
         //BasicDBList ExifList = (BasicDBList) metadataList.get(0);
            
         System.out.println("eiii " + metadataList);
         for (int i = 0; i < metadataList.size(); i++) {
         BasicDBObject studentObj = (BasicDBObject) metadataList.get(i);
         String maquina = studentObj.getString("make");
         //System.out.println(maquina);

         }

         //Pausa
         vectorC.add(obj);
         //System.out.println(obj);
         }*/
        /*
        
         BasicDBObject criteria = new BasicDBObject();
         BasicDBObject projections = new BasicDBObject();
         criteria.put("metadatos.Exif IDF0.Make", new BasicDBObject("$not", new BasicDBObject("$size", 0)));
         DBCursor cur = collection.find(criteria);
         while (cur.hasNext()) {
           
            
            
         String json = cur.next().toString();
         System.out.println(json);
         }*/
        
        
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        
        field.put("metadatos.Exif IFD0.Make", 1);
        DBCursor cursor = table.find(query, field);
        String str;
        Vector<String> miVector=new Vector<>(); 
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
           
            //System.out.println(obj.get("metadatos.Exif IFD0.Make"));
            //System.out.println(obj.get("metadatos"));
            
            String[] partes=obj.get("metadatos").toString().split("\"Make\" : \"");
            
            if (partes.length>1){
                //System.out.println(partes[1]);
                String [] parteFinal=partes[1].split("\"");
                //System.out.println(parteFinal[0]);
                if (!miVector.contains(parteFinal[0])){
                    miVector.add(parteFinal[0]);
                   
                }
            }
            //System.out.println(partes[1]);
            //System.out.println(cursor.getKeysWanted());
            //System.out.println(obj.toString());
            /*str = cursor.curr().get("metadatos.Exif IFD0").toString();
            System.out.println(str);*/
            
        }
        for (String e:miVector){
                System.out.println(e);
            }
        return null;
    }

    public static void main(String[] args) {
        consultarModeloCamara("metadatos.Exif IFD0");
    }
}
