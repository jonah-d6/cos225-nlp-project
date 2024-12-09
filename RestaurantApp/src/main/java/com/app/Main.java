package com.app;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.review.Review;
import com.app.database.Database;
import com.app.nlp.Classifier;

/**
 * The Main class holds the functions to start up the program
 * 
 * It has no attributes
 */

public class Main
{

  /**
   * Initializes the connection to the database using the function from Database
   * 
   * @param args
   * 
   * Starts up the program
   */

  public static void main(String[] args)
  {
    Database.initializeConnection();
    startUp();
    Classifier.finalizeTraining();
    Menu.menu();
  }


  /**
   * startUp() initializes the review data from the .csv and uses the info to initialize Review objects
   * It then uploads them to MongoDB via the function in Review
   */


  private static void startUp()
  {
    String fileName = "src/main/resources/restaurant_reviews.csv";
    try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
    {
      String line = reader.readLine();
      line = reader.readLine();//skips the header

      //helper variables for the following loop
      String reviewText;
      Review currentLine;
      List<String> contentsAsList;
      int reviewScore;

      int i = 0; //TESTING PURPOSE COUNTER ONLY
                 
      while (line != null)
      {
        //THE FOLLOWING IS FOR TEST OF 10 LINES
        
        if (i >= 100)
          break;
        i++;
        
        //END OF TESTING CODE

        reviewText = line.substring(0, line.length() - 2);

        //splits the text over spaces and punctuation, and stores as a List
        contentsAsList = Arrays.asList(reviewText.split("[\\p{Punct}\\s]+"));

        //parses the final character of the line as an int
        reviewScore = Integer.parseInt(line.substring(line.length() - 1));

        //since the Review constructor takes a boolean, we need to turn our int into a boolean
        if(reviewScore==0)
          currentLine = new Review(false, new ArrayList<String>(contentsAsList));
        else
          currentLine = new Review(true, new ArrayList<String>(contentsAsList));

        Classifier.updateTraining(currentLine);

        currentLine.uploadToMongo("reviews");

        line = reader.readLine();
      }
    }
    catch(Exception e)
    {
      System.out.println("Something happened while trying to read the file");
      e.printStackTrace();
    }
  }
}
