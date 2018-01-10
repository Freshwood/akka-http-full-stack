CREATE TABLE todos (
  id         UUID PRIMARY KEY,
  name       VARCHAR   NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT current_timestamp,
  updated_at TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE UNIQUE INDEX todos_name
  ON todos (name);