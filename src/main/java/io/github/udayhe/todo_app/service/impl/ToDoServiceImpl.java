package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static io.github.udayhe.todo_app.constant.Constant.MAX_LIMIT_COMPLETED;
import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TodoRepository repository;

    @Override
    public List<Todo> list() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            log.error("Exception in list.", e);
        }
        return null;
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
    public Boolean markAsCompleted(Set<String> ids) {
        try {
            if (isNotEmpty(ids) && ids.size() > MAX_LIMIT_COMPLETED)
                throw new IllegalArgumentException("More than " + MAX_LIMIT_COMPLETED + " ToDos can not be completed");
            List<Todo> todos = repository.findAllById(ids);
            todos.forEach(todo -> {
                if (!todo.isCompleted()) {
                    todo.setCompleted(true);
                    todo.setModifiedTime(System.currentTimeMillis());
                }
            });
            repository.saveAll(todos);
            return true;
        } catch (Exception e) {
            log.error("Error marking todos as completed", e);
            return false;
        }
    }

    @Override
    public Todo update(String id, Todo todo) {
        try {
            Todo existing = findById(id);
            existing.setTitle(todo.getTitle());
            existing.setCompleted(todo.isCompleted());
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
