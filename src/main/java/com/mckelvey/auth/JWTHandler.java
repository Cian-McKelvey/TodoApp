package com.mckelvey.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.mckelvey.Constants.*;

import java.util.Date;

public class JWTHandler {

    private static final Logger logger = LoggerFactory.getLogger(JWTHandler.class);

    private static final byte[] JWT_SIGNING_KEY_BYTES = JWT_SIGNING_KEY.getBytes();
    private static final SecretKey GENERATED_ENCRYPTED_KEY = Keys.hmacShaKeyFor(JWT_SIGNING_KEY_BYTES);


    public static String generateNewJwtToken(String username, boolean adminStatus) {
        // Set the expiration time to 30 minutes from now
        Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

        // Create the JWT
        String jwt = Jwts.builder().subject(username).claims()
                .add("isAdmin", adminStatus).and()
                .expiration(expirationTime)
                .signWith(GENERATED_ENCRYPTED_KEY)
                .compact();

        logger.info("New Token has been generated for profile: " + username);

        return jwt;
    }

    public static boolean isTokenValid(String jwtString) {
        try {
            // Parse the token to get the claims
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(GENERATED_ENCRYPTED_KEY)
                    .build()
                    .parseSignedClaims(jwtString);

            // Get the body of the claims
            Claims claims = claimsJws.getBody();

            // Check if the token is expired
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                logger.error("Failed login with expired token: " + jwtString);
                return false;
            }

            return true;
        } catch (JwtException e) {
            // Invalid signature/claims
            logger.error("Invalid JWT signature" + e);
            return false;
        } catch (Exception e) {
            logger.error("ERROR" + e);
            return false;
        }
    }

    public boolean isTokenBlacklisted() {
        return false;
    }

    public boolean verifyAdminAndTokenStatus(String jwtString) {
        try {
            // Parse the token to get the claims
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(GENERATED_ENCRYPTED_KEY)
                    .build()
                    .parseSignedClaims(jwtString);

            // Get the body of the claims
            Claims claims = claimsJws.getBody();

            // Check if the token is expired
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                logger.error("Failed login with expired token: " + jwtString);
                return false;
            }

            // Add a check on admin being true
            // Check if the user is an admin
            boolean isAdmin = claims.get("isAdmin", Boolean.class);
            if (!isAdmin) {
                logger.info("User is not an admin");
                return false;
            }

            return true;
        } catch (JwtException e) {
            // Invalid signature/claims
            logger.error("Invalid JWT signature" + e);
            return false;
        } catch (Exception e) {
            logger.error("ERROR" + e);
            return false;
        }
    }

}
