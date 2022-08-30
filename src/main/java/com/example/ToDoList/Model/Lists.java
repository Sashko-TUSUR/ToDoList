package com.example.ToDoList.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "listName", "colors", "tasks"})
@DynamicUpdate

public class Lists {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ListName;

    private Long userId;


    @OneToMany(mappedBy = "lists", cascade = CascadeType.ALL)
    private List<Tasks> tasks;


    @ManyToOne
    @JoinColumn(name = "lists_color")
    private Colors colors;

    public Lists(String listName) {
        ListName = listName;

    }

}
