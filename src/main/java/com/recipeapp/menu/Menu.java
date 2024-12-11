package com.recipeapp.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.recipeapp.database.Database;
import com.recipeapp.recipe.Recipe;

public class Menu {

    /*
     * This method is called before showing the menu options to the user. It creates the necessary collections in the database.
     * It also parses the necessary CSV files and populatest the collection.
     * 
     * You would also want to add other features such 
     */
    public void startUp() {

        // Create a collection in the database to store Recipe objects
        Database recipeDatabase = new Database("recipe_reviews", "review_data");
        recipeDatabase.createCollection();

        // Parse test_recipe_metadata.txt
        String txtFile = "src/main/resources/test_recipe_metadata.txt";
        String line;
        String delimiter = "#";

        int lineCounter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
            // Skip the first header line
            br.readLine();
            lineCounter++;
            while ((line = br.readLine()) != null) {
                try {
                    lineCounter++;
                    String[] recipeData = line.split(delimiter);
                    String recipeNames = recipeData[0];
                    Integer thumbsUp = Integer.parseInt(recipeData[1]);
                    Integer thumbsDown = Integer.parseInt(recipeData[2]);
                    String reviewContent = recipeData[3];
                    // System.out.println(line);
                    Recipe recipeObject = new Recipe(recipeNames, thumbsUp, thumbsDown, reviewContent);
                    recipeDatabase.addToDatabase(recipeObject.getDocument());
                } catch (ArrayIndexOutOfBoundsException e ){
                    System.out.println("Encountered issue on line "+ lineCounter 
                                        +". Sending you back to the main menu...");
                    mainMenu();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException occurred. Sending you back to the main menu...");
            mainMenu();
        } 
    }

    /*
     * This method is called whenever user selects the Exit option. This method deletes the collection created in the database.
     */
    public void shutDown() {

        Database recipeDatabase = new Database("recipe_app_database", "recipe_data");
        recipeDatabase.deleteAllDocuments();
        recipeDatabase.deleteCollection();
        System.out.println("Trying to delete...");

    }

    public void addRecipeToDatabase() {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter the name of the recipe");
            String newRecipeName = scanner.nextLine();
            System.out.println("Please enter review of the recipe");
            String newReviewContent = scanner.nextLine();
            System.out.println("Please enter the amount of likes on this review");
            Integer newThumbsUp = Integer.parseInt(scanner.nextLine());
            System.out.println("Please enter the amount of dislikes on this review");
            Integer newThumbsDown = Integer.parseInt(scanner.nextLine());

            Recipe userRecipe = new Recipe(newRecipeName, newThumbsUp, newThumbsDown, newReviewContent);
            Database recipeDatabase = new Database("recipe_app_database", "recipe_data");

            recipeDatabase.addToDatabase(userRecipe.getDocument());

        }
    }

    public void printRecipeFromDatabase() {

        // COde to print all reviews information goes here, as well as weighted average review for Recipe
        //ALso ask user about which recipe they want, tell them avergae thumbs count, and % up or down, etc.
        
        

    }

    public void getRecipeTastiness(ArrayList<String[]> recipeChoiceData){
        
    }

    public void seeReviews(ArrayList<String[]> recipeChoiceData) {
        
    }
    public void seeThumbsData(ArrayList<String[]> recipeChoiceData){
        
    }


    public void mainMenu(){
        
    }
    public static void main(String[] args) {

        System.out.println("Initializing the recipe app...");
        
        //Call the startUp method
        Menu menu = new Menu();
        menu.startUp();
        menu.mainMenu();
        // Ideally you want to make this menu an endless loop until the user enters to exit the app. (done)
        // When they select the option, you call the shutDown method. (done)

    }
}
