package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.ToDoService;
import io.github.udayhe.todo_app.service.factory.StatusFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static io.github.udayhe.todo_app.enums.Status.OVERDUE;
import static java.lang.System.currentTimeMillis;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TodoRepository repository;
    private final StatusFactory statusFactory;

    @Override
    public List<Todo> list() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Exception in list.", e);
        }
        return emptyList();
    }

    @Override
    public Todo save(Todo todo) {
        try {
            return repository.save(todo);
        } catch (Exception e) {
            log.error("Exception save.", e);
        }
        return null;
    }

    @Override
    public Boolean updateStatus(String status, Set<String> ids) {
        return statusFactory.get(status).update(ids);
    }

    @Override
    public Todo update(String id, Todo todo) {
        try {
            Todo existing = findById(id);
            existing.setTitle(todo.getTitle());
            if(nonNull(todo.getDueTime()) && todo.getDueTime() > currentTimeMillis())
                existing.setDueTime(todo.getDueTime());
            return save(existing);
        } catch (Exception e) {
            log.error("Exception in update.", e);
        }
        return null;
    }

    @Override
    public Todo findById(String id) {
        try {
            return repository.findById(id).orElseThrow();
        } catch (Exception e) {
            log.error("Exception in findById.", e);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            log.error("Exception in delete by id.", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            repository.deleteAll();
        } catch (Exception e) {
            log.error("Exception in deleteAll.", e);
        }
    }

}
