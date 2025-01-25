package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.enums.Status;
import io.github.udayhe.todo_app.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static io.github.udayhe.todo_app.constant.Constant.MAX_LIMIT;

@Service
@Slf4j
public class CompletedStatusService extends BaseStatusService {

    public CompletedStatusService(TodoRepository todoRepository) {
        super(todoRepository);
    }

    @Override
    public String getStatus() {
        return Status.COMPLETED.name();
    }

    @Override
    public Boolean update(Set<String> ids) {
        try {
            validateIds(ids, MAX_LIMIT);
            List<Todo> todos = findAndValidateTodos(ids,Status.NEW.name(), Status.OVERDUE.name());
            todos.forEach(todo -> todo.setCompletionTime(System.currentTimeMillis()));
            updateTodoFields(todos, Status.COMPLETED.name(), System.currentTimeMillis(), "SYSTEM");
            saveTodos(todos);
            return true;
        } catch (Exception e) {
            log.error("Error marking todos as completed", e);
            return false;
        }
    }
}

