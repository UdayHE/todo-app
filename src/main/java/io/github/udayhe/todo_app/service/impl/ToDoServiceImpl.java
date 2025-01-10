package io.github.udayhe.todo_app.service.impl;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.repository.TodoRepository;
import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final TodoRepository repository;

    @Override
    public List<Todo> list() {
        return repository.findAll();
    }

    @Override
    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public Todo update(String id, Todo todo) {
        Todo existing = findById(id);
        existing.setTitle(todo.getTitle());
        existing.setCompleted(todo.isCompleted());
        return save(existing);
    }

    @Override
    public Todo findById(String id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
