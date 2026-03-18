-- ============================================================
-- 01: Seed Data for IronBoard
-- ============================================================
--
-- CONTEXT:
-- Yesterday we created the IronBoard schema (projects, tasks).
-- Today we populate it with realistic data so we can practice
-- SELECT, INSERT, UPDATE, and DELETE.
--
-- KEY CONCEPT: DML (Data Manipulation Language)
-- ---------------------------------------------------------
-- DML is the subset of SQL that works with DATA (not structure):
--   INSERT  -- add rows
--   SELECT  -- read rows
--   UPDATE  -- modify rows
--   DELETE  -- remove rows
--
-- PATTERN: Always specify column names in INSERT
-- ---------------------------------------------------------
-- Bad:  INSERT INTO projects VALUES (1, 'IronBoard', 'desc', NOW());
--       -- Breaks if column order changes or columns are added
-- Good: INSERT INTO projects (name, description) VALUES ('IronBoard', 'desc');
--       -- Explicit, resilient to schema changes
-- ============================================================

CREATE DATABASE IF NOT EXISTS ironboard;
USE ironboard;

-- Recreate tables if needed (safe with IF NOT EXISTS)
CREATE TABLE IF NOT EXISTS projects (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tasks (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status      VARCHAR(20) DEFAULT 'TODO',
    priority    VARCHAR(20),
    due_date    DATE,
    project_id  INT NOT NULL,
    CONSTRAINT fk_task_project FOREIGN KEY (project_id)
        REFERENCES projects(id)
);


-- ---------------------------------------------------------
-- Seed projects
-- ---------------------------------------------------------
-- Multi-row INSERT is more efficient than individual INSERTs.
-- The database sends one command instead of three.
--

INSERT INTO projects (name, description) VALUES
    ('IronBoard', 'Project management application'),
    ('IronSchool', 'Learning management system'),
    ('IronShop', 'E-commerce platform');


-- ---------------------------------------------------------
-- Seed tasks
-- ---------------------------------------------------------
-- Each task references a project_id. The FOREIGN KEY constraint
-- guarantees these IDs exist in the projects table.
--

INSERT INTO tasks (title, description, status, priority, due_date, project_id) VALUES
    ('Setup Spring Boot',       'Initialize the project with Spring Initializr', 'DONE',        'HIGH',   '2026-01-15', 1),
    ('Create REST endpoints',   'Build CRUD endpoints for projects and tasks',   'DONE',        'HIGH',   '2026-01-20', 1),
    ('Add database layer',      'Migrate from HashMap to MySQL with JPA',        'IN_PROGRESS', 'HIGH',   '2026-02-01', 1),
    ('Write unit tests',        'Test all service methods with JUnit',           'TODO',        'MEDIUM', '2026-02-10', 1),
    ('Deploy to production',    'Set up CI/CD pipeline and deploy',              'TODO',        'LOW',    '2026-02-28', 1),
    ('Design course structure', 'Plan units, lessons, and labs',                 'DONE',        'HIGH',   '2026-01-10', 2),
    ('Build student portal',    'Create React frontend for students',            'IN_PROGRESS', 'HIGH',   '2026-02-15', 2),
    ('Create product catalog',  'Build product CRUD with search',                'TODO',        'HIGH',   '2026-03-01', 3);


-- ---------------------------------------------------------
-- Verify the seed data
-- ---------------------------------------------------------
SELECT * FROM projects;
-- Expected: 3 rows (IronBoard, IronSchool, IronShop)

SELECT * FROM tasks;
-- Expected: 8 rows with various statuses and priorities


-- ---------------------------------------------------------
-- Multi-row vs Single-row INSERT Performance
-- ---------------------------------------------------------
-- Multi-row INSERT (what we used above) sends ONE command:
--   INSERT INTO tasks (...) VALUES (...), (...), (...);
--   -> 1 network round-trip, 1 transaction
--
-- Single-row INSERTs send N commands:
--   INSERT INTO tasks (...) VALUES (...);
--   INSERT INTO tasks (...) VALUES (...);
--   INSERT INTO tasks (...) VALUES (...);
--   -> 3 network round-trips, 3 transactions
--
-- For 3 rows the difference is negligible. For 10,000 rows,
-- multi-row INSERT can be 10-50x faster.
--
-- PATTERN: Always prefer multi-row INSERT when inserting
-- multiple rows at once.


-- ---------------------------------------------------------
-- INSERT with Explicit DEFAULT
-- ---------------------------------------------------------
-- You can explicitly request the default value for a column:
--   INSERT INTO projects (name, created_at) VALUES ('Test', DEFAULT);
--
-- This is equivalent to omitting the column entirely:
--   INSERT INTO projects (name) VALUES ('Test');
--
-- WHY use DEFAULT explicitly? Readability. It tells the reader
-- "I know this column has a default, and I'm using it on purpose"
-- rather than "I forgot to set this column."
