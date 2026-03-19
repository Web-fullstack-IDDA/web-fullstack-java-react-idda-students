-- ============================================================
-- 01: Extended Seed Data with Users
-- ============================================================
--
-- CONTEXT:
-- Days 1-2 worked with projects and tasks only. Today we add
-- users and link them to projects (owner) and tasks (assignee).
-- This creates the multi-table relationships needed for JOINs.
--
-- WHY three tables?
-- Real applications almost always have multiple related tables.
-- A task belongs to a project. A project is owned by a user.
-- A task can be assigned to a user. Querying across these
-- relationships is the core skill of SQL.
--
-- PATTERN: Full schema reset
-- ---------------------------------------------------------
-- For a clean starting point, we drop everything and recreate.
-- This ensures the seed data is consistent regardless of what
-- experiments were run on Days 1-2.
-- ============================================================

CREATE DATABASE IF NOT EXISTS ironboard;
USE ironboard;


-- ---------------------------------------------------------
-- Create the full schema (users -> projects -> tasks)
-- ---------------------------------------------------------

CREATE TABLE users (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    role       VARCHAR(20) DEFAULT 'DEVELOPER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    owner_id    INT,
    CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE tasks (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status      VARCHAR(20) DEFAULT 'TODO',
    priority    VARCHAR(20),
    due_date    DATE,
    project_id  INT NOT NULL,
    assignee_id INT,
    CONSTRAINT fk_task_project  FOREIGN KEY (project_id)  REFERENCES projects(id),
    CONSTRAINT fk_task_assignee FOREIGN KEY (assignee_id) REFERENCES users(id)
);


-- ---------------------------------------------------------
-- Seed users
-- ---------------------------------------------------------
INSERT INTO users (name, email, role) VALUES
    ('Alice Smith',    'alice@ironhack.com', 'MANAGER'),
    ('Bob Johnson',    'bob@ironhack.com',   'DEVELOPER'),
    ('Carol Williams', 'carol@ironhack.com', 'DEVELOPER'),
    ('David Brown',    'david@ironhack.com', 'DEVELOPER');

-- ---------------------------------------------------------
-- Seed projects (with owners)
-- ---------------------------------------------------------
-- Alice owns IronBoard and IronSchool. Bob owns IronShop.
INSERT INTO projects (name, description, owner_id) VALUES
    ('IronBoard',  'Project management application', 1),
    ('IronSchool', 'Learning management system',     1),
    ('IronShop',   'E-commerce platform',            2);

-- ---------------------------------------------------------
-- Seed tasks (with varying assignees)
-- ---------------------------------------------------------
-- NOTE: Some tasks have assignee_id = NULL (unassigned).
-- This is intentional -- it makes LEFT JOIN exercises meaningful.
-- Tasks 6, 7, 8 have no assignee.
INSERT INTO tasks (title, description, status, priority, due_date, project_id, assignee_id) VALUES
    ('Setup Spring Boot',       'Initialize the project',              'DONE',        'HIGH',   '2026-01-15', 1, 2),
    ('Create REST endpoints',   'Build CRUD endpoints',                'DONE',        'HIGH',   '2026-01-20', 1, 2),
    ('Add database layer',      'Migrate from HashMap to JPA',         'IN_PROGRESS', 'HIGH',   '2026-02-01', 1, 2),
    ('Write unit tests',        'Test all service methods',            'TODO',        'MEDIUM', '2026-02-10', 1, 3),
    ('Deploy to production',    'Set up CI/CD pipeline',               'TODO',        'LOW',    '2026-02-28', 1, 3),
    ('Design course structure', 'Plan units, lessons, and labs',       'DONE',        'HIGH',   '2026-01-10', 2, NULL),
    ('Build student portal',    'Create React frontend for students',  'IN_PROGRESS', 'HIGH',   '2026-02-15', 2, NULL),
    ('Create product catalog',  'Build product CRUD with search',      'TODO',        'HIGH',   '2026-03-01', 3, NULL);
