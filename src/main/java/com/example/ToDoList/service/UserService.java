package com.example.ToDoList.service;

import com.example.ToDoList.Enumeration.EnumRole;
import com.example.ToDoList.Model.Lists;
import com.example.ToDoList.Model.Role;
import com.example.ToDoList.Model.Tasks;
import com.example.ToDoList.Model.User;
import com.example.ToDoList.Repository.ListsRepository;
import com.example.ToDoList.Repository.RoleRepository;
import com.example.ToDoList.Repository.TasksRepository;
import com.example.ToDoList.Exception.ResourceNotFoundException;
import com.example.ToDoList.Repository.UserRepository;
import com.example.ToDoList.payload.Request.ListRequest;
import com.example.ToDoList.payload.Request.SignUpRequest;
import com.example.ToDoList.payload.Request.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    public TasksRepository tasksRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public ListsRepository listsRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    //СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ
    public void createUser(SignUpRequest signUpRequest) {

        Optional<User> byEmail = userRepository.findByEmail(signUpRequest.getEmail());
        if (byEmail.isPresent()) {
            throw new RuntimeException("Пользователь с таким email " + byEmail + " уже зарегистирован");
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Role roles = roleRepository.findByName(EnumRole.ROLE_USER).get();
        user.setRoles(Collections.singleton(roles));
        System.out.print("СТОЙ");
          userRepository.save(user);
    }


    //сохранение задачи
    public void saveTask(TaskRequest taskRequest) {
        Tasks tasks = new Tasks();
        tasks.setTask(taskRequest.getTask());
        tasks.setEnd(taskRequest.getEnd());
        tasksRepository.save(tasks);
    }


//редактирование задачи
    /*
    public void updateTask(Long id,TaskRequest taskRequest)
    {
        Tasks updateTasks = tasksRepository.findById(taskRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Задачи c таким id: " + id +"не существует"));
        updateTasks.setStatus(taskRequest.isStatus());
        tasksRepository.save(updateTasks);

    }

     */
    //редактирование статуса задачи


    //вывод всех списков

    public void outputLists()
    {

    }


    //вывод задач в списке

}
