package com.mckelvey.resources;

import com.mckelvey.api.Todo;
import com.mckelvey.db.MongoDBUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)  // Changes the return types of the methods to json in their http response
public class TodoResource {

    // Imports the MongoDB collection
    private MongoCollection<Document> todoCollection;

    // Fetches the collection so that it can be used
    public TodoResource() {
        this.todoCollection = MongoDBUtils.getTodoCollection();
    }

    // Database operations methods
    public void addTodo(Todo todo) {
        String todoJson = todo.toJsonString();
        Document todoDocument = Document.parse(todoJson);
        todoCollection.insertOne(todoDocument);
    }

    // Update todo

    // Delete todo

    // Fetch all todos
    @GET
    @Path("/get/all")
    public List<Document> getAllTodos() {
        // Fetch all documents from the collection
        List<Document> documents = new ArrayList<>();
        try (MongoCursor<Document> cursor = todoCollection.find().iterator()) {
            while (cursor.hasNext()) {
                documents.add(cursor.next());
            }
        }
        return documents;
    }

    // Fetch all todos by userID - not needed right now
}
