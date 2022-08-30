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

    @Query(value = "SELECT * FROM lists JOIN lists_user on lists.id = lists_user.lists_id INNER JOIN user ON lists_user.user_id = user.id where user.id=:id",
            nativeQuery = true)
    List<Lists> findByAllLists(@Param("id")Long id);

    Optional<Lists> findById(Long id);
}
