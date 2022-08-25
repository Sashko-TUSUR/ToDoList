package com.example.ToDoList.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String task;
    private Date end;

    private String description;

    @Value("false")
    private boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lists_id")
    private Lists lists;

    public Tasks(String task, Date end, Lists lists) {
        this.task = task;
        this.end = end;
        this.lists = lists;
    }
}
