package com.recipeapp.recipe;

import org.bson.Document;

public class Recipe {

    private String recipeName;
    private Float thumbUpCount;
    private Float thumbDownCount;
    private String reviewText;

    public Recipe(String recipeName, Float thumbUpCount, Float thumbDownCount, String reviewText) {
        this.recipeName = recipeName;
        this.thumbUpCount = thumbUpCount;
        this.thumbDownCount =  thumbDownCount;
        this.reviewText = reviewText;
    }
    
    public String getRecipeName(){
        return recipeName;
    }
    
    public float getThumbUpCount(){
        return thumbUpCount;
    }

    public float getThumbDownCount(){
        return thumbDownCount;
    }

    public String getReviewText(){
        return reviewText;
    }

   public void setRecipeName(String recipeName) {
       this.recipeName = recipeName;
   }

   public void setThumbDownCount(Float thumbDownCount) {
       this.thumbDownCount = thumbDownCount;
   }

   public void setThumbUpCount(Float thumbUpCount) {
       this.thumbUpCount = thumbUpCount;
   }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
   }

    public Document getDocument() {
        Document document = new Document();
        document.append("recipe_name", recipeName);
        document.append("thumbs_up", thumbUpCount);
        document.append("thumbs_down", thumbDownCount);
        document.append("text", reviewText);
        return document;
    }
}