package com.example.todolist.repository;

import com.example.todolist.exceptions.ResourceNotFoundException;
import com.example.todolist.model.Task;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.Instant;
import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public int save(Task task) {
        try {
            String query = getQueryString(CREATE_TODO_LIST);
            return jdbcTemplate.update(query, task.getDescription(), Instant.now().toString(), false);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            throw new RuntimeException("Error saving task.", e);
        }
    }

    @Override
    public int update(Task task) {
        try {
            String query = getQueryString(UPDATE_TASK);
            return jdbcTemplate.update(query, task.getDescription(), task.getIsComplete(), task.getUpdatedAt(), task.getId());
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            throw new RuntimeException("Error updating task.", e);
        }
    }

    @Override
    public Task findById(Long id) {
        try {
            String query = getQueryString(FIND_BY_ID);
            return jdbcTemplate.queryForObject(query, BeanPropertyRowMapper.newInstance(Task.class), id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            String query = getQueryString(SELECT_ALL);
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Task.class));
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            throw new ResourceNotFoundException("No task available.");
        }
    }

    @Override
    public List<Task> findByDescriptionContaining(String description) {
        try {
            String query = getQueryString(FIND_BY_DESCRIPTION);
            return jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(Task.class), "%" + description + "%");
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            throw new RuntimeException("Error finding tasks by description.", e);
        }
    }

    @Override
    public int deleteById(long id) {
        try {
            String query = getQueryString(DELETE_BY_ID);
            return jdbcTemplate.update(query, id);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            throw new RuntimeException("Error deleting task by ID.", e);
        }
    }
}
