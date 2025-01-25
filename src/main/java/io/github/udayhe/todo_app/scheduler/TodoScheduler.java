package io.github.udayhe.todo_app.scheduler;

import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static io.github.udayhe.todo_app.enums.Status.OVERDUE;
import static java.util.Collections.emptySet;

@Component
@RequiredArgsConstructor
public class TodoScheduler {

    private final ToDoService todoService;

    @Scheduled(fixedRate = 60000L)
    public void checkForOverdueTodos() {
        todoService.updateStatus(OVERDUE.name(), emptySet());
    }
}
