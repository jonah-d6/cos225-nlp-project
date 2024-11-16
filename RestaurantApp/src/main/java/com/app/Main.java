package com.app;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.review.Review;

public class Main
{
  public static void main(String[] args)
  {
    startUp();
  }
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

      while (line != null)
      {
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

        currentLine.uploadToMongo(""/*TODO: insert collection name here*/);

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
