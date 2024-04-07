package com.dsi.todowithspringboot.controller;

import com.dsi.todowithspringboot.entity.Todo;
import com.dsi.todowithspringboot.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/store")
    public String save(@Valid @ModelAttribute Todo todo) {

        try {
            this.todoService.save(todo);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return "redirect:/";
    }
}
