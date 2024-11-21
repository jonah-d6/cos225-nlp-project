package com.app.classifier

public class Classifier
{
  P(+ve) = numPosDocs / numTotalDocs;
  P(-ve) = numNegDocs / numTotalDocs;

  for (String word : review)
  {
    P(word|+ve) = wordCountInPosContext / allCountInNegContext;
    P(word|-ve) = wordCountInNegContext / allCountInNegContext;
  }

  P(R|+ve) = P(+ve) * {PRODUCT(P(word|+ve))}
  P(R|-ve) = P(-ve) * {PRODUCT(P(word|-ve))}
  
}
