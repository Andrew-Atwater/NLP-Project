package com.recipeapp.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.io.Serial;
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
        Database recipeDatabase = new Database("recipe_reviews", "recipe_data");
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
                    System.out.println("Encountered issue on line "+ lineCounter +"."); //error catch statement
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    /*
     * This method is called whenever user selects the Exit option. This method deletes the collection created in the database.
     */
    public void shutDown() {

        Database recipeDatabase = new Database("recipe_app_database", "recipe_data");
        recipeDatabase.deleteCollection();

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
        
        System.out.println("Pretend this is your recipe information");

    }


    // Method to find reviews for a  specific recipe
    public void findRecipeOtherReviews() {
        String recipeFile = "src/main/resources/test_recipe_metadata.txt";  
        String line;
        String delimiter = "#"; 

        // Scanner to get the recipe name from user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the recipe to search for: ");
        String recipeSearchName = scanner.nextLine();  // Read the recipe name input by the user
        System.out.println("Searching for " + recipeSearchName + "...\n");

        try (BufferedReader br = new BufferedReader(new FileReader(recipeFile))) {
            // Skip the first header line if present (assuming the first line is a header)
            br.readLine();
            
            int reviewCounter = 1;
            // Loop through each line in the file
            boolean foundRecipe = false;
            while ((line = br.readLine()) != null) {
                // Split the line by the delimiter "#"
                String[] recipeData = line.split(delimiter);
                
                // Check if the line contains at least four elements (recipe name, thumbs up, thumbs down, and review text)
                if (recipeData.length >= 4) {
                    String recipeName = recipeData[0].strip();
                    // Compare the input recipe name with the recipe name from the file 
                    if (recipeSearchName.equalsIgnoreCase(recipeName)) {
                        if (foundRecipe == true) {
                            System.out.println("Here are the reviews for " + recipeName + ":\n");
                            foundRecipe = true; 
                        }
                        // Print review text
                        System.out.println("Review " + reviewCounter + ": "+ recipeData[3].trim() + "\n");
                        reviewCounter++;
                    }
                }
            }

            // If no recipe was found by the name
            if (!foundRecipe) {
                System.out.println("Recipe not found!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }scanner.close();       
    
    }

    public static void main(String[] args) {

        System.out.println("Initializing the recipe app...");
        
        //Call the startUp method
        Menu menu = new Menu();
        menu.startUp();
        
        // Ideally you want to make this menu an endless loop until the user enters to exit the app.
        // When they select the option, you call the shutDown method.
        System.out.println("Hello! Welcome to the recipe app!");
        
        System.out.println("Please select one of the following options ");
        System.out.println("1. Add a recipe review to the database.");
        System.out.println("2. Get details of a recipe from the database.");
        System.out.println("3. Find other reviews for the recipe.");
        System.out.println("4. Exit.");

        System.out.print("Enter you choice: ");

        try (Scanner scanner = new Scanner(System.in)) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Adding recipe reviews...");
                    menu.addRecipeToDatabase();
                    break;

                case 2:
                    System.out.println("Reading a recipe from the database");
                    menu.printRecipeFromDatabase();
                    break;

                case 3:
                    System.out.println("Finding similar recipe reviews...");
                    menu.findRecipeOtherReviews();
                    break;
 
                case 4:
                    System.out.println("Exiting the recipe app...");
                    menu.shutDown();
                    break;  

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }


    }
}
