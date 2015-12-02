/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bbdd;
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
    
    public static void main(String[] args) {
        
        MongoClient mongoClient = new MongoClient("192.168.183.55", 27017);
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("prueba");
        
        collection.insertOne (new Document("address",
                new Document()
                        .append("street", "2 Avenue")
                        .append("zipcode", "10075")
                        .append("building", "1480")
                        .append("coord", asList(-73.9557413, 40.7720266)))
                .append("borough", "Manhattan")
                .append("cuisine", "Italian")
                .append("grades", asList(
                        new Document()
                                .append("date", "2014-10-01T00:00:00Z")
                                .append("grade", "A")
                                .append("score", 11),
                        new Document()
                                .append("date", "2014-01-16T00:00:00Z")
                                .append("grade", "B")
                                .append("score", 17)))
                .append("name", "Vella")
                .append("restaurant_id", "41704620"));
        //MongoDatabase db = new 
        //MongoDatabase db = mongoClient.listDatabases()
        //db.createCollection("prueba");
        
        
        
    }
    
}
