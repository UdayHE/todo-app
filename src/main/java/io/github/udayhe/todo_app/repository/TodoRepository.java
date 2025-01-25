package io.github.udayhe.todo_app.repository;

import io.github.udayhe.todo_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("SELECT t FROM Todo t WHERE t.dueTime < :currentTime AND t.status NOT IN ('OVERDUE', 'COMPLETED', 'CANCELLED')")
    List<Todo> findOverdueTodos(@Param("currentTime") Long currentTime);

    @Query("SELECT t FROM Todo t WHERE (t.status = 'NEW' OR t.status = 'OVERDUE') AND t.id IN :ids")
    List<Todo> findTodosByStatusNewOrOverdue(@Param("ids") Set<String> ids);

}
