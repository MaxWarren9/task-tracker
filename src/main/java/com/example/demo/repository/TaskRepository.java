package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TaskRowMapper mapper = new TaskRowMapper();

    public Task save(Task task) {

        String sql = """
                INSERT INTO tasker.tasks(title, description, dead_line,
                                  author, assignee, status,
                                  created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING *
                """;

        return jdbcTemplate.queryForObject(sql, mapper,
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getAuthor(),
                task.getAssignee(),
                task.getStatus()
                    .name(),
                task.getCreatedAt()
        );
    }

    public Optional<Task> findById(long id) {
        String sql = "SELECT * FROM tasker.tasks WHERE id = ? AND status <> 'DELETE'";

        return jdbcTemplate.query(sql, mapper, id)
                           .stream()
                           .findFirst();
    }

    public Task update(Task task) {

        String sql = """
                UPDATE tasker.tasks
                SET title = ?,
                    description = ?,
                    dead_line = ?,
                    assignee = ?,
                    status = ?,
                    updated_at = ?
                WHERE id = ?
                RETURNING *
                """;

        return jdbcTemplate.queryForObject(sql, mapper,
                task.getTitle(),
                task.getDescription(),
                task.getDeadline(),
                task.getAssignee(),
                task.getStatus()
                    .name(),
                task.getUpdatedAt(),
                task.getId()
        );
    }

    public List<Task> findAllFiltered(String status, Long assignee) {

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM tasker.tasks WHERE status <> 'DELETE'"
        );

        if (status != null) {
            sql.append(" AND status = '")
               .append(status)
               .append("'");
        }

        if (assignee != null) {
            sql.append(" AND assignee = ")
               .append(assignee);
        }

        return jdbcTemplate.query(sql.toString(), mapper);
    }
}