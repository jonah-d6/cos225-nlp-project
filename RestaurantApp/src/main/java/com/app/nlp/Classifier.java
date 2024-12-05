package com.app.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
  }

  public void updateTraining(Review trainer)
  {
    ArrayList<String> words = trainer.trimContents();
    if (review.getIsPositive())
      updateWordCount(positiveWordCount, words);
    else
      updateWordCount(negativeWordCount, words);
  }
  
}