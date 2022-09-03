package com.example.ToDoList.Repository;

import com.example.ToDoList.Model.RefreshToken;
import com.example.ToDoList.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);


    @Query(value =  "SELECT DISTINCT refresh_token.user_id FROM refresh_token WHERE refresh_token.user_id=:id", nativeQuery = true)
    Integer findUser(@Param("id") Long id);

    @Query(value =  "SELECT DISTINCT * FROM refresh_token WHERE refresh_token.user_id=:id", nativeQuery = true)
    RefreshToken findToken(@Param("id") Long id);

}
