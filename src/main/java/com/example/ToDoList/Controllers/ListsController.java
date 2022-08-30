package com.example.ToDoList.Controllers;


import com.example.ToDoList.Model.Lists;
import com.example.ToDoList.Repository.ListsRepository;
import com.example.ToDoList.payload.Request.ListRequest;
import com.example.ToDoList.payload.Request.PutUserRequest;
import com.example.ToDoList.payload.Response.ApiResponse;
import com.example.ToDoList.service.UserDetailsImpl;
import com.example.ToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/lists")
public class ListsController {

    @Autowired
    private ListsRepository listsRepository;
    @Autowired
    private UserService userService;


    //списки пользователя
    @GetMapping()
    public List<Lists> listUser(@AuthenticationPrincipal UserDetailsImpl userDetails)
    {
       return listsRepository.findByAllLists(userDetails.getId());
    }
    /// добавление листа
    @PostMapping("/add")
    public ResponseEntity<?> addList(@Valid @RequestBody ListRequest listRequest, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        userService.saveList(listRequest,userDetails.getId());
        return ResponseEntity.ok(new ApiResponse(true, "Лист добавлен"));
    }
    ///редактирование лист
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editList(@Valid @RequestBody ListRequest listRequest, @PathVariable(value = "id") Long id)
    {
        userService.editList(listRequest,id);
        return ResponseEntity.ok(new ApiResponse(true, "Лист обновлён"));
    }
    // удаление листа посмотреть как удалять все записи этого листа
    @DeleteMapping("del/{id}")
    public ResponseEntity<?> delList(@PathVariable(value = "id") Long id)
    {
        userService.deleteList(id);
        return ResponseEntity.ok(new ApiResponse(true, "Лист удалён"));
    }
    //поделиться списком
    @PutMapping("share/{id}")
    public ResponseEntity<?> shareList(@PathVariable(value = "id") Long id , @RequestBody PutUserRequest putUser)
    {
        userService.putList(putUser,id);
        return ResponseEntity.ok(new ApiResponse(true, "Пользователь добавлен"));
    }

}