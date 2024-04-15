package com.dsi.todowithspringboot.controller;

import com.dsi.todowithspringboot.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final TodoService todoService;

    public HomeController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("todoStatistics", todoService.findStatistics());

        return "front/index";
    }
}
