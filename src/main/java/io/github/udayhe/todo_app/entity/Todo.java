package io.github.udayhe.todo_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Todo {
    @Id
    private String id;
    private String title;
    private boolean completed;


    @PrePersist
    private void generateIdIfNotSet() {
        if (id == null || id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
    }
}

