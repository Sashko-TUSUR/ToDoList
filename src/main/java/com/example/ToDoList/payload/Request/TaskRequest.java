package com.example.ToDoList.payload.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

public class TaskRequest {

    private Long id;
    private String taskName;
   // private Long endTime;
    private Date endTime;
    private boolean status;
    private String description;

}

