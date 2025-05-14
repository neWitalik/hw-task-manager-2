package org.example.taskmanager11;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task addTask(String description) {
        Task task = new Task();

        task.setDescription(description);
        taskRepository.save(task);

        return task;
    }

    @Transactional
    public void removeTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task toggleTask(Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty())
            throw new RuntimeException("Task not found");

        Task task = taskOpt.get();
        task.setComplete(!task.getComplete());

        taskRepository.save(task);
        return task;
    }
}
