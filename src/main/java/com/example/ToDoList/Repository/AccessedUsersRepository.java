package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.AccessedUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessedUsersRepository extends JpaRepository<AccessedUsers, Long> {
}
