package io.github.udayhe.todo_app.scheduler;

import io.github.udayhe.todo_app.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static io.github.udayhe.todo_app.constant.Constant.SCHEDULER_INTERVAL;
import static io.github.udayhe.todo_app.enums.Status.OVERDUE;
import static java.util.Collections.emptySet;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoScheduler {

    private final ToDoService todoService;


    @Scheduled(fixedRate = SCHEDULER_INTERVAL)
    public void checkForOverdueTodos() {
        log.info("checkForOverdueTodos every {} seconds", SCHEDULER_INTERVAL / 1000L);
        todoService.updateStatus(OVERDUE.name(), emptySet());
    }
}
