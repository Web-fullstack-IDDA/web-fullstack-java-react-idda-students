-- ============================================================
-- 02: CREATE TABLE with Constraints
-- ============================================================

-- ---------------------------------------------------------
-- Table 1: projects
-- ---------------------------------------------------------
-- This table has NO foreign keys, so it can be created first.
--
-- Column breakdown:
--   id          - Primary key, auto-generated. Never set this manually.
--   name        - Required (NOT NULL). VARCHAR(100) limits to 100 chars.
--   description - Optional (nullable). VARCHAR(500) for longer text.
--   created_at  - Auto-set to current time on INSERT via DEFAULT.
--

CREATE TABLE IF NOT EXISTS projects (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- VERIFY: Check the table structure
DESCRIBE projects;


-- ---------------------------------------------------------
-- Table 2: tasks
-- ---------------------------------------------------------
-- Tasks belong to projects. The project_id column is a FOREIGN KEY
-- that enforces this relationship at the database level.
--
-- KEY CONCEPT: Foreign Keys
-- ---------------------------------------------------------
-- A foreign key says: "this column's value MUST exist in the
-- referenced table." If you try to insert a task with
-- project_id = 999 and no project has id = 999, the database
-- rejects the INSERT with a constraint violation error.
--
-- This is called REFERENTIAL INTEGRITY -- the database
-- guarantees that relationships are always valid.
--
-- PATTERN: Named constraints with CONSTRAINT keyword
-- ---------------------------------------------------------
-- IT's not mandatory to give foreign keys a name, but it's a good practice. 
-- It makes error messages more readable.
--
--   W/O CONSTRAINT:  "Cannot add or update a child row: a foreign key constraint fails"
--   WITH CONSTRAINT: "Cannot add or update a child row: fk_task_project fails"
--

CREATE TABLE IF NOT EXISTS tasks (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status      VARCHAR(20) DEFAULT 'TODO',
    priority    VARCHAR(20) DEFAULT 'MEDIUM',
    due_date    DATE,
    project_id  INT NOT NULL,
    CONSTRAINT fk_task_project FOREIGN KEY (project_id)
        REFERENCES projects(id)
);

-- VERIFY: Check the table structure
DESCRIBE tasks;


-- ---------------------------------------------------------
-- Verify both tables exist
-- ---------------------------------------------------------
SHOW TABLES;


-- ---------------------------------------------------------
-- Test the constraints (quick experiments)
-- ---------------------------------------------------------

-- Experiment 1: AUTO_INCREMENT generates IDs automatically
INSERT INTO projects (name) VALUES ('Test Project');
INSERT INTO projects (name) VALUES ('Another Project');
SELECT * FROM projects;
-- Expected: id 1 and 2 were auto-generated

-- Experiment 2: NOT NULL prevents missing required fields
-- INSERT INTO projects (description) VALUES ('No name given');
-- Uncomment to see: ERROR 1364 - Field 'name' doesn't have a default value

-- Experiment 3: FOREIGN KEY prevents invalid references
-- INSERT INTO tasks (title, project_id) VALUES ('Orphan task', 999);
-- Uncomment to see: ERROR 1452 - Cannot add or update a child row

-- Experiment 4: Valid foreign key works
INSERT INTO tasks (title, project_id) VALUES ('Valid task', 1);
SELECT id, title, status, project_id FROM tasks;
-- Expected: status defaults to 'TODO', project_id is 1

-- Clean up experiment data
DELETE FROM tasks;
DELETE FROM projects;


-- ============================================================
-- EXPANDED: ID Strategy Alternatives
-- ============================================================
-- AUTO_INCREMENT is the default, but there are other options.
-- Knowing them helps you choose the right one for each project.
--
-- STRATEGY 1: AUTO_INCREMENT (default, what we use)
-- ---------------------------------------------------------
--   id INT AUTO_INCREMENT PRIMARY KEY
--   Pros: Simple, sequential, human-readable (id=1, id=2, id=3)
--   Cons: Predictable (security risk if exposed in URLs),
--         doesn't work across multiple database servers
--   Use: Most applications, internal IDs
--
-- STRATEGY 2: UUID (Universally Unique Identifier)
-- ---------------------------------------------------------
--   id CHAR(36) PRIMARY KEY
--   Example value: '550e8400-e29b-41d4-a716-446655440000'
--   Pros: Globally unique (no collisions even across servers),
--         not guessable (safe in URLs), no sequence gaps
--   Cons: 36 characters (larger storage), slower indexing,
--         not human-readable, no natural ordering
--   Use: Public APIs, distributed systems, merge scenarios
--   In JPA: @GeneratedValue(strategy = GenerationType.UUID)
--

-- Demo: UUID table
CREATE TABLE IF NOT EXISTS uuid_demo (
    id CHAR(36) PRIMARY KEY,
    label VARCHAR(100)
);

-- MySQL has UUID() function to generate UUIDs
INSERT INTO uuid_demo (id, label) VALUES (UUID(), 'First item');
INSERT INTO uuid_demo (id, label) VALUES (UUID(), 'Second item');
SELECT * FROM uuid_demo;
-- Notice: IDs are random strings, not sequential numbers

-- Compare with AUTO_INCREMENT (our projects table):
-- INSERT INTO projects (name) VALUES ('Test');
-- SELECT id FROM projects; -- id = 1, 2, 3 (predictable)

-- Clean up demo
DROP TABLE IF EXISTS uuid_demo;


-- ============================================================
-- EXPANDED: More Data Types
-- ============================================================
-- The lesson covers INT, VARCHAR, TEXT, DATE, DATETIME, TIMESTAMP.
-- Here are additional types you'll encounter in real projects.
--
-- ENUM: A column that can only hold predefined values.
-- ---------------------------------------------------------
-- Better than VARCHAR for fields with a fixed set of options.
-- The database REJECTS any value not in the list.
--

CREATE TABLE IF NOT EXISTS data_types_demo (
    id INT AUTO_INCREMENT PRIMARY KEY,

    -- ENUM: fixed set of values (DB enforces validity)
    status ENUM('TODO', 'IN_PROGRESS', 'DONE') DEFAULT 'TODO',

    -- DECIMAL: exact precision for money
    -- DECIMAL(10,2) = 10 max total digits, 2 after decimal point
    budget DECIMAL(10,2),

    -- BOOLEAN: true/false flag (MySQL stores as TINYINT: 0 or 1)
    is_archived BOOLEAN DEFAULT FALSE,

    -- DATE vs DATETIME vs TIMESTAMP
    -- DATE:      '2026-03-15'              (date only)
    -- DATETIME:  '2026-03-15 14:30:00'     (date + time, no timezone)
    due_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Test ENUM validation
INSERT INTO data_types_demo (status, budget, is_archived)
VALUES ('TODO', 1500.50, FALSE);
SELECT * FROM data_types_demo;

-- This would fail (invalid ENUM value):
-- INSERT INTO data_types_demo (status) VALUES ('INVALID');
-- ERROR 1265: Data truncated for column 'status'

-- Test DECIMAL precision (no floating point errors)
INSERT INTO data_types_demo (budget) VALUES (0.10 + 0.20);
SELECT budget FROM data_types_demo WHERE id = 2;
-- Returns: 0.30 (exact, not 0.30000000000000004)

-- Test BOOLEAN
INSERT INTO data_types_demo (is_archived) VALUES (TRUE);
INSERT INTO data_types_demo (is_archived) VALUES (FALSE);
SELECT id, is_archived FROM data_types_demo;
-- MySQL shows 1 (true) and 0 (false)

-- Clean up
DROP TABLE IF EXISTS data_types_demo;
