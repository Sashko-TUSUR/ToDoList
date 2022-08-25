package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.Lists;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListsRepository extends JpaRepository<Lists,Long> {

    @Query(value = "SELECT * FROM lists JOIN tasks on lists.id = tasks.lists_id where",
            nativeQuery = true)
    List<Lists> findByLists();

}
