package com.example.demo.repository;

import com.example.demo.model.TaskEvent;
import com.example.demo.repository.mapper.TaskEventRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskEventRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TaskEventRowMapper mapper = new TaskEventRowMapper();
    private static final String SAVE = """
                INSERT INTO tasker.task_events(task_id, event_type, event_payload, created_at)
                VALUES (?, ?, ?, ?)
                RETURNING *
                """;
    private static final String FIND_BY_ID = """
                SELECT * FROM tasker.task_events
                WHERE task_id = ?
                ORDER BY created_at ASC
                """;

    public TaskEvent save(TaskEvent event) {
        return jdbcTemplate.queryForObject(SAVE, mapper,
                event.getTaskId(),
                event.getEventType().name(),
                event.getEventPayload(),
                event.getCreatedAt()
        );
    }

    public List<TaskEvent> findByTaskId(Long taskId) {
        return jdbcTemplate.query(FIND_BY_ID, mapper, taskId);
    }
}
