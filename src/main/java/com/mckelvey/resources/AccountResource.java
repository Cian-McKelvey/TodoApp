package com.mckelvey.resources;


import com.mckelvey.api.UserAccount;
import com.mckelvey.api.UserProfile;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

import static com.mckelvey.auth.JWTHandler.isTokenValid;


@Path("/users/account")
@Produces(MediaType.APPLICATION_JSON)  // Changes the return types of the methods to json in their http response
public class AccountResource {

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewUser(Map<String, Object> jsonData, @HeaderParam("x-access-token") String token) {

        if (token == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required header 'x-access-token'")
                    .build();
        }

        boolean validTokenStatus = isTokenValid(token);
        if (validTokenStatus) {

            String username = (String) jsonData.get("username");
            String email = (String) jsonData.get("email");
            String password = (String) jsonData.get("password");

            // Need to add some checks here such as values are present

            // Password is hashed when constructing, no need for password hashing here
            UserAccount newUserAccount = new UserAccount(username, email, password);
            UserProfile newUserProfile = UserProfile.generateAssociatedProfile(newUserAccount);



            // Then write these to database in their respective collections
            return Response.status(Response.Status.OK)
                    .entity("Account and Profile Created")
                    .build();

        } else {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("JWT is either not valid, or has expired")
                    .build();
        }

    }

}
