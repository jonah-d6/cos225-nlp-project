package com.app.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;

import com.app.review.Review;

public class Classifier
{
  private static HashSet<String> vocabulary = new HashSet<>();

  private static int numPositiveReviews = 0;
  private static int numNegativeReviews = 0;
  private static int numTotalReviews = 0;

  private static HashMap<String, Integer> positiveWordCount = new HashMap<>();
  private static HashMap<String, Integer> negativeWordCount = new HashMap<>();

  private static HashMap<String, Double> positiveProbabilities = new HashMap<>();
  private static HashMap<String, Double> negativeProbabilities = new HashMap<>();

  public static boolean classify(String text)
  {
    //TODO: Ensure classify method works properly/without error
    List<String> contentsAsList = Arrays.asList(text.split("[\\p{Punct}\\s]+"));
    contentsAsList = trim(contentsAsList);

    double positiveScore = Math.log((double) numPositiveReviews / numTotalReviews);
    double negativeScore = Math.log((double) numNegativeReviews / numTotalReviews);

    for (String word : contentsAsList)
    {
      positiveScore += Math.log(positiveProbabilities.getOrDefault(word, 1.0 / (positiveWordCount.size() + vocabulary.size())));
      negativeScore += Math.log(negativeProbabilities.getOrDefault(word, 1.0 / (negativeWordCount.size() + vocabulary.size())));
    }

    return (positiveScore >= negativeScore);
  }

  public static void updateTraining(Review trainer)
  {
    ArrayList<String> words = trainer.trimContents();
    if (trainer.getIsPositive())
      updateWordCount(positiveWordCount, words);
    else
      updateWordCount(negativeWordCount, words);
  }

  public static void finalizeTraining()
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

  private static double calculateProbability(String word, HashMap<String, Integer> wordCount, int totalWords)
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

  private static void updateWordCount(HashMap<String, Integer> wordCount, ArrayList<String> words)
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

  private static ArrayList<String> trim(List<String> words){
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
