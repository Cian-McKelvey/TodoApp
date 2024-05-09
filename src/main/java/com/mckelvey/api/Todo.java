package com.mckelvey.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Todo {

    private String todoID;
    private String userID;
    private String content;
    private String creationDate;

    public Todo(String userID, String content) {
        this.todoID = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        this.creationDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.userID = userID;
        this.content = content;
    }

    public String getTodoID() {
        return todoID;
    }

    public String getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String toJsonString() {
        return "{" +
                "\"todoID\":\"" + todoID + "\"," +
                "\"userID\":\"" + userID + "\"," +
                "\"content\":\"" + content + "\"," +
                "\"creationDate\":\"" + creationDate + "\"" +
                "}";
    }
}
