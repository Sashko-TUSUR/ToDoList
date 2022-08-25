package com.example.ToDoList.payload.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
