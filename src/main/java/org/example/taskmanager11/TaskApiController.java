package org.example.taskmanager11;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    private final TaskService taskService;

    public TaskApiController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        String description = task.getDescription();

        if (description == null || description.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400
        }

        Task result = taskService.addTask(description);
        return ResponseEntity.ok(result); // 200
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTask(@PathVariable Long id) {
        try {
            Task result = taskService.toggleTask(id);
            return ResponseEntity.ok(result); // 200
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
