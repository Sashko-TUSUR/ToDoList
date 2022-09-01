package com.example.ToDoList.service;

import com.example.ToDoList.Enumeration.EnumRole;
import com.example.ToDoList.Exception.ResourceNotFoundException;
import com.example.ToDoList.Model.*;
import com.example.ToDoList.Repository.*;
import com.example.ToDoList.payload.Request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Optional;

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
    public ColorRepository colorRepository;
    @Autowired
    public CommentRepository commentRepository;
    @Autowired AccessedUsersRepository accessedUsersRepository;

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
          userRepository.save(user);
    }


    //сохранение задачи
    public void saveTask(TaskRequest taskRequest, Long id) {
        Tasks tasks = new Tasks();
        Lists lists = listsRepository.findById(id).get();
        tasks.setLists(lists);
        tasks.setTaskName(taskRequest.getTaskName());
        tasks.setEndTime(taskRequest.getEndTime());
        tasks.setDescription(taskRequest.getDescription());
        tasksRepository.save(tasks);
    }

//редактирование задачи
    public void editTask(Long id,TaskRequest taskRequest)
    {
        Tasks updateTasks = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Задачи c таким id: " + id +"не существует"));
        updateTasks.setTaskName(taskRequest.getTaskName());
        updateTasks.setDescription(taskRequest.getDescription());
        updateTasks.setStatus(taskRequest.isStatus());
        tasksRepository.save(updateTasks);

    }

    //////Удаление задачи
    public void deleteTask(Long id)
    {
        tasksRepository.deleteById(id);
    }


    //ДОБАВЛЕНИЕ ЛИСТА
    public void saveList(ListRequest listRequest, Long id)
    {
        Lists lists = new Lists();
        Colors colors = colorRepository.findByColor(listRequest.getColor()).orElseThrow(() -> new ResourceNotFoundException("Цвета c таким id: " + listRequest.getColor() +"не существует"));
        lists.setListName(listRequest.getListName());
        lists.setColors(colors);
        User user = userRepository.findById(id).get();
        lists.setUsers(Collections.singleton(user));
        AccessedUsers accessedUsers = new AccessedUsers();
        accessedUsers.setId(user.getId());
        accessedUsers.setEmail(user.getEmail());
        lists = listsRepository.save(lists);
    }

    //редактирование листа
    public void editList(ListRequest listRequest,Long id)
    {
        Lists editList = listsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Листа c таким id: " + id +"не существует"));
        if(listRequest.getListName()!=null) {
            editList.setListName(listRequest.getListName());
            Colors colors = colorRepository.findByColor(listRequest.getColor()).get();
            editList.setColors(colors);
        }
        else {
            Colors colors = colorRepository.findByColor(listRequest.getColor()).get();
            editList.setColors(colors);
        }
        listsRepository.save(editList);
    }

    //удаление листа
    public void deleteList(Long id)
    {

        listsRepository.deleteById(id);
    }

    ///поделиться списком
    public void putList(PutUserRequest putUser, Long id)
    {
        User user = userRepository.findByEmail(putUser.getEmail()).orElseThrow(()
                -> new ResourceNotFoundException("Пользователя с таким email: "+putUser.getEmail() + " не существует" ));
        Lists lists = listsRepository.findById(id).get();
        lists.getUsers().add(user);
        AccessedUsers accessedUsers = new AccessedUsers();
        accessedUsers.setEmail(putUser.getEmail());
        accessedUsers.setId(user.getId());
        accessedUsersRepository.save(accessedUsers);
        listsRepository.save(lists);
    }


    //добавить коммент
    public void addComment(Long userId, CommentRequest commentRequest,Long id) {
       Comment comment = new Comment();
       User user = userRepository.findById(userId).get();
        comment.setUser(user);
        comment.setTimestamp(commentRequest.getTimestamp());
        comment.setContent(commentRequest.getContent());
        Tasks tasks = tasksRepository.findById(id).get();
        comment.setTasks(tasks);
        commentRepository.save(comment);
    }


}
