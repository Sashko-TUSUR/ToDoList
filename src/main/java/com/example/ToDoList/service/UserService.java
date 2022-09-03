package com.example.ToDoList.service;

import com.example.ToDoList.Enumeration.EnumRole;
import com.example.ToDoList.Exception.ResourceNotFoundException;
import com.example.ToDoList.Model.*;
import com.example.ToDoList.Repository.*;
import com.example.ToDoList.payload.Request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private static String name = "Название";
    private static String description = "Описание";

    //сохранение задачи
    public void saveTask(TaskRequest taskRequest, Long id) {
        Tasks tasks = new Tasks();
        Lists lists = listsRepository.findById(id).get();
        tasks.setLists(lists);
        if(taskRequest.getTaskName().trim().length()!=0)
            tasks.setTaskName(taskRequest.getTaskName());
        else tasks.setTaskName(name);

        if(taskRequest.getDescription().trim().length()!=0)
            tasks.setDescription(taskRequest.getDescription());
         else tasks.setDescription(description);

        tasks.setEndTime(taskRequest.getEndTime());
        tasksRepository.save(tasks);
    }

//редактирование задачи
    public void editTask(Long id,TaskRequest taskRequest)
    {
        Tasks updateTasks = tasksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Задачи c таким id: " + id +"не существует"));
        if(taskRequest.getTaskName().trim().length()!=0) {
            updateTasks.setTaskName(taskRequest.getTaskName());
        }
        else updateTasks.setTaskName(name);

        if(taskRequest.getDescription().trim().length()!=0) {
            updateTasks.setDescription(taskRequest.getDescription());
        }
        else updateTasks.setDescription(description );

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
        if(listRequest.getListName().trim().length()!=0) {
           lists.setListName(listRequest.getListName());
        }
        else lists.setListName(name);

        lists.setColors(colors);
        User user = userRepository.findById(id).get();
        lists.setUsers(Collections.singleton(user));
        listsRepository.save(lists);
    }

    //редактирование листа
    public void editList(ListRequest listRequest,Long id)
    {
        Lists editList = listsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Листа c таким id: " + id +"не существует"));

        if(listRequest.getListName().trim().length()!=0) {
            editList.setListName(listRequest.getListName());
            Colors colors = colorRepository.findByColor(listRequest.getColor()).get();
            editList.setColors(colors);
        }
        else {
            editList.setListName(name);
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
    public void putList(PutUserRequest putUser, Long id ,UserDetailsImpl userDetails)
    {
        User user = userRepository.findByEmail(putUser.getEmail()).orElseThrow(()
                -> new ResourceNotFoundException("Пользователя с таким email: "+putUser.getEmail() + " не существует" ));
        Lists lists = listsRepository.findById(id).get();
       User user1 = userRepository.findByEmail(userDetails.getEmail()).get();
       Long userID = (listsRepository.findUser(user1.getId()));
       Long listId = (listsRepository.findList(lists.getId()));
       if((user1.getId().equals(userID)) & (lists.getId().equals(listId)))
       {
           lists.getUsers().add(user);
           lists.getAccessedUsers().add(user);
       }
       else
       {
           lists.getAccessedUsers().add(user1);
           lists.getUsers().add(user1);
           lists.getUsers().add(user);
           lists.getAccessedUsers().add(user);
       }

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
