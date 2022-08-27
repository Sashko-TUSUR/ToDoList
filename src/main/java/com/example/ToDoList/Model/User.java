package com.example.ToDoList.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;


private String email;

@JsonIgnore
private String password;

@JsonIgnore
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(name="user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
private Set<Role> roles;

@JsonIgnore
@OneToMany()
private List<Lists> lists;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
