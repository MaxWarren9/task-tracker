package com.example.demo.repository.mapper;

import com.example.demo.enums.TaskEventType;
import com.example.demo.model.TaskEvent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskEventRowMapper implements RowMapper<TaskEvent> {

    @Override
    public TaskEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TaskEvent.builder()
                        .id(rs.getLong("id"))
                        .taskId(rs.getLong("task_id"))
                        .eventType(TaskEventType.valueOf(rs.getString("event_type")))
                        .eventPayload(rs.getString("event_payload"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build();
    }
}