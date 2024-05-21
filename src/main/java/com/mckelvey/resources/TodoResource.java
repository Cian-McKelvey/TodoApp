package com.mckelvey.resources;

import com.mckelvey.api.Todo;
import com.mckelvey.auth.JWTHandler;
import com.mckelvey.db.MongoDBUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)  // Changes the return types of the methods to json in their http response
public class TodoResource {

    // Imports the MongoDB collection
    private MongoCollection<Document> todoCollection;

    JWTHandler jwtHandler = new JWTHandler();

    // Fetches the collection so that it can be used
    public TodoResource() {
        this.todoCollection = MongoDBUtils.getTodoCollection();
    }


    // Database operations methods


    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTodo(Map<String, Object> jsonData, @HeaderParam("x-access-token") String token) {

        // Only checks for the presence of a token doesn't actually check it
        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required header 'x-access-token'")
                    .build();
        }

        String userId = (String) jsonData.get("userId");
        String content = (String) jsonData.get("content");

        Todo newTodo = new Todo(userId, content);
        String todoJson = newTodo.toJsonString();
        Document todoDocument = Document.parse(todoJson);
        todoCollection.insertOne(todoDocument);

        // Return appropriate status code and message
        return Response.ok().entity("Processed successfully").build();
    }

    // Update todo
    @PUT
    @Path("/{id}/update")
    public Response updateTodo(@PathParam("id") String id, Map<String, Object> jsonData) {

        String content = (String) jsonData.get("content");
        if (content == null || content.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Content must not be empty").build();
        }

        // Update the content field of the todo item
        Document updateResult = todoCollection.findOneAndUpdate(
                eq("todoID", id),
                set("content", content)
        );

        if (updateResult == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Todo not found").build();
        }

        // Return the updated document (re-fetch it from the database to ensure updated version is returned)
        Document updatedTodo = todoCollection.find(eq("todoID", id))
                // Projections are used to hide, or include certain data from the response
                // Here it is hiding the _id value, it serves no purpose.
                .projection(Projections.fields(Projections.excludeId())).first();
        return Response.ok(updatedTodo).build();
    }

    // Delete todo
    @DELETE
    @Path("/{id}/delete")
    public Response deleteTodo(@PathParam("id") String id) {
        try {
            Document deletedTodo = todoCollection.findOneAndDelete(
                    eq("todoID", id)
            );

            if (deletedTodo != null) {
                return Response.ok().entity(id + " has been deleted").build();
            } else {
                System.out.println("THERE WASNT ANY ITEM FOUND TO BE DELETED");
                return Response.status(Response.Status.BAD_REQUEST).entity("No todo with that id present in database").build();
            }
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not perform delete action").build();
        }
    }

    // Fetch all todos
    @GET
    @Path("/get/all")
    public List<Document> getAllTodos() {
        // Fetch all documents from the collection
        List<Document> resultList = new ArrayList<Document>();
        todoCollection.find().projection(Projections.excludeId()).into(resultList);
        return resultList;
    }

    // Fetch all todos by userID - not needed right now

    @GET
    @Path("/token")
    public String getNewTokenTest() {
        return jwtHandler.generateNewJwtToken("example", false);
    }

}
