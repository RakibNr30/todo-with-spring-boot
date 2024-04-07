package com.dsi.todowithspringboot.service;

import com.dsi.todowithspringboot.entity.Todo;
import com.dsi.todowithspringboot.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo save(Todo todo) {
        return this.todoRepository.save(todo);
    }

    public Todo findById(Long id) {
        return this.todoRepository.findById(id).orElse(null);
    }

    public Todo update(Todo todo) {
        return  this.todoRepository.save(todo);
    }

    public void delete(Long id) {
        this.todoRepository.deleteById(id);
    }
}
