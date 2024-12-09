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
    //TODO: Ensure to call all the proper classes in Main.java
  }

  public boolean classify(String text)
  {
    //TODO: Ensure classify method works properly/without error
    List<String> contentsAsList = Arrays.asList(text.split("[\\p{Punct}\\s]+"));
    contentsAsList = trim(contentsAsList);

    double positiveScore = Math.log((double) numPositiveReviews / numTotalReviews);
    double negativeScore = Math.log((double) numNegativeReviews / numTotalReviews);

    for (String word : contentsAsList)
    {
      positiveScore += Math.log(positiveProbablities.getOrDefault(word, 1.0 / (positiveWordCount.size() + vocabulary.size())));
    {
      negativeScore += Math.log(negativeProbablities.getOrDefault(word, 1.0 / (negativeWordCount.size() + vocabulary.size())));
    }

    return (positiveScore >= negativeScore)
  }

  public void updateTraining(Review trainer)
  {
    ArrayList<String> words = trainer.trimContents();
    if (review.getIsPositive())
      updateWordCount(positiveWordCount, words);
    else
      updateWordCount(negativeWordCount, words);
  }

  public void finalizeTraining()
  {
    int totalPositiveWords = positiveWordCount.values().stream().mapToInt(Integer::intValue).sum();
    int totalNegativeWords = negativeWordCount.values().stream().mapToInt(Integer::intValue).sum();

    for (String word : vocabulary)
    {
      double positiveProbability = calculateProbability(word, positiveWordCount, totalPositiveWords);
      double negativeProbability = calculateProbability(word, negativeWordCount, totalNegativeWords);

      positiveProbabilities.put(word, positiveProbability);
      negativeProbabilities.put(word, negativeProbability);
    }
  }

  private double calculateProbability(String word, HashMap<String, Integer> wordCount, int totalWords)
  {
    if (wordCount.containsKey(word))
    {
      return (double) wordCount.get(word) / totalWords;
    }
    else
    {
      return 1.0 / (totalWords + vocabulary.size());
    }
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

  private ArrayList<String> trim(List<String> words){
    ArrayList<String> trimmedContents = new ArrayList<>();

    for(String word : words) {
      //remove punctuation
      String checkedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

      //add to trimmed list if not stop word and empty
      if(!checkedWord.isEmpty() && !Review.isStopWord(checkedWord)){
        trimmedContents.add(checkedWord);
      }
    }

    return trimmedContents;
  }
  
}
