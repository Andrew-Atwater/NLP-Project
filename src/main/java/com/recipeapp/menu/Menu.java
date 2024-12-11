package com.recipeapp.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Flow.Publisher;

import org.bson.Document;

import com.mongodb.client.result.DeleteResult;
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
        String txtFile = "test_recipe_metadata.txt";
        String line;
        String delimiter = "#";
        String recipeChoice;
        ArrayList<String[]> recipeChoiceData = new ArrayList<>();
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Please enter the recipe you would like to see reviews for: ");
            recipeChoice = scanner.nextLine();
            int lineCounter = 0;
            try(BufferedReader br = new BufferedReader(new FileReader(txtFile))){
                while((line = br.readLine()) != null)
                    try {
                        lineCounter++;
                        String[] recipeData = line.split(delimiter);
                        if(recipeData[0].equals(recipeChoice)){
                            recipeChoiceData.add(recipeData);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Out of bounds at line: " + lineCounter + ", error occurred."
                                            +"Sending you back to the main menu...");
                        mainMenu();
                    }
            } catch (IOException e) {
                System.out.println("IOException occurred. Sending you back to the main menu...");
                mainMenu();
            }
            System.out.println("Please select what you would like to see from the " + recipeChoice  
                                +" recipe from the menu by choosing an integer associated with each option:" 
                                +"\n1.) Scroll through reviews"
                                +"\n2.) See the thumbs up/down count and average data"
                                +"\n3.) See whether this is a good or bad recipe based on the reviews"
                                +"\n4.) Return to main menu.");
            int menuChoice = scanner.nextInt();
            switch(menuChoice){
                case 1:
                    System.out.println("Collecting review content...");
                    seeReviews(recipeChoiceData);
                    break;
                case 2:
                    System.out.println("Counting thumbs...");
                    seeThumbsData(recipeChoiceData);
                    break;
                case 3:
                    System.out.println("Determining recipe tastiness...");
                    getRecipeTastiness(recipeChoiceData);
                    break;
                case 4:
                    System.out.println("Sending you back to the menu...");
                    mainMenu();
                    break;
                default:
                    System.out.println("Invalid choice! Sending you back to the menu...");
                    mainMenu();
                    break;
            }
        }
        

    }

    public void getRecipeTastiness(ArrayList<String[]> recipeChoiceData){
        String goodFile = "goodWords.txt";
        String badFile = "badWords.txt";
        String line;
        int lineCounter;
        int goodWordCount = 0;
        int badWordCount = 0;
        String[] review;
        try(BufferedReader br = new BufferedReader(new FileReader(goodFile))){
            lineCounter = 0;
            while((line = br.readLine()) != null)
                try {
                    for(review : ){
                        String reviewWords = recipeChoiceData.get(lineCounter)[3];
                        if(reviewWords.contains(line)){
                        goodWordCount += 1;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error on line " + lineCounter +". Sending you back to the menu...");
                }
        } catch (IOException e) {
            System.out.println("Problem reading file. Sending you back to the menu...");
            mainMenu();
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(badFile))){
            //copy from goodFile code fragment
        } catch (IOException e) {
            System.out.println("Problem reading file. Sending you back to the menu...");
            mainMenu();
        }
    }

    public void seeReviews(ArrayList<String[]> recipeChoiceData) {
        String choice = "y";
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Review text will now be presented for the recipe you have selected." 
                                +"Input 'y' to see the next review for the recipe, or any other letter to return to the menu.");
            while(choice.equals("y"))
                try {
                    for(String[] recipe : recipeChoiceData){
                        //implement NLP: see whether the current review is good or bad
                        System.out.println(recipe[3] + "\nPlease enter 'y' to see another review, "
                                            +"or any other key to exit to the menu.");
                        choice = scanner.nextLine();
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Ran out of review text. Returning to main menu.");
                    mainMenu();
                }
            mainMenu();
        }
    }
    public void seeThumbsData(ArrayList<String[]> recipeChoiceData){
        System.out.println("Presenting total thumbs up/down count for all reviews for the selected recipe, " 
                            +"as well as weighted average rating based on thumbs up count...");
        int thumbUpCount = 0;
        int thumbDownCount = 0;
        for(String[] review : recipeChoiceData){
            thumbUpCount += Integer.parseInt(review[1]);
            thumbDownCount += Integer.parseInt(review[2]);
        }
        System.out.println("Total thumbs up count for all reviews for the " + recipeChoiceData.get(0)[0] + " recipe:"
                            +"\n" + thumbUpCount
                            +"\nTotal thumbs down: "
                            +"\n" + thumbDownCount);
        int totalThumbs = thumbUpCount + thumbDownCount;
        //if the total thumb count on a review is less than 2, discount it
        thumbUpCount = 0;
        thumbDownCount = 0;
        int up = 0;
        int down = 0;
        for(String[] review : recipeChoiceData){
            up = Integer.parseInt(review[1]);
            down = Integer.parseInt(review[2]);
            if(up + down >= 2){
                thumbUpCount += up;
                thumbDownCount += down;
            }
        }
        double positiveRatedPct = (thumbUpCount / totalThumbs) * 100;
        double negativeRatedPct = (thumbDownCount / totalThumbs) * 100;
        double neutralRatedPct = ((thumbUpCount + thumbDownCount) / totalThumbs) * 100;
        System.out.println("The percentage of positively rated reviews is: " + positiveRatedPct
                            +"\nThe percentage of negatively rated reviews is: " + negativeRatedPct
                            +"\nThe percentage of neutrally rated reviews"
                            +" or reviews that do not have enough ratings to count is: " + neutralRatedPct);
    }


    public void mainMenu(){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Hello! Welcome to the recipe app!");
        
            System.out.println("Please select one of the following options:"
                            +"\n1.) Add a recipe review to the database."
                            +"\n2.) Get details of a recipe from the database."
                            +"\n3.) Find other reviews for the recipe."
                            +"\n4.) Exit the app");
            
            int menuChoice = scanner.nextInt();

            switch(menuChoice){
                case 1:
                    System.out.println("Getting recipe add function...");
                    addRecipeToDatabase();
                    break;
                case 2:
                    System.out.println("Getting print recipe data function...");
                    printRecipeFromDatabase();
                    break;
                case 3:
                    System.out.println("Thank you for using the recipe app!");
                    shutDown();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again. Resetting menu...");
                    mainMenu();
                    break;
            }

        }
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
