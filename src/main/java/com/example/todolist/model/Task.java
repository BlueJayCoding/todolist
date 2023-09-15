package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class Task {


    private Long id;

    private String description;

    private Boolean isComplete;

    private String createdAt;

    private String updatedAt;

    public Task() {

    }


    @Override
    public String toString() {
        return "todolist [id=" + id + ", description=" + description + ", createdAt=" + createdAt + ", " +
                "updatedAt=" + updatedAt + ", isComplete=" + isComplete +"]";
    }



}
