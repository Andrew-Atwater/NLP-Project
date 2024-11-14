package com.system.package1.recipeapp;

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


}