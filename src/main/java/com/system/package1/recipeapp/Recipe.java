package com..recipe;

import org.bson.Document;

public class Recipe {

    private String recipeName;
    private Float thumbUpCount;
    private Float thumbDownCount;
    private  

    public Recipe(String recipeName, Float thumbUpCount, Float thumbDownCount) {
        this.recipeName = recipeName;
        this.thumbUpCount = thumbUpCount;
        this.thumbDownCount =  thumbDownCount;
    }