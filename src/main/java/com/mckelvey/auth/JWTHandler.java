package com.mckelvey.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;


import static com.mckelvey.Constants.*;

import java.util.Date;

public class JWTHandler {

    private static final byte[] JWT_SIGNING_KEY_BYTES = JWT_SIGNING_KEY.getBytes();
    private static final SecretKey GENERATED_ENCRYPTED_KEY = Keys.hmacShaKeyFor(JWT_SIGNING_KEY_BYTES);

    public String generateNewJwtToken(String username, boolean adminStatus) {
        // Set the expiration time to 30 minutes from now
        Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

//                .setSubject(username)
//                .claim("isAdmin", adminStatus)
//                .setExpiration(expirationTime)
//                .signWith(SignatureAlgorithm.HS256, JWT_SIGNING_KEY)
//                .compact();

        // Create the JWT
        String jwt = Jwts.builder().subject(username).claims()
                .add("isAdmin", adminStatus).and()
                .expiration(expirationTime)
                .signWith(GENERATED_ENCRYPTED_KEY)
                .compact();

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
