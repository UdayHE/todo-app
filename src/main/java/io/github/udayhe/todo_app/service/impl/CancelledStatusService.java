package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.enums.Status;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static io.github.udayhe.todo_app.constant.Constant.MAX_LIMIT;
import static io.github.udayhe.todo_app.constant.Constant.SYSTEM;
import static io.github.udayhe.todo_app.enums.Status.CANCELLED;
import static java.lang.System.currentTimeMillis;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;


@Service
@RequiredArgsConstructor
@Slf4j
public class CancelledStatusService implements StatusService {

    private final TodoRepository todoRepository;

    @Override
    public String getStatus() {
        return Status.CANCELLED.name();
    }

    @Override
    public Boolean update(Set<String> ids) {
        try {
            if (isNotEmpty(ids) && ids.size() > MAX_LIMIT)
                throw new IllegalArgumentException("More than " + MAX_LIMIT + " ToDos can not be cancelled");
            List<Todo> todos = todoRepository.findTodosByStatusNewOrOverdue(ids);
            long currentTime = currentTimeMillis();
            todos.forEach(todo -> {
                todo.setStatus(CANCELLED.name());
                todo.setCancelledTime(currentTime);
                todo.setModifiedTime(currentTime);
                todo.setModifiedBy(SYSTEM);
            });
            todoRepository.saveAll(todos);
            return true;
        } catch (Exception e) {
            log.error("Error marking todos as cancelled", e);
            return false;
        }
    }
}
