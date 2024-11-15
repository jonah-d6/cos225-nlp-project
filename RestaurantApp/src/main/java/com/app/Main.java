import java.io.*;
import java.util.ArrayList;

import com.app.review.Review;

public class Main
{
  private static void startUp()
  {
    String fileName = "../../../resources/restaurant_reviews.csv";
    try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
    {
      String line = reader.nextLine();
      Review currentLine;
      ArrayList<String> contents;
      int reviewScore;
      while (line != null)
      {
        //TODO: fill up contents with the words in the review
        //TODO: initialize reviewScore with the final value, separated by the comma
        currentLine = (reviewScore, contents);
        line = reader.nextLine();
      }
    }
    catch(Exception e)
    {
      System.out.println("Something happened while trying to read the file");
      e.printStackTrace();
    }
  }
}
