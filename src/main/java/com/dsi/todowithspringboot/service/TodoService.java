package com.dsi.todowithspringboot.service;

import com.dsi.todowithspringboot.entity.Todo;
import com.dsi.todowithspringboot.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Long count() {
        return this.todoRepository.count();
    }

    public Long countByIsCompleted(Boolean isCompleted) {
        return this.todoRepository.countByIsCompleted(isCompleted);
    }

    public Long countByIsStarred(Boolean isStarred) {
        return this.todoRepository.countByIsStarred(isStarred);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll(latest());
    }

    @Transactional
    public Todo save(Todo todo) {
        return this.todoRepository.save(todo);
    }

    public Todo findById(Long id) {
        return this.todoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Todo update(Todo todo) {
        return this.todoRepository.save(todo);
    }

    @Transactional
    public void delete(Long id) {
        this.todoRepository.deleteById(id);
    }

    private Sort latest() {
        return Sort.by(Sort.Direction.DESC, "createdAt");
    }
}
