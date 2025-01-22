package io.github.udayhe.todo_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

import static ch.qos.logback.core.util.StringUtil.isNullOrEmpty;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Todo extends BaseEntity {
    @Id
    private String id;
    private String title;
    private boolean completed;


    @PrePersist
    private void generateIdIfNotSet() {
        if (isNullOrEmpty(this.id)) {
            this.id = UUID.randomUUID().toString();
        }
    }
}

