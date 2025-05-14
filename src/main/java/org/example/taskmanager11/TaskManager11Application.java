package org.example.taskmanager11;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskManager11Application {

    public static void main(String[] args) {
        SpringApplication.run(TaskManager11Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(TaskService taskService) {
        return args -> {
            System.out.println("Hello World");

            for (int i = 0; i < 10; i++) {
                taskService.addTask("Test task #" + i);
            }
        };
    }
}
