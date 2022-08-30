package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.Lists;
import com.example.ToDoList.Model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {


    @Query(value = "SELECT * FROM tasks JOIN lists on tasks.lists_id = lists.id where lists.user_id=:id",
            nativeQuery = true)
    List<Tasks> findByAllTask(@Param("id")Long id);

}
