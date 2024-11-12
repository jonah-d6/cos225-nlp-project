package com.app.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters.*;

public class Database
{
  public static void main(String[] args)
  { //TODO: Remove main method once Database is finished
    System.out.println("main method for testing purposes only");
  }
  public static void upload(Document d)
  {
    String connectionString = "";
    //TODO: Make text file and make functionality to read it for connectionString

    try(MongoClient mongoclient = MongoClients.create(connectionString))
    {
      //TODO: Initialize database name, collection name
      
      String dbname = "";
      String collectionName = "";

      MongoDatabase database = mongoclient.getDatabase(dbname);

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
}
