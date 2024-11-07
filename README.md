# cos225-nlp-project

COS 225
Names: Dylan Alvord, JT Kearns, Jonah Dean
Date: 10.25.2024
Dataset Link: https://huggingface.co/datasets/pachequinho/restaurant_reviews

Problem: Our project addresses classification, where restaurant reviews are automatically classified as either positive or negative based on the content of the review. This will help to identify/evaluate customer satisfaction, as well as identify potential improvements that may need to be made.

Domain: The domain for the project is all short restaurant reviews.

Rationale: We chose this dataset for the (hopeful) practical application of the finished product. When complete, the program will be able to compare the reviews in the dataset to a review given by a user. That would then be classified as positive or negative based on the analysis of the data. We aimed to find a domain that included written words that could be classified as positive or negative, as this seemed ideal for language processing.

Key Features: 
- Data Processing - Read information from the database and process it into a comprehensive format
- Feature Extraction - Trim content of reviews down into key identifying words by removing punctuation, connecting words (is, and, was, etc.)
- Classification Model - Classify words into categories based on how often they appear in positive versus negative reviews


Dataset: The dataset, from Hugging Face (linked in document header), contains a large number of restaurant reviews, each labeled as positive or negative. Each entry includes the text of the review, and a label of 0 or 1, for negative or positive respectively. This provides a fairly straightforward model for NLP.

Further Improvement: This program can be expanded upon as well. Additional features in the program can improve the project in some way, shape, or form. Some ideas we came up with for what this might be are:
- Configure the user input in a way that the inputs would be reviews for a specific restaurant and update the rating of the restaurant based on the positive and negative reviews
- Provide user functionality to be able to access a list of reviews ranked by most positive or most negative based on the score given by our system
