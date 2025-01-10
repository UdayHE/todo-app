package io.github.udayhe.todo_app.service;

import io.github.udayhe.todo_app.entity.Todo;

import java.util.List;

public interface ToDoService {
    List<Todo> list();
    Todo save(Todo todo);
    Todo update(String id, Todo todo);
    Todo findById(String id);
    void delete(String id);
}
