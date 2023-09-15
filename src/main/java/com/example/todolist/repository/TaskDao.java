package com.example.todolist.repository;

import com.example.todolist.model.Task;

import java.util.List;

public interface TaskDao {

    String CREATE_TODO_LIST = "createTodoList";
    String FIND_BY_ID = "findById";
    String UPDATE_TASK = "updateTask";
    String SELECT_ALL = "selectAll";
    String FIND_BY_DESCRIPTION =  "findByDescription";
    String DELETE_BY_ID = "deleteById";
    String DELETE_ALL = "deleteAll";

    int save(Task toDoItem);

    int update(Task toDoItem);

    Task findById(Long id);

    List<Task> findAll();

    List<Task> findByDescriptionContaining(String description);

    int deleteAll();

    int deleteById(long id);


//    void setDataSource(String dataSource);
}
