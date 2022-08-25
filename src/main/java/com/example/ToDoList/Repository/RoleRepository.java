package com.example.ToDoList.Repository;

import com.example.ToDoList.Enumeration.EnumRole;
import com.example.ToDoList.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(EnumRole name);
}
