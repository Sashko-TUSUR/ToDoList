package com.example.ToDoList.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "lists", cascade = CascadeType.ALL)
    private List<Tasks> tasks;


    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    @JoinTable(name = "lists_user",joinColumns = @JoinColumn(name = "lists_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "accessedUsers",joinColumns = @JoinColumn(name = "lists_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"))
    private Set<User> accessedUsers = new HashSet<>();


    @ManyToOne
    @JsonUnwrapped
    @JoinColumn(name = "lists_color")
    private Colors colors;

    public Lists(String listName) {
        ListName = listName;

    }

}
