package com.example.ToDoList.Controllers;


import com.example.ToDoList.Model.Lists;
import com.example.ToDoList.Model.Tasks;
import com.example.ToDoList.Repository.TasksRepository;
import com.example.ToDoList.Exception.ResourceNotFoundException;
import com.example.ToDoList.payload.Response.ApiResponse;
import com.example.ToDoList.payload.Request.TaskRequest;
import com.example.ToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping()

public class TaskController {
/*
    @Autowired
    UserService userService;
    @Autowired
    TasksRepository tasksRepository;



@PostMapping
public ResponseEntity<?> TaskAdd(@RequestBody TaskRequest taskRequest)
{
    userService.saveTask(taskRequest);
    return ResponseEntity.ok(new ApiResponse(true, "Задача добавлена"));
}


//добавление задачи
@PostMapping()
public ResponseEntity<?> TaskAdd(@RequestBody TaskRequest taskRequest,@PathVariable Lists list)
{
    Tasks tasks = new Tasks(taskRequest.getTask(),taskRequest.getEnd(),list);

    return ResponseEntity.ok(new ApiResponse(true, "Задача добавлена"));


 */

}
