package bank.person.micros.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDB {
    public static MongoCollection<Document> getMongoCollection(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("personbank");
        MongoCollection<Document> collection  = database.getCollection("person");
        return  collection;
    }

}
