package org.example.taskmanager11.repo;

import org.example.taskmanager11.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByLogin(String login);
}
