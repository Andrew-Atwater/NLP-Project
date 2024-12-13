package com.recipeapp.nlp;

import java.util.HashMap;
import java.util.HashSet;

import org.bson.BsonValue;

import com.recipeapp.recipe.RecipeReview;


public class RecipeClassifier {
    private Processor processor;
    private HashSet<String> vocabulary = new HashSet<>();
    // You want to create as many word count hashmaps, probability hash maps, and attributes to store the count
    //  as the number of classes in your dataset. For example, if your dataset has three types of reviews, tasty, 
    // nottasty, and neutral, you would create three hashmaps for word count, for probabilities, and three attributes 
    // to store the count of each type of review.

    private int numPositiveReviews = 0;
    private int numNegativeReviews = 0;
    private int totalReviews = 0;
    private HashMap<String, Integer> positiveWordCount = new HashMap<>();
    private HashMap<String, Integer> negativeWordCount = new HashMap<>();
    private HashMap<String, Double> positiveProbabilities = new HashMap<>();
    private HashMap<String, Double> negativeProbabilities = new HashMap<>();
    public RecipeClassifier(Processor processor) {
        this.processor = processor;
    }
    /*
     * This method adds a review from the training set and updates the word count
     * 
     * @param id: The id of the review
     * @param review: The MovieReview object
     * 
     * @return void
     */
    public void addSample(BsonValue id, RecipeReview review) {
        String[] words = processor.processText(review.getReview());
        if (review.getSentiment().equals("tasty")) {
            updateWordCount(positiveWordCount, words);
            numPositiveReviews++;
        } else {
            updateWordCount(nottastyWordCount, words);
            numnottastyReviews++;
        }
        totalReviews++;
    }
    /*
     * This method updates the word count hashmaps which is called by the addSample method
     * 
     * @param wordCount: The hashmap to update
     * @param words: The words to add to the hashmap
     * 
     * @return void
     */
    private void updateWordCount(HashMap<String, Integer> wordCount, String[] words) {
        for (String word : words) {
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
            vocabulary.add(word);
        }
    }
    /*
     * In this method we calculate the prior probabilities of each class and the
     * 
     * @return void
     */
    public void train() {
        int totaltastyWords = tastyWordCount.values().stream().mapToInt(Integer::intValue).sum();
        int totalnottastyWords = nottastyWordCount.values().stream().mapToInt(Integer::intValue).sum();

        for (String word : vocabulary) {
            double tastyProbability = calculateProbability(word, tastyWordCount, totaltastyWords);
            double nottastyProbability = calculateProbability(word, nottastyWordCount, totalnottastyWords);

            tastyProbabilities.put(word, tastyProbability);
            nottastyProbabilities.put(word, nottastyProbability);
        }
    }
    /*
     * This method calculates the probability of a seeing word in given a class
     * 
     * @param word: The word to calculate the probability for
     * @param wordCount: The word count hashmap for the class
     * @param totalWords: The total number of words in the class
     * 
     * @return double: The probability of the word given the class
     */
    private double calculateProbability(String word, HashMap<String, Integer> wordCount, int totalWords) {
        if (wordCount.containsKey(word)) {
            return (double) wordCount.get(word) / totalWords;
        } else {
            return 1.0 / (totalWords + vocabulary.size());
        }
    }
    /*
     * This method classifies a review as tasty or nottasty. It calculates the
     * score for each class and returns the class with the highest score
     * 
     * @param review: The review to classify
     * 
     * @return String: The classification of the review
     */
    public String classify(String review) {
        String[] words = processor.processText(review);

        double positiveScore = Math.log((double) numPositiveReviews / totalReviews);
        double negativeScore = Math.log((double) numNegativeReviews / totalReviews);

        for (String word : words) {
            tastyScore += Math.log(tastyProbabilities.getOrDefault(word, 1.0 / (tastyWordCount.size() + vocabulary.size())));
            nottastyScore += Math.log(nottastyProbabilities.getOrDefault(word, 1.0 / (nottastyWordCount.size() + vocabulary.size())));
        }

        if (positiveScore > negativeScore) {
            return "tasty";
        } else {
            return "not tasty";

        }
    }
}