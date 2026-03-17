-- ============================================================
-- 03: ALTER TABLE & DROP TABLE
-- ============================================================

-- ---------------------------------------------------------
-- ADD COLUMN: Add a users table and link it
-- ---------------------------------------------------------
-- The IronBoard app needs users. First create the users table,
-- then add relationship columns to projects and tasks.
--
-- PATTERN: Add the independent table first, then alter
-- dependent tables to reference it.

CREATE TABLE IF NOT EXISTS users (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role  VARCHAR(20) DEFAULT 'DEVELOPER'
);

DESCRIBE users;


-- ---------------------------------------------------------
-- ADD COLUMN + FOREIGN KEY to projects
-- ---------------------------------------------------------
-- Add an owner_id column that references users.

ALTER TABLE projects
ADD COLUMN owner_id INT,
ADD CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id);

DESCRIBE projects;


-- ---------------------------------------------------------
-- ADD COLUMN + FOREIGN KEY to tasks
-- ---------------------------------------------------------
-- Add an assignee_id column. A task may or may not be assigned
-- to someone, so this is nullable by design.

ALTER TABLE tasks
ADD COLUMN assignee_id INT,
ADD CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users(id);

DESCRIBE tasks;

-- ---------------------------------------------------------
-- MODIFY COLUMN: Change column properties
-- ---------------------------------------------------------
-- Make the projects.name column wider (100 -> 200 chars)

ALTER TABLE projects MODIFY COLUMN name VARCHAR(200) NOT NULL;

DESCRIBE projects;


-- ---------------------------------------------------------
-- DROP COLUMN (use with caution)
-- ---------------------------------------------------------
-- Removes a column and ALL its data permanently.
--
-- Example (commented out to preserve our schema):
-- ALTER TABLE tasks DROP COLUMN priority;


-- ---------------------------------------------------------
-- DROP TABLE
-- ---------------------------------------------------------
-- Deletes the entire table and all its data.
--
-- PATTERN: Drop order is the REVERSE of creation order.
-- You must drop child tables (with foreign keys) before
-- parent tables:
--   1. DROP tasks   (child -- has FK to projects)
--   2. DROP projects (parent)
--
-- IF EXISTS prevents errors if the table was already dropped:

-- Uncomment to reset everything:
-- DROP TABLE IF EXISTS tasks;
-- DROP TABLE IF EXISTS projects;
-- DROP TABLE IF EXISTS users;


-- ---------------------------------------------------------
-- RENAME TABLE
-- ---------------------------------------------------------
-- Changes a table's name without affecting its data or structure.
--
-- Example (commented out to preserve schema):
-- RENAME TABLE projects TO boards;
-- DESCRIBE boards;
-- RENAME TABLE boards TO projects;  -- rename back


-- ---------------------------------------------------------
-- Final schema review
-- ---------------------------------------------------------
SHOW TABLES;
-- Expected: projects, tasks, users

-- Quick summary of all columns:
DESCRIBE users;
DESCRIBE projects;
DESCRIBE tasks;
