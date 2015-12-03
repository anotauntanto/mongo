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
import java.io.*;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeString.substring;
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

    public static Vector<String> consultarCampoEtiquetaMetadatos(String clave) {

        Vector<String> miVector = new Vector<>();
        
        
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
            
            System.out.println(miVector);
            return miVector;
    }

}
