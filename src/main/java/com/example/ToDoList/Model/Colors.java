package com.example.ToDoList.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Colors {

    @Id
    private String color;
    @JsonIgnore
    private String value;
    @JsonIgnore
    private String label;

}
