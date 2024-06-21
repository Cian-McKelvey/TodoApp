package com.mckelvey.resources;


import com.mckelvey.api.UserAccount;
import com.mckelvey.api.UserProfile;
import com.mckelvey.db.MongoDBUtils;
import com.mongodb.client.MongoCollection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.Document;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;


@Path("/users/account")
@Produces(MediaType.APPLICATION_JSON)  // Changes the return types of the methods to json in their http response
public class AccountResource {


    // Imports the MongoDB collection
    private MongoCollection<Document> accountCollection;
    private MongoCollection<Document> profileCollection;

    public AccountResource () {
        this.accountCollection = MongoDBUtils.getAccountCollection();
        this.profileCollection = MongoDBUtils.getProfileCollection();
    }


    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewUser(Map<String, Object> jsonData) {

        String username = (String) jsonData.get("username");
        String email = (String) jsonData.get("email");
        String password = (String) jsonData.get("password");

        if (username == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required field: username")
                    .build();
        }

        if (email == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required field: email")
                    .build();
        }

        if (password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required field: password")
                    .build();
        }

        try {
            // Password is hashed when constructing, no need for password hashing here
            UserAccount newUserAccount = new UserAccount(username, email, password);
            UserProfile newUserProfile = UserProfile.generateAssociatedProfile(newUserAccount);

            System.out.println(newUserAccount);
            System.out.println(newUserProfile);

            Document newUserAccountDoc = Document.parse(newUserAccount.toJsonString());
            Document newUserProfileDoc = Document.parse(newUserProfile.toJsonString());

            // Write both to database here
            accountCollection.insertOne(newUserAccountDoc);
            profileCollection.insertOne(newUserProfileDoc);

            return Response.status(Response.Status.OK)
                    .entity("Account and Profile Created")
                    .build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Error." + e)
                    .build();
        }

    }

    @DELETE
    @Path("/{id}/delete")
    public Response deleteExistingUser(@PathParam("id") String id) {

        System.out.println("\n\n\n\n\n ENDPOINT CALLED \n\n\n\n\n");

        try {
            Document deletedAccountDoc = accountCollection.findOneAndDelete(
                    eq("accountId", id)
            );

            Document deleteProfileDoc = profileCollection.findOneAndDelete(
                    eq("profileId", id)
            );

            if (deletedAccountDoc != null && deleteProfileDoc != null)
                return Response.status(Response.Status.OK)
                        .entity("Account has been deleted")
                        .build();

            else
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Could not find account")
                        .build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal Error, " + e)
                    .build();
        }
    }

}
