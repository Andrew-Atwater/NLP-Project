package com.recipeapp.nlp;
import java.util.ArrayList;
import java.util.HashMap;

import org.bson.BsonValue;

import com.recipeapp.database.Database;
import com.recipeapp.recipe.Recipe;

public class RecipeRecommender {
    private TFIDF tfidf;
    private Database recipeDatabase;
    private Processor processor;
    public RecipeRecommender(Processor processor, TFIDF tfidf) {
        this.processor = processor;
        this.tfidf = tfidf;
        recipeDatabase = new Database("recipe_app_database", "recipe_data");
    }
    public ArrayList<Recipe> recommendRecipes(String newReviewContent, int numRecommendations) {
        String[] words = processor.processText(newReviewContent);
        HashMap<BsonValue, Float> recipeScores = new HashMap<>();
        ArrayList<Recipe> recommendedRecipes = new ArrayList<>();
        for (BsonValue id : tfidf.getIds()) {
            float score = tfidf.calculateTFIDF(id, words);
            recipeScores.put(id, score);
        }
        recipeScores.entrySet().stream()
                .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                .limit(numRecommendations)
                .forEach(e -> recommendedRecipes.add(new Recipe(recipeDatabase.getDocumentByID(e.getKey()))));
        return recommendedRecipes;
    }
}
