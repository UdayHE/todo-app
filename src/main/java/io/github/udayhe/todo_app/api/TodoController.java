package io.github.udayhe.todo_app.api;

import io.github.udayhe.todo_app.entity.Todo;
import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final ToDoService toDoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.ok(toDoService.list());
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo todo) {
        return ResponseEntity.ok(toDoService.save(todo));
    }

    @PutMapping("/status/{status}")
    public ResponseEntity<Boolean> statusUpdate(@PathVariable String status, @RequestBody  Set<String> ids) {
        return ResponseEntity.ok(toDoService.updateStatus(status, ids));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody Todo todo) {
        return ResponseEntity.ok(toDoService.update(id, todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        toDoService.delete(id);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAll() {
        toDoService.deleteAll();
        return ResponseEntity.ok("success");
    }
}
