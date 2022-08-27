package com.example.ToDoList.Controllers;


import com.example.ToDoList.Model.Lists;
import com.example.ToDoList.Repository.ListsRepository;
import com.example.ToDoList.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/home")
public class ListsController {

    @Autowired
    private ListsRepository listsRepository;

    //списки пользователя
    @GetMapping()
    public List<Lists> listUser(UserDetailsImpl userDetails)
    {
        System.out.print(userDetails.getId());
       return listsRepository.findByLists(userDetails.getId());
    }


}