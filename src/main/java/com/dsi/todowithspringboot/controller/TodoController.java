package com.dsi.todowithspringboot.controller;

import com.dsi.todowithspringboot.dto.TodoStoreDto;
import com.dsi.todowithspringboot.dto.TodoUpdateDto;
import com.dsi.todowithspringboot.entity.Todo;
import com.dsi.todowithspringboot.helper.NotifierHelper;
import com.dsi.todowithspringboot.helper.ValidationHelper;
import com.dsi.todowithspringboot.service.TodoService;
import com.dsi.todowithspringboot.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @ModelAttribute
    public Todo getTodo() {
        return new Todo();
    }

    @GetMapping
    public String index(
            Model model,
            @RequestParam(value = "completed", required = false, defaultValue = "false") Boolean completed,
            @RequestParam(value = "starred", required = false, defaultValue = "false") Boolean starred,
            HttpServletRequest request
    ) {

        List<Todo> todos;

        if (completed) {
            todos = todoService.findAllByIsCompleted(true);
        } else if (starred) {
            todos = todoService.findAllByIsStarred(true);
        } else {
            todos = todoService.findAll();
        }

        model.addAttribute("todos", todos);
        model.addAttribute("queryParams", StringUtils.getQueryParams(request));

        return "front/todo/index";
    }

    @PostMapping("/store")
    public String save(@Valid @ModelAttribute TodoStoreDto todoStoreDto, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("todo", todoStoreDto).bind(result);
            return "redirect:/todo";
        }

        try {
            Todo todo = new Todo();
            todo.setTitle(todoStoreDto.getTitle());
            this.todoService.save(todo);
            new NotifierHelper(attributes).message("Todo added successfully.").success();
        } catch (Exception e) {
            log.error(e.getMessage());
            new NotifierHelper(attributes).message("Todo couldn't be added.").error();
        }

        return "redirect:/todo";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, RedirectAttributes attributes, Model model) {

        Todo todo = this.todoService.findById(id);

        if (todo == null) {
            new NotifierHelper(attributes).message("Todo not found.").error();
            return "redirect:/todo";
        }

        model.addAttribute("todo", todo);

        return "front/todo/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @Valid @ModelAttribute TodoUpdateDto todoUpdateDto, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            new ValidationHelper(attributes).model("todo", todoUpdateDto).bind(result);
            return "redirect:/todo/" + id + "/edit";
        }

        Todo todo = this.todoService.findById(id);

        if (todo == null) {
            new NotifierHelper(attributes).message("Todo not found.").error();
            return "redirect:/todo";
        }

        try {
            todo.setTitle(todoUpdateDto.getTitle());
            todo.setDetails(todoUpdateDto.getDetails());
            if (todoUpdateDto.getIsStarred() != null)
                todo.setIsStarred(todoUpdateDto.getIsStarred());
            if (todoUpdateDto.getIsCompleted() != null)
                todo.setIsCompleted(todoUpdateDto.getIsCompleted());

            this.todoService.update(todo);
            new NotifierHelper(attributes).message("Todo updated successfully.").success();
        } catch (Exception e) {
            log.error(e.getMessage());
            new NotifierHelper(attributes).message("Todo can't be updated.").error();
        }

        return "redirect:/todo/" + id + "/edit";
    }

    @PostMapping("/{id}/destroy")
    public String destroy(@PathVariable("id") Long id, RedirectAttributes attributes, HttpServletRequest request) {

        Todo todo = this.todoService.findById(id);

        String redirect = "redirect:/todo?" + StringUtils.getQueryParams(request);

        if (todo == null) {
            new NotifierHelper(attributes).message("Todo not found.").error();
            return redirect;
        }

        try {
            this.todoService.delete(todo.getId());
            new NotifierHelper(attributes).message("Todo deleted successfully.").success();
        } catch (Exception e) {
            log.error(e.getMessage());
            new NotifierHelper(attributes).message("Todo can't be deleted.").error();
        }

        return redirect;
    }

    @PostMapping("/{id}/toggle-completed")
    public String toggleCompleted(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request) {

        Todo todo = this.todoService.findById(id);

        String redirect = "redirect:/todo?" + StringUtils.getQueryParams(request);

        if (todo == null) {
            new NotifierHelper(attributes).message("Todo not found.").error();
            return redirect;
        }

        try {
            todo.setIsCompleted(!todo.getIsCompleted());
            this.todoService.update(todo);
        } catch (Exception e) {
            log.error(e.getMessage());
            new NotifierHelper(attributes).message("Todo can't be updated.").error();
        }

        return redirect;
    }

    @PostMapping("/{id}/toggle-starred")
    public String toggleStarred(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest request) {

        Todo todo = this.todoService.findById(id);

        String redirect = "redirect:/todo?" + StringUtils.getQueryParams(request);

        if (todo == null) {
            new NotifierHelper(attributes).message("Todo not found.").error();
            return redirect;
        }

        try {
            todo.setIsStarred(!todo.getIsStarred());
            this.todoService.update(todo);
        } catch (Exception e) {
            log.error(e.getMessage());
            new NotifierHelper(attributes).message("Todo can't be updated.").error();
        }

        return redirect;
    }
}
