package com.example.ToDoList.payload.Request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentRequest {
    private String content;
    private Long timestamp;
}
