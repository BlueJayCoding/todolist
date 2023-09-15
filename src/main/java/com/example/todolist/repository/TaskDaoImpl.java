package com.example.todolist.repository;

import com.example.todolist.model.Task;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.time.Instant;
import java.util.List;


public class TaskDaoImpl extends AbstractDao implements TaskDao {

    @Override
    public int save(Task task) {
        return getJdbcTemplate().update(getQueryString(CREATE_TODO_LIST),
                new Object[]{ task.getDescription(), Instant.now().toString(), false });
    }

    @Override
    public int update(Task task) {
        return getJdbcTemplate().update(getQueryString(UPDATE_TASK),
                new Object[]{task.getDescription(), task.getIsComplete(), task.getUpdatedAt(), task.getId() });
    }

    @Override
    public Task findById(Long id) {
        try {
            Task task = getJdbcTemplate().queryForObject(getQueryString(FIND_BY_ID),
                    BeanPropertyRowMapper.newInstance(Task.class), id);
            return task;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Task> findAll() {
        return getJdbcTemplate().query(getQueryString(SELECT_ALL), BeanPropertyRowMapper.newInstance(Task.class));
    }

    @Override
    public List<Task> findByDescriptionContaining(String description) {
        String q = "SELECT * from todolist WHERE description ILIKE '%" + description + "%'";
        return getJdbcTemplate().query(getQueryString(FIND_BY_DESCRIPTION), BeanPropertyRowMapper.newInstance(Task.class));
    }

    @Override
    public int deleteAll() {
        return getJdbcTemplate().update(getQueryString(DELETE_ALL));
    }

    @Override
    public int deleteById(long id) {
        return getJdbcTemplate().update(getQueryString(DELETE_BY_ID), id);
    }


}
