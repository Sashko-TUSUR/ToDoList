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


    @Query(value = "SELECT * FROM tasks JOIN lists on lists.id =tasks.lists_id join lists_user on lists_user.lists_id = lists.id WHERE lists_user.user_id =:id",
            nativeQuery = true)
    List<Tasks> findByAllTask(@Param("id")Long id);

}
