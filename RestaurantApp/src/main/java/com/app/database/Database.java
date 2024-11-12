package com.app.main;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters.*;

public class Database
{
  public static void upload(Document d)
  {
    try(MongoClient mongoclient = MongoClients.create(connectionString))
      //TODO: Make text file and make functionality to read it for connectionString

    { //TODO: Insert database name in the getDatabase function below
      MongoDatabase database = mongoclient.getDatabase();
    }
    catch (Exception e)
    {
      System.out.println(
          "An error has occured while uploading to the database.");
      e.printStackTrace();
    }
  }
}
