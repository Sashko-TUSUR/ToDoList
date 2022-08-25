package com.example.ToDoList.payload.Response;

import com.example.ToDoList.Model.User;
import lombok.Data;

@Data
public class JwtResponseSignUp {


        private  String accessToken;

        public JwtResponseSignUp(String accessToken) {
            this.accessToken = accessToken;

        }
}
