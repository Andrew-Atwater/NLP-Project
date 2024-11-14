package com.recipeapp.database;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.FindIterable;

public class Read {

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://<username>:<password>@<cluster-name>.vajhl.mongodb.net/?retryWrites=true&w=majority&appName=<cluster-name>";

        try (MongoClient mongoclient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoclient.getDatabase("sample_mflix");

            MongoCollection<Document> commentsCollection = database.getCollection("comments");

            Document firstDocument = commentsCollection.find().first();

            System.out.println(firstDocument.toString());

            // The following code snippet will not work since your collection does not have entries with movie_name as "The Matrix"
            // But you can try to create them using the Create.java file and then run this file.
            
            // FindIterable<Document> documents = commentsCollection.find(eq("movie_name", "The Matrix"));

            // for (Document eachDocument : documents) {
            //     System.out.println(eachDocument.toString());
            // }

        }

    }
}