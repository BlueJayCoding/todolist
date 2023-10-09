package com.example.todolist.controller;

import com.example.todolist.exceptions.ResourceNotFoundException;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/todolist")
public class TaskController {

    @Autowired
    private TaskDao taskDao;

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        Task newTask = new Task();
        newTask.setDescription(task.getDescription());
        newTask.setCreatedAt(Instant.now().toString());
        taskDao.save(newTask);
        return new ResponseEntity<>("New task was created successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String description) {
        try{
            List<Task> list;
            if (description == null) {
                list = taskDao.findAll();
            } else {
                list = taskDao.findByDescriptionContaining(description);
            }

            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            

            return new ResponseEntity<>(list, HttpStatus.OK);

        }catch (Exception e){
            throw new ResourceNotFoundException("No content available");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getItemById(@PathVariable("id") long id) {
        Task item = taskDao.findById(id);
        if (item == null) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        Task existingTask = taskDao.findById(id);

        if (existingTask != null) {
            existingTask.setId(id);
            if (task.getDescription() != null) {
                existingTask.setDescription(task.getDescription());
            }
            if (task.getIsComplete() != null) {
                existingTask.setIsComplete(task.getIsComplete());
            }
            existingTask.setUpdatedAt(Instant.now().toString());
            taskDao.update(existingTask);
            return new ResponseEntity<>("Task was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find a task with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id) {
        try {
            int result = taskDao.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find a task with id = " + id, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Task was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete the task with id = " + id + ".", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
