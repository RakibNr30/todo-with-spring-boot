package com.dsi.todowithspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TodoController {

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("from", "INDEX PAGE");
        return "index";
    }
}
