package org.example.taskmanager11.repo;

import org.example.taskmanager11.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.complete = (NOT t.complete) WHERE t.id = :id")
    int toggleTask(long id);

    //void findByDescriptionAndComplete(String description, boolean complete);
}
