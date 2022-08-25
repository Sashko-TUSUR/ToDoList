package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Colors, String> {
}
