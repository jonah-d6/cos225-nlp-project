import java.io.*;
import com.app.Review;

public class Main
{
  private static void startUp()
  {
    String fileName = "../../../resources/restaurant_reviews.csv"
    try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
    {
    }
    catch(Exception e)
    {
      System.out.println("Something happened while trying to read the file");
      e.printStackTrace();
    }
  }
}
