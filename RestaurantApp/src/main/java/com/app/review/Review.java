package com.app.review;

import java.util.ArrayList;
import java.util.Scanner;
import org.bson.Document;
import com.app.database.Database;

import com.app.Menu;

/**
 * The Review class is the object for the reviews
 * It holds a boolean value, isPositive, which denotes if the review is positive or negative
 * It also holds an arraylist of strings of the contents of the review
 */

public class Review {

    private boolean isPositive;
    private ArrayList<String> contents;

    /**
     * The STOP_WORDS constant is a list of words that are deemed "stop words"
     * These words are deemed to be unimportant to the process of determining the positivity of a review
     */

    public String[] STOP_WORDS = {
        "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", 
        "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", 
        "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", 
        "their", "theirs", "themselves", "what", "which", "who", "whom", "this", 
        "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", 
        "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", 
        "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", 
        "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", 
        "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", 
        "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", 
        "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", 
        "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", 
        "s", "t", "can", "will", "just", "don", "should", "now"
    };

    public Review(boolean isPositive, ArrayList<String> contents){
        this.isPositive = isPositive;
        this.contents = contents;
    }

    public boolean getIsPositive(){
        return isPositive;
    }

    public ArrayList<String> getContents(){
        return contents;
    }

    public void setIsPositive(boolean isPositive){
        this.isPositive = isPositive;
    }

    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }

    public void fillContents(String word){
        contents.add(word);
    }

    /**
     * isStopWord() is a fucntion to be called within the function that trims the stopwords from contents
     * 
     * @param word
     * @return boolean isStopWord
     * 
     * The function takes in an indiviual string and determines if it is in the list of stopwords
     * The function returns a boolean value based on the determiniation
     */

    protected boolean isStopWord(String word){
        for(String stopWord : STOP_WORDS){
            if(stopWord.equals(word)){
                return true;
            }
        }
        return false;
    }

    /**
     * trimContents() trims the array list of contents in the review to remove punctuation and stopwords
     *
     * @return ArrayList<String> trimmedContents
     * 
     * The function returns the contents as a new ArrayList of strings with punctuation and stopwords removed
     */

    public ArrayList<String> trimContents(){
        ArrayList<String> trimmedContents = new ArrayList<>();

        for(String word : this.contents) {
            //remove punctuation
            String checkedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            //add to trimmed list if not stop word and empty
            if(!checkedWord.isEmpty() && !isStopWord(checkedWord)){
                trimmedContents.add(checkedWord);
            }
        }

        return trimmedContents;
    }

    /**
     * createReview() is a function to create a new Review object from a user input review and rating
     * 
     * @return Review newReview
     * 
     * The user is prompted to input the text of their review as well as the rating (1-5)
     * The provided inputs are turned into a new Review object which is returned
     */
    public static Review createReview(){
        try (Scanner scanner = new Scanner(System.in)){

            System.out.println("Enter new review text:");
            String reviewText = scanner.nextLine();

            int rating = 0;
            while (true) {
                System.out.println("Enter rating (1-5):");
                try{
                    rating = Integer.parseInt(scanner.nextLine());
                    if (rating >= 1 && rating <= 5){
                        break; //exit if rating is valid
                    } else {
                        System.out.println("Invalid rating. Enter a number rating between 1 and 5");
                    }
                } catch(NumberFormatException e){
                    System.out.println("Invalid input. Enter a number rating between 1 and 5");
                }
            }

            //Determine if review is positive (>= 3)
            boolean isPositive = rating >= 3;

            //Process the review text and clean
            ArrayList<String> contents = new ArrayList<>();
            String[] words = reviewText.split(" ");
            for(String word : words){
                contents.add(word);
            }

            Review newReview = new Review(isPositive, contents);
            newReview.setContents(newReview.trimContents());

            return newReview;
        }
    }

    /**
     * uploadToMongo() uploads the information from a Review object to MongoDB
     * 
     * @param collectionName
     * 
     * The function takes the attributes of a Review object and writes them to MongoDB
     * If there is a problem connecting to MongoDB that error will be kicked to the user
     * This will call the upload function from Database
     */

    public void uploadToMongo(String collectionName){
        try{
            Document reviewDocument = new Document()
                .append("isPositive", this.getIsPositive())
                .append("contents", this.getContents());
                
            Database.upload(reviewDocument, collectionName);
        } catch (Exception e){
            System.out.println("An error occured while uploading to MongoDB");
            e.printStackTrace();
        }
    }
}
