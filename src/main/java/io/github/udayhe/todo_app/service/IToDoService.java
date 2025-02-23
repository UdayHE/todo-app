package io.github.udayhe.todo_app.service;

import io.github.udayhe.todo_app.entity.Todo;

import java.util.List;
import java.util.Set;

public interface IToDoService {

    List<Todo> list();

    Todo save(Todo todo);

    Todo update(String id, Todo todo);

    Todo findById(String id);

    void delete(String id);

    void deleteAll();

    Boolean updateStatus(String status, Set<String> ids);
}
