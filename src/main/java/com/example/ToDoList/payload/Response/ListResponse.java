package com.example.ToDoList.payload.Response;


import lombok.Data;

@Data
public class ListResponse {
    private String ListName;
    private Long id;
    private String color;
    private String value;
    private String label;

    public ListResponse(String listName, Long id, String color, String value, String label) {
        ListName = listName;
        this.id = id;
        this.color = color;
        this.value = value;
        this.label = label;
    }
}
