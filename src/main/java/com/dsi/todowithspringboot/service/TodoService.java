package com.dsi.todowithspringboot.service;

import com.dsi.todowithspringboot.entity.Todo;
import com.dsi.todowithspringboot.repository.TodoRepository;
import com.dsi.todowithspringboot.util.SortUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Map<String, Long> findStatistics() {
        List<Todo> todos = todoRepository.findAll();

        Long total = (long) todos.size();
        Long completed = todos.stream().filter(Todo::getIsCompleted).count();
        Long starred = todos.stream().filter(Todo::getIsStarred).count();

        return Map.of("total", total, "completed", completed, "starred", starred);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll(SortUtils.latest());
    }

    public List<Todo> findAllByIsCompleted(Boolean isCompleted) {
        return todoRepository.findAllByIsCompletedOrderByCreatedAtAsc(isCompleted);
    }

    public List<Todo> findAllByIsStarred(Boolean isStarred) {
        return todoRepository.findAllByIsStarredOrderByCreatedAtAsc(isStarred);
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
}
