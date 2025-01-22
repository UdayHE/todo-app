package io.github.udayhe.todo_app.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    private boolean deleted;
    private String createdBy;
    private String modifiedBy;
    private long createdTime;
    private long modifiedTime;
}
