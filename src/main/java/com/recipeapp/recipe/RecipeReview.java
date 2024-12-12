package com.recipeapp.recipe;
import org.bson.Document;

public class RecipeReview {
    private String review, sentiment;
    public RecipeReview(String review, String sentiment) {
        this.review = review;
        this.sentiment = sentiment;
    }
    public RecipeReview(Document document) {
        this.review = document.getString("review");
        this.sentiment = document.getString("sentiment");
    }
    public String getReview() {
        return review;
    }
    public String getSentiment() {
        return sentiment;
    }
    public Document getDocument() {
        Document document = new Document();
        document.append("review", review);
        document.append("sentiment", sentiment);
        return document;
    }
}