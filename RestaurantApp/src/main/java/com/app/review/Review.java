package com.app.review;

import java.util.ArrayList;
import java.util.Scanner;
import org.bson.Document;
import com.app.database.Database;

public class Review {

    private boolean isPositive;
    private ArrayList<String> contents;

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

    protected boolean isStopWord(String word){
        for(String stopWord : STOP_WORDS){
            if(stopWord.equals(word)){
                return true;
            }
        }
        return false;
    }

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

    //Method to create Review objects via user input
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

    public void uploadToMongo(String collectionName){
        try{
            //Create new mongodb document from Review object
            Document reviewDocument = new Document()
                .append("isPositive", this.getIsPositive())
                .append("contents", this.getContents());
                
            //upload using Database class
            Database.upload(reviewDocument, collectionName);
            System.out.println("Review successfully uploaded to MongoDB.");
        } catch (Exception e){
            System.out.println("An error occured while uploading to MongoDB");
            e.printStackTrace();
        }
    }
}