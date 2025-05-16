package org.example.taskmanager11;

import org.example.taskmanager11.services.ClientService;
import org.example.taskmanager11.services.TaskService;
import org.example.taskmanager11.utils.Utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// E -> R -> S -> C

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(TaskService taskService, ClientService clientService) {
        return args -> {
            System.out.println("Hello World");

            for (int i = 0; i < 10; i++) {
                taskService.addTask("Test task #" + i);
            }

            String salt = Utils.generateRandomString(10);
            clientService.addClient("111", salt, Utils.passwordHash(salt, "222"));
        };
    }
}
