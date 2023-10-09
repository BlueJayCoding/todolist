package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Long id;
    private String description;
    private Boolean isComplete;
    private String createdAt;
    private String updatedAt;

    @Override
    public String toString() {
        return "todolist [id=" + id + ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isComplete=" + isComplete + "]";
    }
}
