package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.Colors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Colors, String> {
    Optional<Colors> findByColor(String color);
}
