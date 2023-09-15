package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskDao taskDao;

    @PostMapping("/todolist")
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            Task _task = new Task();
            _task.setDescription(task.getDescription());
            _task.setCreatedAt(Instant.now().toString());

            taskDao.save(_task);
            return new ResponseEntity<>("New task was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todolist")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String description) {
        try {
            List<Task> list = new ArrayList<Task>();

            if (description == null) {list.addAll(taskDao.findAll());} else {
                list.addAll(taskDao.findByDescriptionContaining(description));
            }

            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todolist/{id}")
    public ResponseEntity<Task> getItemById(@PathVariable("id") long id) {
        Task item = taskDao.findById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todolist/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
        Task _task = taskDao.findById(id);

        if (_task != null) {
            _task.setId(id);
            if(task.getDescription() != null){
                _task.setDescription(task.getDescription());
            }
            if(task.getIsComplete() != null){
                _task.setIsComplete(task.getIsComplete());
            }
            _task.setUpdatedAt(Instant.now().toString());
            taskDao.update(_task);
            return new ResponseEntity<>("Task was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find a task with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") long id) {
        try {
            int result = taskDao.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find a task with id = " + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Task was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete the task with id = " + id + " .",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
