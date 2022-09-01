package com.example.ToDoList.payload.Request;


import lombok.Data;

@Data

public class TaskRequest {

    private Long id;
    private String taskName;
    private Long endTime;
    private boolean status;
    private String description;

}

