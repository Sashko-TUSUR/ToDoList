package com.example.ToDoList.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String content;

    @Column(updatable = false)
    private Long timestamp;

    @JsonIgnoreProperties("id")
    @JsonUnwrapped
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Tasks tasks;


}
