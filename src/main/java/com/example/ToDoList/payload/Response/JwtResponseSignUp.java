package com.example.ToDoList.payload.Response;

import lombok.Data;

@Data
public class JwtResponseSignUp {


        private  String accessToken;

        public JwtResponseSignUp(String accessToken) {
            this.accessToken = accessToken;

        }
}
