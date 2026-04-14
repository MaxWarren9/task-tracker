package com.example.demo.repository.mapper;

import com.example.demo.enums.TaskStatus;
import com.example.demo.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Task.builder()
                   .id(rs.getLong("id"))
                   .title(rs.getString("title"))
                   .description(rs.getString("description"))
                   .deadline(rs.getDate("dead_line") != null ?
                           rs.getDate("dead_line").toLocalDate() : null)
                   .author(rs.getLong("author"))
                   .assignee(rs.getLong("assignee"))
                   .status(TaskStatus.valueOf(rs.getString("status")))
                   .teamId(rs.getLong("team_id"))
                   .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                   .updatedAt(rs.getTimestamp("updated_at") != null ?
                           rs.getTimestamp("updated_at").toLocalDateTime() : null)
                   .completedAt(
                           rs.getTimestamp("completed_at") != null
                                   ? rs.getTimestamp("completed_at").toLocalDateTime()
                                   : null)
                   .build();
    }
}