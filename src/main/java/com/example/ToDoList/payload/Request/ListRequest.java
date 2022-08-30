package com.example.ToDoList.payload.Request;

import lombok.Data;

@Data
public class ListRequest {

     private Long id;
     private String ListName;
     private String color;
}
