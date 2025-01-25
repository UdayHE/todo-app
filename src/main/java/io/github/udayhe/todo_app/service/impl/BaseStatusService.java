package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.IStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract class BaseStatusService implements IStatusService {

    protected final TodoRepository todoRepository;

    protected void validateIds(Set<String> ids, int maxLimit) {
        if (ids != null && !ids.isEmpty() && ids.size() > maxLimit)
            throw new IllegalArgumentException("More than " + maxLimit + " ToDos cannot be updated");
    }

    protected List<Todo> findAndValidateTodos(Set<String> ids, String... allowedStatuses) {
        List<Todo> todos = todoRepository.findTodosByStatusNewOrOverdue(ids);
        if (allowedStatuses != null && allowedStatuses.length > 0)
            todos = todos.stream()
                    .filter(todo -> Arrays.asList(allowedStatuses).contains(todo.getStatus()))
                    .toList();

        return todos;
    }

    protected void saveTodos(List<Todo> todos) {
        try {
            todoRepository.saveAll(todos);
        } catch (Exception e) {
            log.error("Error saving todos", e);
            throw new RuntimeException("Failed to save todos", e);
        }
    }

    protected void updateTodoFields(List<Todo> todos, String status, long currentTime, String systemUser) {
        todos.forEach(todo -> {
            todo.setStatus(status);
            todo.setModifiedTime(currentTime);
            todo.setModifiedBy(systemUser);
        });
    }
}
