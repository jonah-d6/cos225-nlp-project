package com.app.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database
{
  private static String connectionString = "";

  private static final String DBNAME = "COS225NLP-Project";

  /**
   * initializeConnection() gets the connection string from .txt file
   * It kicks an error if the file is not found or the connection is not successful
   * If sucessful, it will initialize the connection string into a string attribute
   */
  public static void initializeConnection(){
    try(BufferedReader reader = new BufferedReader(new FileReader(
            "src/main/resources/connection.txt"))){
      connectionString = reader.readLine();
      System.out.println("Connection string initialized");
      } catch (IOException e) {
        System.out.println("Error reading connection string from 'connection.txt'");
        e.printStackTrace();
      }
  }

  /**
   * upload() handles the uploading to MongoDB after the connection string has been initialized
   * 
   * @param document d - Review obj
   * @param String collectionName
   * 
   * This will initialize the data collection in MongoDB with the provided collectionName
   * This will also upload a Review object to MongoDB and is called in the uploadToMongo() in the Review class
   */
  public static void upload(Document d, String collectionName)
  {
    try(MongoClient mongoclient = MongoClients.create(connectionString))
    {
      MongoDatabase database = mongoclient.getDatabase(DBNAME);

      MongoCollection<Document> reviewCollection =
        database.getCollection(collectionName);

      reviewCollection.insertOne(d);
    }
    catch (Exception e)
    {
      System.out.println(
          "An error has occured while uploading to the database.");
      e.printStackTrace();
    }
  }

  /**
   * read() utilizes the connectionString attribute to connect with MongoDB
   * 
   * @param collectionName
   * @return
   * 
   * This method will read reviews from the MongoDB data and store them in an array
   */

  public static List<Document> read(String collectionName)
  {
    List<Document> documents = new ArrayList<>();
    try(MongoClient mongoclient = MongoClients.create(connectionString))
    {
      MongoDatabase database = mongoclient.getDatabase(DBNAME);

      MongoCollection<Document> reviewCollection =
        database.getCollection(collectionName);

      //get all documents
      FindIterable<Document> iterable = reviewCollection.find();
      MongoCursor<Document> cursor = iterable.iterator();

      while(cursor.hasNext()){
        Document doc = cursor.next();
        documents.add(doc);
      }
      System.out.println("Sucessfully read all documents from collection");
    }
    catch(Exception e)
    {
      System.out.println(
        "An error has occured while reading from the database.");
      e.printStackTrace();
      return new ArrayList<Document>();
    }

    return documents;
  }

  /**
   * deleteAllDocument() also uses the connectionString atrribute to connect with MongoDB
   * 
   * @param collectionName
   * 
   * This method connects with MongoDB and will remove data from the database
   */

  public static void deleteAllDocuments(String collectionName)
  { 
    try(MongoClient mongoClient = MongoClients.create(connectionString))
    {
      MongoDatabase database = mongoClient.getDatabase(DBNAME);
      MongoCollection<Document> reviewCollection =
        database.getCollection(collectionName);

      reviewCollection.deleteMany(new Document());
    }
    catch(Exception e)
    {
      System.out.println("An error has occured while deleting from the database.");
      e.printStackTrace();
    }
  }
}
