CREATE SCHEMA IF NOT EXISTS tasker;

CREATE TABLE IF NOT EXISTS tasker.tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    dead_line DATE,
    author BIGINT,
    assignee BIGINT,
    status VARCHAR(30) NOT NULL DEFAULT 'TO_DO',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP
    );