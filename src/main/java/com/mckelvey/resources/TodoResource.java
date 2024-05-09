package com.mckelvey.resources;

import com.mckelvey.api.Todo;
import com.mckelvey.db.MongoDBUtils;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class TodoResource {

    // Imports the MongoDB collection
    private MongoCollection<Document> todoCollection;

    // Fetches the collection so that it can be used
    public TodoResource() {
        this.todoCollection = MongoDBUtils.getTodoCollection();
    }

    // Database operations methods
    public void addTodo(Todo todo) {
        // Use todoCollection to interact with the MongoDB collection ...
    }

    // Update todo

    // Delete todo

    // Fetch all todos

    // Fetch all todos by userID - not needed right now
}
