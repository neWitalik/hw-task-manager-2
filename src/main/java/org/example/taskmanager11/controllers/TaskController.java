package org.example.taskmanager11.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.taskmanager11.model.Task;
import org.example.taskmanager11.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String index(Model model, HttpSession session) {
        checkAuthorized(session);

        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id, HttpSession session) {
        checkAuthorized(session);

        taskService.toggleTask(id);
        return "redirect:/";
    }

    @ExceptionHandler(value = NotAuthorizedException.class)
    public String onException() {
        return "login";
    }

    private void checkAuthorized(HttpSession session) {
        if (session.getAttribute("login") == null)
            throw new NotAuthorizedException();
    }

    /*@GetMapping("v2")
    public String index2(Model model) {
        return "index2";
    }*/
}
