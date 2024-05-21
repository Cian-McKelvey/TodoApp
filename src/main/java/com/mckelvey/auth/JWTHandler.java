package com.mckelvey.auth;

import static com.mckelvey.Constants.*;

import com.mckelvey.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTHandler {

    public String generateNewJwtToken(String userId, boolean adminStatus) {
        // Set the expiration time to 30 minutes from now
        Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // Create the JWT
        String jwt = Jwts.builder()
                .setSubject(userId)
                .claim("isAdmin", adminStatus)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, JWT_SIGNING_KEY)
                .compact();

        //System.out.println("JWT: " + jwt);
        return jwt;
    }

    public boolean isValidToken() {
        return false;
    }

    public boolean isTokenBlacklisted() {
        return false;
    }

    public boolean isUserAdmin() {
        return false;
    }

}
