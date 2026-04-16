package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TaskRowMapper mapper = new TaskRowMapper();
    private static final String SAVE = """
            INSERT INTO tasker.tasks(title, description, dead_line,
                              author, assignee, status, team_id,
                              created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING *
            """;
    private static final String FIND_BY_ID = "SELECT * FROM tasker.tasks WHERE id = ? AND status <> 'DELETE'";
    private static final String UPDATE = """
            UPDATE tasker.tasks
            SET title = ?,
                description = ?,
                dead_line = ?,
                assignee = ?,
                status = ?,
                team_id = ?,
                updated_at = ?,
                completed_at = ?
            WHERE id = ?
            RETURNING *
            """;
    private static final String EXISTS_ACTIVE = """
            SELECT EXISTS (
                SELECT 1
                FROM tasker.tasks
                WHERE assignee = ?
                  AND status NOT IN ('DONE', 'DELETE')
            )
            """;

    public Task save(Task task) {

        return jdbcTemplate.queryForObject(SAVE, mapper, toParamsCreate(task));
    }

    public Optional<Task> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, mapper, id)
                           .stream()
                           .findFirst();
    }

    public Task update(Task task) {
        return jdbcTemplate.queryForObject(UPDATE, mapper, toParamsUpdate(task));
    }

    public List<Task> findAllFiltered(String status, Long assignee, Long teamId) {

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

        if (teamId != null) {
            sql.append(" AND team_id = ")
               .append(teamId);
        }

        return jdbcTemplate.query(sql.toString(), mapper);
    }

    public boolean existsActiveByAssignee(Long userId) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_ACTIVE, Boolean.class, userId)
        );
    }

    private MapSqlParameterSource toParamsCreate(Task task) {
        return new MapSqlParameterSource()
                .addValue("title", task.getTitle())
                .addValue("description", task.getDescription())
                .addValue("deadline", task.getDeadline())
                .addValue("author", task.getAuthor())
                .addValue("assignee", task.getAssignee())
                .addValue("status", task.getStatus()
                                        .name())
                .addValue("teamId", task.getTeamId())
                .addValue("createdAt", task.getCreatedAt());
    }

    private MapSqlParameterSource toParamsUpdate(Task task) {
        return new MapSqlParameterSource()
                .addValue("title", task.getTitle())
                .addValue("description", task.getDescription())
                .addValue("deadline", task.getDeadline())
                .addValue("assignee", task.getAssignee())
                .addValue("status", task.getStatus()
                                        .name())
                .addValue("teamId", task.getTeamId())
                .addValue("updatedAt", task.getUpdatedAt())
                .addValue("CompletedAt", task.getCompletedAt())
                .addValue("id", task.getId());
    }
}