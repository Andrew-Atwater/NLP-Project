package com.recipeapp.recipe;
import org.bson.Document;

public class Recipe {
    private String recipeName;
    private Integer thumbUpCount;
    private Integer thumbDownCount;
    private String reviewText;
    public Recipe(String recipeName, Integer thumbUpCount, Integer thumbDownCount, String reviewText) {
        this.recipeName = recipeName;
        this.thumbUpCount = thumbUpCount;
        this.thumbDownCount =  thumbDownCount;
        this.reviewText = reviewText;
    }
    public Recipe(Document document) {
        this.recipeName = document.getString("recipe_name");
        this.thumbUpCount = document.getInteger("thumbs_up");
        this.thumbDownCount = document.getInteger("thumbs_down");
        this.reviewText = document.getString("text");
    }
    public String getRecipeName(){
        return recipeName;
    }
    public Integer getThumbUpCount(){
        return thumbUpCount;
    }
    public Integer getThumbDownCount(){
        return thumbDownCount;
    }
    public String getReviewText(){
        return reviewText;
    }
   public void setRecipeName(String recipeName) {
       this.recipeName = recipeName;
   }
   public void setThumbDownCount(Integer thumbDownCount) {
       this.thumbDownCount = thumbDownCount;
   }
   public void setThumbUpCount(Integer thumbUpCount) {
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