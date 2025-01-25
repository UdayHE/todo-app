package io.github.udayhe.todo_app.aspect;

import io.github.udayhe.todo_app.entity.BaseEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static io.github.udayhe.todo_app.constant.Constant.SYSTEM;
import static java.util.Objects.isNull;

@Aspect
@Component
public class BaseEntityAspect {

    @Before("execution(* org.springframework.data.repository.CrudRepository.save(..)) && args(entity)")
    public void beforeSave(Object entity) {
        if (entity instanceof BaseEntity baseEntity) {
            if (isNull(baseEntity.getCreatedTime()) || baseEntity.getCreatedTime() == 0) {
                baseEntity.setCreatedTime(Instant.now().toEpochMilli());
                baseEntity.setCreatedBy(getCurrentUserId());
            }
            baseEntity.setModifiedTime(Instant.now().toEpochMilli());
            baseEntity.setModifiedBy(getCurrentUserId());
        }
    }

    @Before("execution(* org.springframework.data.repository.CrudRepository.saveAll(..)) && args(entities)")
    public void beforeSaveAll(Iterable<?> entities) {
        for (Object entity : entities) {
            if (entity instanceof BaseEntity baseEntity) {
                if (isNull(baseEntity.getCreatedTime()) || baseEntity.getCreatedTime() == 0) {
                    baseEntity.setCreatedTime(Instant.now().toEpochMilli());
                    baseEntity.setCreatedBy(getCurrentUserId());
                }
                baseEntity.setModifiedTime(Instant.now().toEpochMilli());
                baseEntity.setModifiedBy(getCurrentUserId());
            }
        }
    }

    private String getCurrentUserId() {
       return SYSTEM;
    }
}
