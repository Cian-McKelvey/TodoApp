package com.mckelvey.api;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mckelvey.auth.DataHashing;


public class UserAccount {
    // Decide on what user accounts should contain, use what is available from PC part project and add more.

    private String userId;
    private String username;
    private String passwordHash;
    private String email;
    private LocalDateTime createdAt;
    private boolean isAdmin;

    public UserAccount(String username, String email, String password) {
        // Default values
        this.userId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.isAdmin = false;

        // User Preference values
        this.username = username;
        this.email = email;
        this.passwordHash = DataHashing.hashStringData(password);
    }


    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    // Encrypt here too
    public void setPasswordHash(String password) {
        this.passwordHash = DataHashing.hashStringData(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", isAdmin=" + isAdmin +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    // Probably isn't needed but ill leave it for now
    public String toJsonString() {
        return "{" +
                "\"userId\":\"" + userId + "\"," +
                "\"createdAt\":\"" + createdAt + "\"," +
                "\"isAdmin\":" + isAdmin + "," +
                "\"username\":\"" + username + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"passwordHash\":\"" + passwordHash + "\"" +
                "}";
    }

}
