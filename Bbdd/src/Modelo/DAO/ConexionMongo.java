/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.DAO;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author inftel08
 */
public class ConexionMongo {
    
    private static String direccion = "192.168.183.55";
    private static long port = 27017;
    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static String nomColeccion = "prueba";
    private static String nomDatabase = "test";
    
    
    public static synchronized MongoClient obtenerConexion() {

        if (mongoClient == null) {
            mongoClient = new MongoClient( direccion , 27017 );
            db = mongoClient.getDatabase("test");                
        }
        
        return mongoClient;
    }
    
    public static synchronized MongoDatabase obtenerDataBase() {

        if (mongoClient == null) {
            mongoClient = new MongoClient( direccion , 27017 );
            db = mongoClient.getDatabase("test");                
        }
        
        return db;
    }
    
    public static String getnomColeccion () {
       
        return nomColeccion;
    }
    
    public static String getnomDB () {
       
        return nomDatabase;
    }
    
    
    
}
