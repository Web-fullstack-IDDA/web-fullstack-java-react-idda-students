-- ============================================================
-- 03: INSERT, UPDATE, and DELETE
-- ============================================================
--
-- KEY CONCEPT: The Golden Rule
-- ---------------------------------------------------------
-- ALWAYS use WHERE with UPDATE and DELETE.
--
--   UPDATE tasks SET status = 'TODO';
--   -- This changes EVERY row in the table. All 8 tasks are now TODO.
--
--   DELETE FROM tasks;
--   -- This deletes EVERY row. Your table is now empty.
--
-- ============================================================

USE ironboard;


-- ---------------------------------------------------------
-- INSERT: Adding New Data
-- ---------------------------------------------------------

-- Single row insert
INSERT INTO projects (name, description)
VALUES ('IronChat', 'Real-time messaging app');

-- Verify
SELECT * FROM projects WHERE name = 'IronChat';


-- Multi-row insert (more efficient than separate INSERTs)
INSERT INTO tasks (title, status, priority, project_id) VALUES
    ('Create user model',    'TODO', 'HIGH',   4),
    ('Build chat interface',  'TODO', 'MEDIUM', 4),
    ('Add emoji support',    'TODO', 'LOW',    4);

-- Verify
SELECT * FROM tasks WHERE project_id = 4;


-- ---------------------------------------------------------
-- UPDATE: Modifying Existing Data
-- ---------------------------------------------------------

-- Preview the row
SELECT * FROM tasks WHERE id = 4;

-- Update the row
UPDATE tasks
SET status = 'IN_PROGRESS'
WHERE id = 4;

-- Verify the change
SELECT * FROM tasks WHERE id = 4;


-- Update multiple columns at once
UPDATE tasks
SET status = 'IN_PROGRESS',
    priority = 'HIGH'
WHERE id = 5;

-- Verify
SELECT * FROM tasks WHERE id = 5;


-- Update multiple rows (all TODO tasks in project 4 -> HIGH priority)
-- Preview the rows
SELECT * FROM tasks WHERE project_id = 4 AND status = 'TODO';

-- Update the rows
UPDATE tasks
SET priority = 'HIGH'
WHERE project_id = 4 AND status = 'TODO';

-- Verify the change
SELECT * FROM tasks WHERE project_id = 4;


-- ---------------------------------------------------------
-- DANGEROUS UPDATE (demonstration -- DO NOT run without WHERE)
-- ---------------------------------------------------------
-- COMMON MISTAKE: Forgetting WHERE on UPDATE.
--
--   UPDATE tasks SET status = 'TODO';
--   -- This resets ALL tasks to TODO. Every row is affected.
--   -- There is no "undo" in SQL (unless you're in a transaction).


-- ---------------------------------------------------------
-- DELETE: Removing Data
-- ---------------------------------------------------------

-- Preview the row
SELECT * FROM tasks WHERE id = 11;

-- Delete the row
DELETE FROM tasks WHERE id = 11;

-- Verify it's gone
SELECT * FROM tasks WHERE project_id = 4;


-- Delete multiple rows (all DONE tasks from IronBoard)
-- Preview the rows
SELECT * FROM tasks WHERE status = 'DONE' AND project_id = 1;

-- Delete the rows
DELETE FROM tasks WHERE status = 'DONE' AND project_id = 1;

-- Verify the change
SELECT * FROM tasks WHERE project_id = 1;


-- ---------------------------------------------------------
-- Foreign Key Blocks Unsafe Deletes
-- ---------------------------------------------------------
-- Try deleting a project that still has tasks:
--
-- DELETE FROM projects WHERE id = 1;
-- ERROR 1451: Cannot delete or update a parent row:
--   a foreign key constraint fails
--
-- WHY? The tasks table has rows with project_id = 1.
-- The foreign key prevents deleting the parent (project)
-- while children (tasks) still reference it.
--
-- To delete the project, first delete its tasks:
--   DELETE FROM tasks WHERE project_id = 1;
--   DELETE FROM projects WHERE id = 1;
--
-- Or define ON DELETE CASCADE on the foreign key (the database
-- automatically deletes child rows when the parent is deleted).
-- We'll see this in Spring with JPA relationships.


-- ---------------------------------------------------------
-- DANGEROUS DELETE (demonstration -- DO NOT run)
-- ---------------------------------------------------------
-- COMMON MISTAKE: Forgetting WHERE on DELETE.
--
--   DELETE FROM tasks;
--   -- Deletes EVERY row. Table is now empty.
--   -- Unlike DROP TABLE, the table structure still exists.
--   -- But the data is gone forever (without a backup).



-- ============================================================
-- EXPANDED: Soft Deletes (Real-World Pattern)
-- ============================================================
-- In production, rows are rarely DELETED. Instead, they're
-- "soft deleted" by marking them with a timestamp:
--
--   ALTER TABLE tasks ADD COLUMN deleted_at DATETIME DEFAULT NULL;
--
-- To "delete" a task:
--   UPDATE tasks SET deleted_at = NOW() WHERE id = 5;
--
-- To query only "active" tasks:
--   SELECT * FROM tasks WHERE deleted_at IS NULL;
--
-- We don't implement this in IronBoard (keeps it simple),
-- but you'll see this pattern in almost every production app.