package com.example.ToDoList.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String taskName;
    private Long endTime;
    private String description;

    @Value("false")
    private boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lists_id")
    private Lists lists;


    @OneToMany(mappedBy = "tasks",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments=new ArrayList<>();

    public Tasks(String taskName, Long endTime, Lists lists) {
        this.taskName = taskName;
        this.endTime = endTime;
        this.lists = lists;

    }
}
