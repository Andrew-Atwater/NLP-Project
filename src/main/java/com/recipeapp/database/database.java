package com.recipeapp.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String databaseName, String collectionName) {
        // Write your own connection string here!
        this.connectionString = "mongodb+srv://lee12:pass2026@cos225-nlp.4yugd.mongodb.net/?retryWrites=true&w=majority&appName=COS225-NLP";
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public Database(String connectionString, String databaseName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = databaseName;
        this.collectionName = collectionName;

    }

    public void addToDatabase(Document document) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase recipeDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> recipeCollection = recipeDatabase.getCollection(this.collectionName);

            recipeCollection.insertOne(document);

        }

    }

    public void createCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase recipeDatabase = mongoClient.getDatabase(this.databaseName);
            recipeDatabase.createCollection(this.collectionName);

        }

    }

    public void deleteCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase recipeDatabase = mongoClient.getDatabase(this.databaseName);
            recipeDatabase.getCollection(this.collectionName).drop();

        }
        System.out.println("Deleted...");

    }

    public void deleteAllDocuments() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase recipeDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> recipeCollection = recipeDatabase.getCollection(this.collectionName);

            recipeCollection.deleteMany(new Document());

        }
        System.out.println("Almost deleted...");
    }

}