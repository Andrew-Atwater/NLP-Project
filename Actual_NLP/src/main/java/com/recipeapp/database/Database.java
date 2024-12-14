package com.recipeapp.database;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.bson.BsonValue;
import com.mongodb.client.result.InsertOneResult;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String databaseName, String collectionName, String envFilePath) {
        try (BufferedReader br = new BufferedReader (new FileReader(envFilePath))) {
                this.connectionString = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public Database(String databaseName, String collectionName) {
        try (BufferedReader br = new BufferedReader (new FileReader("src/main/resources/.env"))) {
            this.connectionString = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public InsertOneResult addToDatabase(Document document) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> genericCollection = genericDatabase.getCollection(this.collectionName);
            return genericCollection.insertOne(document);
        }
    }

    public void createCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            genericDatabase.createCollection(this.collectionName);
        }
    }

    public void deleteCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            genericDatabase.getCollection(this.collectionName).drop();
        }
        // System.out.println("Deleted...");   //for error testing
    }

    public void deleteAllDocuments() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> genericCollection = genericDatabase.getCollection(this.collectionName);
            genericCollection.deleteMany(new Document());
        }
        // System.out.println("Almost deleted...");  //for error testing
    }

    /**
     * This method returns all the documents from a MongoDB collection as an ArrayList.
     * 
     * @return ArrayList<Document> documents - An ArrayList of all the documents in the collection.
     */
     public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> documents = new ArrayList<Document>();
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> genericCollection = genericDatabase.getCollection(this.collectionName);
            for (Document doc : genericCollection.find()) {
                documents.add(doc);
            }
        } 
        return documents;
    }

    public Document getDocumentByID(BsonValue id) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> genericCollection = genericDatabase.getCollection(this.collectionName);
            return genericCollection.find(new Document("_id", id)).first();
        }
    }
}