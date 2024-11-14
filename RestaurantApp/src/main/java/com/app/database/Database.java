package com.app.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters.*;

public class Database
{
  private String connectionString = "";
  //TODO: Make text file and make functionality to read it for connectionString

  private final String DBNAME = "";
  //TODO: Initialize dbname

  public static void main(String[] args)
  { //TODO: Remove main method once Database is finished
    System.out.println("main method for testing purposes only");
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

  public static Document read(String collectionName)
  {
    try(MongoClient mongoclient = MongoClients.create(connectionString))
    {
      MongoDatabase database = mongoclient.getDatabase(DBNAME);

      MongoCollection<Document> reviewCollection =
        database.getCollection(collectionName);

      Document firstDocument = reviewCollection.find().first();
      //This code can only read the first entry. TODO: Find out how to read other entries

      return firstDocument;
    }
    catch(Exception e)
    {
      System.out.println(
        "An error has occured while reading from the database.");
    }
  }
}
