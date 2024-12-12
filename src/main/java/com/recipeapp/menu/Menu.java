package com.recipeapp.menu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.recipeapp.database.Database;
import com.recipeapp.recipe.Recipe;
import org.bson.BsonValue;
import com.mongodb.client.result.InsertOneResult;
import com.recipeapp.recipe.RecipeReview;
import com.recipeapp.nlp.TFIDF;
import com.recipeapp.nlp.RecipeClassifier;
import com.recipeapp.nlp.RecipeRecommender;
import com.recipeapp.nlp.Processor;


public class Menu {

    private Processor processor = new Processor("src/main/resources/listOfStopWords.txt");
    private TFIDF tfidf = new TFIDF(processor);
    private RecipeClassifier classifier = new RecipeClassifier(processor);

    /*
     * This method is called before showing the menu options to the user. It creates the necessary collections in the database.
     * It also parses the necessary CSV files and populates the collection.
     */
    public void startUp() {
        // Create a collection in the database to store Recipe objects
        Database recipeDatabase = new Database("recipe_app_database", "recipe_data");
        recipeDatabase.createCollection();
        Database reviewDatabase = new Database("recipe_app_database", "recipe_reviews");
        reviewDatabase.createCollection();
        // Parse test data
        String txtFile = "src/main/resources/recipe_data_test.txt";
        String line;
        String delimiter = "#";
        int lineCounter = 0;  //for error testing
        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
            // Skip the first header line
            br.readLine();
            lineCounter++;   //for error testing
            while ((line = br.readLine()) != null) {
                try {  //for error testing
                    lineCounter++;  //for error testing
                    String[] recipeData = line.split(delimiter);
                    String recipeNames = recipeData[0];
                    Integer thumbsUp = Integer.parseInt(recipeData[1]);
                    Integer thumbsDown = Integer.parseInt(recipeData[2]);
                    String reviewContent = recipeData[3];
                    // System.out.println(line);
                    Recipe recipeObject = new Recipe(recipeNames, thumbsUp, thumbsDown, reviewContent);
                    InsertOneResult result = recipeDatabase.addToDatabase(recipeObject.getDocument());
                    BsonValue id = result.getInsertedId();
                    tfidf.addSample(id, recipeObject.getReviewText());
                } catch (ArrayIndexOutOfBoundsException e ){   //shows where txt file doesn't fit format
                    System.out.println("Encountered issue on line "+ lineCounter +". Sending you back to the main menu..."); //error catch statement
                    mainMenu();
                }
            }
            tfidf.calculateIDF();
        } catch (IOException e) {
            System.out.println("IOException occurred. Sending you back to the main menu...");
            mainMenu();
        } 
        // Parse the recipe_review_train.csv
        String reviewTXTFile = "src/main/resources/recipe_review_train.csv";
        String reviewLine;
        try (BufferedReader br = new BufferedReader(new FileReader(reviewTXTFile))) {
            // Skip the first header line
            br.readLine();
            while ((reviewLine = br.readLine()) != null) {
                String[] reviewData = reviewLine.split(delimiter);
                String review = reviewData[0];
                String sentiment = reviewData[1];
                RecipeReview reviewObject = new RecipeReview(review, sentiment);
                InsertOneResult result = reviewDatabase.addToDatabase(reviewObject.getDocument());
                classifier.addSample(result.getInsertedId(), reviewObject);
            }
            classifier.train();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException occurred. Sending you back to the main menu...");
            mainMenu();
        }
    }

    /*
     * This method is called whenever user selects the Exit option. This method 
     * deletes the collection created in the database.
     */
    public void shutDown() {
        Database recipeDatabase = new Database("recipe_app_database", "recipe_data");
        recipeDatabase.deleteCollection();
        Database reviewDatabase = new Database("recipe_app_database", "recipe_reviews");
        reviewDatabase.deleteCollection();
        // System.out.println("Trying to delete...");  //for error testing
    }

    public void addRecipeToDatabase(Scanner scanner) {

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

            InsertOneResult result = recipeDatabase.addToDatabase(userRecipe.getDocument());
            tfidf.addSample(result.getInsertedId(), userRecipe.getReviewText());
            System.out.println("Recipe " + newRecipeName + " successfully added to the database!");

    }

    public void findSimilarRecipes(Scanner scanner) {
        System.out.println("Please enter review of the recipe");
        String newReviewContent = scanner.nextLine();
        System.out.println("How many recipes would you like to see?");
        int numRecommendations = scanner.nextInt();
        System.out.println("Finding similar recipes...");
        RecipeRecommender recommender = new RecipeRecommender(processor, tfidf);
        ArrayList<Recipe> recommendations = recommender.recommendRecipes(newReviewContent, numRecommendations);
        for (Recipe recipe : recommendations) {
            System.out.println("Name: " + recipe.getRecipeName());
            System.out.println("Review: " + recipe.getReviewText());
            System.out.println("Thumbs Up: " + recipe.getThumbUpCount());
            System.out.println("Thumbs Down: " + recipe.getThumbDownCount());
        }

    }

    public void classifyRecipeReview(Scanner scanner) {
        System.out.println("Please enter the review of the recipe");
        String review = scanner.nextLine();
        String sentiment = classifier.classify(review);
        System.out.println("The sentiment of the review is: " + sentiment);
    }

    public static void main(String[] args) {
        System.out.println("Initializing the recipe app...");
        //Call the startUp method
        Menu menu = new Menu();
        menu.startUp();
        menu.mainMenu();  //actual menu, made navigatable from IO errors
        // Ideally you want to make this menu an endless loop until the user enters to exit the app. (done)
        // When they select the option, you call the shutDown method. (done)
    }

    public void mainMenu(){   
        System.out.println("Hello! Welcome to the movie app!");
        Scanner scanner = new Scanner(System.in);
            int choice = 0;
            while (choice != 6) {
                System.out.println("Please select one of the following options:"
                                +"\n1.) Add a recipe review to the database."
                                +"\n2.) Get details of a recipe from the database."
                                +"\n3.) Find similar movies."
                                +"\n4.) Classify movie review."
                                +"\n5.) Exit the app");
                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    continue;
                }
                switch(choice){
                    case 1:
                        System.out.println("Getting recipe addition function...");
                        addRecipeToDatabase(scanner);
                        break;
                    case 2:
                        System.out.println("Getting recipe printing function...");
                        //printRecipeFromDatabase();
                        System.out.println("This function is still under construction, please pick another!");
                        break;
                    case 3:
                        System.out.println("Getting similar recipe function...");
                        findSimilarRecipes(scanner);
                        break;
                    case 4:
                        System.out.println("Getting recipe classifier function...");
                        classifyRecipeReview(scanner);
                        break;
                    case 5:
                        System.out.println("Goodbye! Thank you for using the recipe app!");
                        shutDown();
                        scanner.close();
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again. Resetting menu...");
                        break;
                }
            }
    }
}
