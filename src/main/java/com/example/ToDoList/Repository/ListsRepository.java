package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListsRepository extends JpaRepository<Lists,Long> {

    @Query(value = "SELECT * FROM lists JOIN tasks on lists.id = tasks.lists_id where lists.user_id=:id",
            nativeQuery = true)
    List<Lists> findByAllLists(@Param("id")Long id);

    Optional<Lists> findById(Long id);
}
