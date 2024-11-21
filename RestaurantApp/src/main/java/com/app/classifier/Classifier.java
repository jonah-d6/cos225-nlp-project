package com.app.classifier;

public class Classifier
{
  public boolean classify(String[] words)
  {
    P(+ve) = numPosDocs / numTotalDocs;
    P(-ve) = numNegDocs / numTotalDocs;

    P(R|+ve) = P(+ve);

    for (String word : words)
    { //adding vocabulary to denominator is optional here
      P(word|+ve) = (wordCountInPosContext+1) / (allCountInNegContext+vocabulary);
      P(word|-ve) = (wordCountInNegContext+1) / (allCountInNegContext+vocabulary);

      P(R|+ve) *= P(word|+ve);
      P(R|-ve) *= P(word|-ve);
    }
  }
}
