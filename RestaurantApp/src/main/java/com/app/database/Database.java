package com.app.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import com.mongodb.client.model.Filters.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database
{
  private static String connectionString = "";

  private static final String DBNAME = "COS225NLP-Project";

  public static void main(String[] args)
  { //TODO: Remove main method once Database is finished
    System.out.println("main method for testing purposes only");
    initializeConnection();
  }

  private static void initializeConnection(){
    try(BufferedReader reader = new BufferedReader(new FileReader(
            "src/main/resources/connection.txt"))){
      connectionString = reader.readLine().trim();
      System.out.println("Connection string initialized");
      } catch (IOException e) {
        System.out.println("Error reading connection string from 'connection.txt'");
        e.printStackTrace();
      }
  }

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
      return new Document();
    }

    return documents;
  }

  public static void deleteAllDocuments(String collectionName)
  { //TODO: Make sure this method works
    try(MongoClient mongoClient = MongoClients.create(connectionString))
    {
      MongoDatabase database = mongoClient.getDatabase(DBNAME);
      MongoCollection<Document> reviewCollection =
        database.getCollection(collectionName);

      reviewCollection.deleteMany(new Document());
    }
    catch(Exception e)
    {
      System.out.println(
        "An error has occured while deleting from the database.");
      e.printStackTrace();
    }
  }
}
