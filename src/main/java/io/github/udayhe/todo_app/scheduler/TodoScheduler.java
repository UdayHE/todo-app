package io.github.udayhe.todo_app.scheduler;

import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static io.github.udayhe.todo_app.constant.Constant.SCHEDULED_TIME;

@Component
@RequiredArgsConstructor
public class TodoScheduler {

    private ToDoService todoService;

    @Scheduled(fixedRate = SCHEDULED_TIME)
    public void checkForOverdueTodos() {
        todoService.updateOverdueTodos();
    }
}
