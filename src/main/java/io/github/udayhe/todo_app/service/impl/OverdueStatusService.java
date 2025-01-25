package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.enums.Status;
import io.github.udayhe.todo_app.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OverdueStatusService extends BaseStatusService {

    public OverdueStatusService(TodoRepository todoRepository) {
        super(todoRepository);
    }

    @Override
    public String getStatus() {
        return Status.OVERDUE.name();
    }

    @Override
    @Transactional
    public Boolean update(Set<String> ids) {
        try {
            long currentTime = System.currentTimeMillis();
            List<Todo> todos = todoRepository.findOverdueTodos(currentTime);
            updateTodoFields(todos, Status.OVERDUE.name(), currentTime, "SYSTEM");
            saveTodos(todos);
            return true;
        } catch (Exception e) {
            log.error("Error marking todos as overdue", e);
            return false;
        }
    }
}
