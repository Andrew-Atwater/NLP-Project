// package com.recipeapp.database;

// import java.io.FileReader;
// import java.io.IOException;

// import org.bson.Document;

// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.MongoDatabase;

// public class Database {

//     public static void main(String[] args) {
        
//         String connectionString = readFile();

//         try (MongoClient mongoclient = MongoClients.create(connectionString)) {

//             MongoDatabase database = mongoclient.getDatabase("recipe_reviews");

//             MongoCollection<Document> commentsCollection = database.getCollection("bulk_data");

//             Document firstDocument = commentsCollection.find().first();

//             System.out.println(firstDocument.toString());

//             // The following code snippet will not work since your collection does not have entries with movie_name as "The Matrix"
//             // But you can try to create them using the Create.java file and then run this file.
            
//             // FindIterable<Document> documents = commentsCollection.find(eq("movie_name", "The Matrix"));

//             // for (Document eachDocument : documents) {
//             //     System.out.println(eachDocument.toString());
//             // }

//         }

//     }


//     /**
//      * This reader function simply pulls in a filename from the main method and spits out a String version of that txt file
//      * This is the same method that I used for my data analyzer
//      */
//     public static String readFile() {
//     try (FileReader reader = new FileReader("pass.txt")) {
//         int character = reader.read();
//         String fileString = "";  

//         while (character != -1) { //this counts newline as one chara
//             if (character != 13) { //ascii for skipping carriage return
//                 fileString = fileString+((char) character);
//             }
//             character = reader.read();
//         }

//         return fileString;

//     } catch (IOException e) {
//         System.out.println("Something Unexpected Occurred.");
//         e.printStackTrace();

//         return ("Error");
//     }

//     }


// }


package com.recipeapp.database;

import java.io.FileReader;
import java.io.IOException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String dbName, String collectionName) {
        // Write your own connection string here!
        //this.connectionString = readFile();
        this.connectionString = "mongodb+srv://lee12:pass2026@cos225-nlp.4yugd.mongodb.net/?retryWrites=true&w=majority&appName=COS225-NLP";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    public Database(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;

    }

    public void addToDatabase(Document document) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);

            movieCollection.insertOne(document);

        }

    }

    public void createCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.createCollection(this.collectionName);

        }

    }

    public void deleteCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.getCollection(this.collectionName).drop();

        }

    }

    public void deleteAllDocuments() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);

            movieCollection.deleteMany(new Document());

        }

    }


    /**
     * This reader function simply pulls in a filename from the main method and spits out a String version of that txt file
     * This is the same method that I used for my data analyzer
     */
    public static String readFile() {
    try (FileReader reader = new FileReader("pass.txt")) {
        int character = reader.read();
        String fileString = "";  

        while (character != -1) { //this counts newline as one chara
            if (character != 13) { //ascii for skipping carriage return
                fileString = fileString+((char) character);
            }
            character = reader.read();
        }

        return fileString;

    } catch (IOException e) {
        System.out.println("Something Unexpected Occurred.");
        e.printStackTrace();

        return ("Error");
    }

    }



}