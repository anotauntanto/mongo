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
import org.bson.types.ObjectId;

/**
 *
 * @author inftel08
 */
public class ConsultasMongoDAO {

    private static MongoDatabase db;
    private static MongoClient mongoClient;

    public static DBCursor consultarUnCampoPrincipal(String clave, String valor) {

        /*db = ConexionMongo.obtenerDataBase();
         FindIterable<Document> find = db.getCollection(ConexionMongo.getnomColeccion()).find(new Document(clave, valor));

         return find;*/
        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query = new BasicDBObject(clave, valor);
        DBCursor find = coll.find(query);

        return find;

    }

    public static DBCursor obtenerFotosCamara(String clave1, String valor1, String clave2, String valor2) {

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query1 = new BasicDBObject(clave1, valor1);
        DBObject query2 = new BasicDBObject(clave2, valor2);
        BasicDBList and = new BasicDBList();
        and.add(query1);
        and.add(query2);
        DBObject query = new BasicDBObject("$and", and);
        DBCursor find = coll.find(query);

        return find;
        /*
         //iterate through the list and print the elements
         while (find.hasNext()) {
         DBObject next = find.next();
            
         }*/

    }

    public static Vector<String> consultarCampoMarcaEtiquetaMetadatos(String clave) {

        Vector<String> miVector = new Vector<>();

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        List cl1 = coll.distinct(clave);

        //iterate through the list and print the elements
        for (int i = 0; i < cl1.size(); i++) {
            miVector.add(cl1.get(i).toString());

        }

        return miVector;
    }

    public static Vector<String> consultarCampoModeloEtiquetaMetadatos(String clave, String valor) {

        Vector<String> miVector = new Vector<>();

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query = new BasicDBObject(clave, valor);
        List cl1 = coll.distinct("Exif IFD0.Model", query);

        //iterate through the list and print the elements
        for (int i = 0; i < cl1.size(); i++) {
            miVector.add(cl1.get(i).toString());
            //System.out.println(cl1.get(i));

        }

        return miVector;
    }

    public static DBCursor consultarUnCampoParcial(String clave, String valor) {

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        BasicDBObject busqueda = new BasicDBObject(clave, new BasicDBObject("$regex", ".*" + valor + ".*"));

        DBCursor find = coll.find(busqueda);

        return find;

    }

    public static DBCursor consultarValoresMayores(String clave, String valor) {

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        int tamano = Integer.parseInt(valor);
        BasicDBObject busqueda = new BasicDBObject(clave, new BasicDBObject("$lt", tamano));

        DBCursor find = coll.find(busqueda);

        return find;

    }

    public static DBCursor obtenerFoto(String nombre, String ruta, String extension) {

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query1 = new BasicDBObject("nombre_foto", nombre);
        DBObject query2 = new BasicDBObject("nombre_ruta", ruta);
        DBObject query3 = new BasicDBObject("extension", extension);

        BasicDBList and = new BasicDBList();
        and.add(query1);
        and.add(query2);
        and.add(query3);

        DBObject query = new BasicDBObject("$and", and);
        DBCursor find = coll.find(query);

        return find;

    }

    public static DBCursor consultarFecha(String valor) {

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        DBObject query1 = new BasicDBObject("Exif IFD0.Date/Time", new BasicDBObject("$regex", ".*" + valor + ".*"));
        DBObject query2 = new BasicDBObject("GPS.GPS Date Stamp", new BasicDBObject("$regex", ".*" + valor + ".*"));

        BasicDBList or = new BasicDBList();
        or.add(query1);
        or.add(query2);
        DBObject fecha = new BasicDBObject("$or", or);
        
        DBCursor find = coll.find(fecha);

        return find;

    }

    public static Vector<String> obtenerMetadatosPorDirectorio(String id_foto, String directorio) {

        Vector<String> miVector = new Vector<>();

        System.out.println(id_foto + " " + directorio);

        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query = new BasicDBObject("_id", new ObjectId(id_foto));
        List cl1 = coll.distinct(directorio, query);
        /*System.out.println("tamaño " + cl1.size());
         DBObject found = coll.findOne(query);
        
         System.out.println(found);*/
        //iterate through the list and print the elements

        for (int i = 0; i < cl1.size(); i++) {
            miVector.add(cl1.get(i).toString());
            Object[] toArray = cl1.toArray();

            System.out.println(cl1.get(i));

        }

        return miVector;
    }

    public static DBCursor consultarPorClave(String clave) {

        //System.out.println(directorio);
        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query = new BasicDBObject("$ne", null);
        DBObject directorio_ = new BasicDBObject(clave, query);

        DBCursor find = coll.find(directorio_);

        return find;
    }

    public static DBCursor consultarPorDirectorioyEtiqueta(String directorio, String etiqueta) {

        System.out.println(directorio + " " + etiqueta);
        String clave = directorio + '.' + etiqueta;
        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query = new BasicDBObject("$ne", null);
        DBObject etiqueta_ = new BasicDBObject(clave, query);
        //DBObject directorio_ = new BasicDBObject (directorio, etiqueta_);
        DBCursor find = coll.find(etiqueta_);

        return find;
    }

    public static Vector<String> obtenerCoordenadas(String id_foto) {

        Vector<String> miVector = new Vector();
        //Get a new connection to the db assuming that it is running 
        MongoClient m1 = ConexionMongo.obtenerConexion();

        //use test as a database,use your database here
        DB db = m1.getDB(ConexionMongo.getnomDB());

        //fetch the collection object ,car is used here,use your own 
        DBCollection coll = db.getCollection(ConexionMongo.getnomColeccion());

        //call distinct method and store the result in list l1
        DBObject query1 = new BasicDBObject("_id", new ObjectId(id_foto));

        List cl1 = coll.distinct("GPS.GPS Longitude", query1);
        if (cl1.size() != 0) {
            miVector.add(cl1.get(0).toString());
        }

        List cl2 = coll.distinct("GPS.GPS Latitude", query1);
        if (cl2.size() != 0) {
            miVector.add(cl2.get(0).toString());
        }

        System.out.println(miVector);

        return miVector;

    }


    /*public static void main (String[] args) {
     consultarCampoModeloEtiquetaMetadatos("Exif IFD0.Make", "LGE");
        
        
     }*/
}
