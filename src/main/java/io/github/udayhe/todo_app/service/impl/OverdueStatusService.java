package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.enums.Status;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.StatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static io.github.udayhe.todo_app.constant.Constant.SYSTEM;
import static io.github.udayhe.todo_app.enums.Status.OVERDUE;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class OverdueStatusService implements StatusService {

    private final TodoRepository todoRepository;

    @Override
    public String getStatus() {
        return Status.OVERDUE.name();
    }

    @Override
    @Transactional
    public Boolean update(Set<String> ids) {
        long currentTime = currentTimeMillis();
        List<Todo> overdueTodos = todoRepository.findOverdueTodos(currentTime);
        for (Todo todo : overdueTodos) {
            todo.setStatus(OVERDUE.name());
            todo.setModifiedTime(currentTime);
            todo.setModifiedBy(SYSTEM);
        }
        todoRepository.saveAll(overdueTodos);
        return true;
    }
}
