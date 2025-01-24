package io.github.udayhe.todo_app.repository;

import io.github.udayhe.todo_app.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {

    @Query("SELECT t FROM Todo t WHERE t.dueTime < :currentTime AND t.status <> 'OVERDUE'")
    List<Todo> findOverdueTodos(@Param("currentTime") Long currentTime);
}
