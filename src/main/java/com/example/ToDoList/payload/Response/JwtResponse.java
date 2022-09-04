package com.example.ToDoList.payload.Response;

import com.example.ToDoList.Model.User;
import lombok.Data;

import java.util.Optional;

@Data
public class  JwtResponse {
    private  String accessToken;
    private String refreshToken;
    private Optional<User> user;

    public JwtResponse(String accessToken, Optional<User> user) {
        this.accessToken = accessToken;
        this.user = user;
    }


}
