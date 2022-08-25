package com.example.ToDoList.Model;

import com.example.ToDoList.Enumeration.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumRole name;

}
