package io.github.udayhe.todo_app.aspect;

import io.github.udayhe.todo_app.entity.BaseEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class BaseEntityAspect {

    @Before("execution(* org.springframework.data.repository.CrudRepository.save(..)) && args(entity)")
    public void beforeSave(Object entity) {
        if (entity instanceof BaseEntity baseEntity) {
            if (baseEntity.getCreatedTime() == 0) {
                baseEntity.setCreatedTime(Instant.now().toEpochMilli());
                baseEntity.setCreatedBy(getCurrentUsername());
            }
            baseEntity.setModifiedTime(Instant.now().toEpochMilli());
            baseEntity.setModifiedBy(getCurrentUsername());
        }
    }

    @Before("execution(* org.springframework.data.repository.CrudRepository.saveAll(..)) && args(entities)")
    public void beforeSaveAll(Iterable<?> entities) {
        for (Object entity : entities) {
            if (entity instanceof BaseEntity baseEntity) {
                if (baseEntity.getCreatedTime() == 0) {
                    baseEntity.setCreatedTime(Instant.now().toEpochMilli());
                    baseEntity.setCreatedBy(getCurrentUsername());
                }
                baseEntity.setModifiedTime(Instant.now().toEpochMilli());
                baseEntity.setModifiedBy(getCurrentUsername());
            }
        }
    }

    private String getCurrentUsername() {
       return "007";
    }
}
