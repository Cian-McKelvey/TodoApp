package com.mckelvey.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// Class is used to hold the mongodb client, database, and collections
public class MongoDBUtils {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> todoCollection;
    // Add user and account collections

    public static void setMongoClient(MongoClient client) {
        mongoClient = client;
    }

    public static void setMongoDatabase(MongoDatabase database) {
        mongoDatabase = database;
    }

    public static void setTodoCollection(String collectionName) {
        todoCollection = mongoDatabase.getCollection(collectionName);
        // Add user and account collection
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoCollection<Document> getTodoCollection() {
        return todoCollection;
    }
}
