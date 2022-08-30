package com.example.ToDoList.Controllers;


import com.example.ToDoList.Model.Tasks;
import com.example.ToDoList.Repository.TasksRepository;
import com.example.ToDoList.payload.Request.CommentRequest;
import com.example.ToDoList.payload.Request.TaskRequest;
import com.example.ToDoList.payload.Response.ApiResponse;
import com.example.ToDoList.service.UserDetailsImpl;
import com.example.ToDoList.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/tasks")

public class TaskController {

    @Autowired
    UserService userService;
    @Autowired
    TasksRepository tasksRepository;


////добавление задачи
@PostMapping("/add/{id}")
public ResponseEntity<?> TaskAdd(@Valid @RequestBody TaskRequest taskRequest, @PathVariable(value = "id") Long id)
{
    userService.saveTask(taskRequest,id);
    return ResponseEntity.ok(new ApiResponse(true, "Задача добавлена"));
}
    /////редактирование задачи
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editTask(@Valid @RequestBody TaskRequest taskRequest, @PathVariable(value = "id") Long id)
    {
        userService.editTask(id,taskRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Задача обновлена"));
    }

    // удаление задачи
    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> delTask(@PathVariable(value = "id") Long id)
    {
        userService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse(true, "Задача удалёна"));
    }


    //оставить коммент
    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable(value = "id") Long id , @Valid @RequestBody CommentRequest commentRequest,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        userService.addComment(userDetails.getId(),commentRequest,id);
        return ResponseEntity.ok(new ApiResponse(true, "Комментарий добавлен "));
    }



    //вывод всех задач пользователя
    @GetMapping
    @JsonIgnoreProperties({ "" })
    public List<Tasks> taskUser(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {

      return tasksRepository.findByAllTask(userDetails.getId());
    }




}
