package com.recipeapp.nlp;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.BsonValue;

import com.recipeapp.database.Database;

// import java.util.HashMap;
// import java.util.ArrayList;
// import com.movieapp.movie.Movie;
// import com.movieapp.database.Database;
// import org.bson.BsonValue;


public class RecipeRecommender {
    
    private TFIDF tfidf;
    private Database movieDatabase;
    private Processor processor;

    // public RecipeRecommender(Processor processor, TFIDF tfidf) {
    //     this.processor = processor;
    //     this.tfidf = tfidf;
    //     movieDatabase = new Database("movie_app_database", "movie_data");
    // }

    // public ArrayList<Movie> recommendMovies(String movieOverview, int numRecommendations) {
    //     String[] words = processor.processText(movieOverview);
    //     HashMap<BsonValue, Float> movieScores = new HashMap<>();
    //     ArrayList<Movie> recommendedMovies = new ArrayList<>();

    //     for (BsonValue id : tfidf.getIds()) {
    //         float score = tfidf.calculateTFIDF(id, words);
    //         movieScores.put(id, score);
    //     }

    //     movieScores.entrySet().stream()
    //             .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
    //             .limit(numRecommendations)
    //             .forEach(e -> recommendedMovies.add(new Movie(movieDatabase.getDocumentByID(e.getKey()))));

    //     return recommendedMovies;
    }
}
