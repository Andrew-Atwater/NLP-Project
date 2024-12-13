package com.recipeapp.recipe;

import org.bson.Document;

public class recipeReview {
    private String reviewText;
    private String sentiment;
    
    public recipeReview(String reviewText, String sentiment){
        this.reviewText = reviewText;
        this.sentiment = sentiment;
    }
    public String getReviewText(){
        return reviewText;
    }
    public String getSentiment(){
        return sentiment;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public Document getDocument() {
        Document document = new Document();
        document.append("review", reviewText);
        document.append("sentiment", sentiment);
        return document;
    }
}
