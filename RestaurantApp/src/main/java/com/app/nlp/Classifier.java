package com.app.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;

public class Classifier
{
  private HashSet<String> vocabulary = new HashSet<>();

  private int numPositiveReviews = 0;
  private int numNegativeReviews = 0;
  private int numTotalReviews = 0;

  private HashMap<String, Integer> positiveWordCount = new HashMap<>();
  private HashMap<String, Integer> negativeWordCount = new HashMap<>();

  private HashMap<String, Double> positiveProbabilites = new HashMap<>();
  private HashMap<String, Double> negativeProbabilites = new HashMap<>();

  public Classifier()
  {
    //TODO: Determine whether or not items in classifier should be static
  }

  public boolean classify(String text)
  {
    List<String> contentsAsList = Arrays.asList(reviewText.split("[\\p{Punct}\\s]+"));
    //TODO: finish classify method
  }

  public void updateTraining(Review trainer)
  {
    ArrayList<String> words = trainer.trimContents();
    if (review.getIsPositive())
      updateWordCount(positiveWordCount, words);
    else
      updateWordCount(negativeWordCount, words);
  }

  private void updateWordCount(HashMap<String, Integer> wordCount, ArrayList<String> words)
  {
    for (String word : words)
    {
      if (wordCount.containsKey(word))
        wordCount.put(word, wordCount.get(word) + 1);
      else
        wordCount.put(word, 1);
      vocabulary.add(word);
    }
  }
  
}
