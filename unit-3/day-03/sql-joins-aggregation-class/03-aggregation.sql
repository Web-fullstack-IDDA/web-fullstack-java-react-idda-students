-- ============================================================
-- 03: Aggregation, GROUP BY, and HAVING
-- ============================================================
--
-- CONTEXT:
-- JOINs combine tables. Aggregation SUMMARIZES data:
-- "How many tasks per project?" "What's the latest due date?"
-- "Which users have the most work?"
--
-- KEY CONCEPT: Aggregate Functions
-- ---------------------------------------------------------
--   COUNT(*)      -- count all rows
--   COUNT(column) -- count non-NULL values in that column
--   SUM(column)   -- sum of values
--   AVG(column)   -- average
--   MAX(column)   -- maximum value
--   MIN(column)   -- minimum value
--
-- KEY CONCEPT: GROUP BY
-- ---------------------------------------------------------
-- GROUP BY splits rows into groups and applies aggregates
-- per group. Every non-aggregate column in SELECT MUST
-- appear in GROUP BY.
--
-- KEY CONCEPT: HAVING vs WHERE
-- ---------------------------------------------------------
-- WHERE filters ROWS (before grouping).
-- HAVING filters GROUPS (after aggregation).
-- If it uses an aggregate function -> HAVING.
-- If it doesn't -> WHERE.
-- ============================================================

USE ironboard;


-- ---------------------------------------------------------
-- Basic Aggregation (no GROUP BY) -- all table is treated as an implicit single group
-- ---------------------------------------------------------

-- Total task count
SELECT COUNT(*) AS total_tasks FROM tasks;
-- Step by step:
-- 1. FROM tasks                     -> start with all 8 rows in the tasks table
-- 2. SELECT COUNT(*) AS total_tasks -> count every row, label the column "total_tasks"

-- Count of ASSIGNED tasks (COUNT(column) skips NULLs)
-- PATTERN: COUNT(*) counts all rows. COUNT(column) counts non-NULL values.
SELECT COUNT(assignee_id) AS assigned_tasks FROM tasks;
-- Step by step:
-- 1. FROM tasks                              -> start with all 8 rows
-- 2. SELECT COUNT(assignee_id)               -> count only rows where assignee_id is NOT NULL
--           AS assigned_tasks                   (skips the 3 NULL assignees)

-- Due date range
SELECT
    MIN(due_date) AS earliest_due,
    MAX(due_date) AS latest_due
FROM tasks;
-- Step by step:
-- 1. FROM tasks              -> start with all 8 rows
-- 2. SELECT MIN(due_date),   -> scan all due_date values, return the smallest
--          MAX(due_date)     -> and the largest


-- ---------------------------------------------------------
-- GROUP BY: Tasks per Status
-- ---------------------------------------------------------
-- "How many tasks are in each status?"
--
-- GROUP BY status creates three groups: DONE, IN_PROGRESS, TODO.
-- COUNT(*) counts rows within each group.

SELECT
    status,
    COUNT(*) AS task_count
FROM tasks
GROUP BY status;
-- Step by step:
-- 1. FROM tasks                            -> start with all 8 rows
-- 2. GROUP BY status                       -> split rows into groups: DONE(2), IN_PROGRESS(2), TODO(4)
--    (aggregation per group)               -> COUNT(*): TODO=4, IN_PROGRESS=2, DONE=2
-- 3. SELECT status, COUNT(*) AS task_count -> pick the group label and computed count


-- ---------------------------------------------------------
-- GROUP BY with JOIN: Tasks per Project
-- ---------------------------------------------------------
-- "How many tasks does each project have?"

SELECT
    p.name AS project_name,
    COUNT(t.id) AS task_count
FROM projects p
LEFT JOIN tasks t ON p.id = t.project_id
GROUP BY p.id, p.name;
-- Step by step:
-- 1. FROM projects p              -> start with all 3 projects
-- 2. LEFT JOIN tasks t ON ...     -> attach matching tasks to each project
--                                    (LEFT keeps projects even if they have 0 tasks)
-- 3. GROUP BY p.id, p.name        -> collapse into one row per project
--    (aggregation per group)       -> COUNT(t.id): IronBoard=5, IronSchool=2, IronShop=1
--                                    (COUNT(t.id) returns 0 for projects with no tasks)
-- 4. SELECT p.name,               -> pick the project name and computed count
--           COUNT(t.id) AS task_count

-- ---------------------------------------------------------
-- GROUP BY: Tasks per Status per Project
-- ---------------------------------------------------------
-- "For each project, how many tasks are in each status?"

SELECT
    p.name AS project,
    t.status,
    COUNT(*) AS count
FROM projects p
JOIN tasks t ON p.id = t.project_id
GROUP BY p.id, p.name, t.status
ORDER BY p.name, t.status;
-- Step by step:
-- 1. FROM projects p                    -> start with all 3 projects
-- 2. JOIN tasks t ON ...                -> attach tasks (INNER: only matched rows)
-- 3. GROUP BY p.id, p.name, t.status   -> split into groups by project + status
--                                          e.g. (IronBoard, DONE), (IronBoard, TODO), ...
--    (aggregation per group)            -> COUNT(*) computed for each project-status combination
-- 4. SELECT p.name, t.status,          -> pick group labels and computed count
--           COUNT(*) AS count
-- 5. ORDER BY p.name, t.status          -> sort alphabetically by project, then status


-- ---------------------------------------------------------
-- GROUP BY: Tasks per Assignee
-- ---------------------------------------------------------
-- Use COALESCE to replace NULL with a readable label.
-- COALESCE(a, b) returns a if not NULL, else b.
--
-- NOTE: COUNT(t.id) vs COUNT(*) — both return the same
-- result here because t.id (the left table's PK) is never
-- NULL. We use COUNT(t.id) to make the intent explicit:
-- "count tasks", not just "count rows".

SELECT
    COALESCE(u.name, 'Unassigned') AS assignee,
    COUNT(t.id) AS task_count
FROM tasks t
LEFT JOIN users u ON t.assignee_id = u.id
GROUP BY u.id, u.name
ORDER BY task_count DESC;
-- Step by step:
-- 1. FROM tasks t                              -> start with all 8 tasks
-- 2. LEFT JOIN users u ON ...                  -> attach user info (LEFT keeps unassigned tasks)
-- 3. GROUP BY u.id, u.name                     -> group by assignee (NULLs form one group)
--    (aggregation per group)                   -> COUNT(t.id): Bob=3, NULL=3, Carol=2
-- 4. SELECT COALESCE(u.name, 'Unassigned'),    -> replace NULL label with 'Unassigned'
--           COUNT(t.id) AS task_count          -> output computed count per assignee
-- 5. ORDER BY task_count DESC                  -> show busiest assignees first


-- ---------------------------------------------------------
-- HAVING: Filter Groups
-- ---------------------------------------------------------

-- "Which projects have more than 2 tasks?"
SELECT
    p.name AS project_name,
    COUNT(t.id) AS task_count
FROM projects p
JOIN tasks t ON p.id = t.project_id
GROUP BY p.id, p.name
HAVING COUNT(t.id) > 2;
-- Step by step:
-- 1. FROM projects p              -> start with all 3 projects
-- 2. JOIN tasks t ON ...          -> attach tasks (INNER: matched only)
-- 3. GROUP BY p.id, p.name        -> one group per project
--    (aggregation per group)       -> COUNT(t.id): IronBoard=5, IronSchool=2, IronShop=1
-- 4. HAVING COUNT(t.id) > 2       -> discard groups where count ≤ 2; only IronBoard (5) survives
-- 5. SELECT p.name,               -> output surviving project names
--           COUNT(t.id) AS task_count -> and their task counts
-- Returns: IronBoard (5 tasks)

-- COMMON MISTAKE: Using WHERE instead of HAVING for aggregates.
--   WHERE COUNT(t.id) > 2  -- ERROR: aggregate in WHERE
--   HAVING COUNT(t.id) > 2 -- CORRECT: aggregate in HAVING


-- "Which users are assigned more than 1 task?"
SELECT
    u.name,
    COUNT(t.id) AS task_count
FROM users u
JOIN tasks t ON u.id = t.assignee_id
GROUP BY u.id, u.name
HAVING COUNT(t.id) > 1;
-- Step by step:
-- 1. FROM users u                 -> start with all 4 users
-- 2. JOIN tasks t ON ...          -> attach assigned tasks (INNER: drops users with 0 tasks)
-- 3. GROUP BY u.id, u.name        -> one group per user (David already dropped by INNER JOIN)
--    (aggregation per group)       -> COUNT(t.id): Bob=3, Carol=2
-- 4. HAVING COUNT(t.id) > 1       -> keep only users with more than 1; both survive
-- 5. SELECT u.name,               -> output surviving user names
--           COUNT(t.id) AS task_count -> and their task counts


-- "Which statuses have at least 2 tasks?"
SELECT status, COUNT(*) AS count
FROM tasks
GROUP BY status
HAVING COUNT(*) >= 2;
-- Step by step:
-- 1. FROM tasks                       -> start with all 8 rows
-- 2. GROUP BY status                  -> three groups: DONE(2), IN_PROGRESS(2), TODO(4)
--    (aggregation per group)          -> COUNT(*): TODO=4, IN_PROGRESS=2, DONE=2
-- 3. HAVING COUNT(*) >= 2             -> keep groups with 2 or more; all three survive
-- 4. SELECT status, COUNT(*) AS count -> output status and count for surviving groups


-- ---------------------------------------------------------
-- WHERE + HAVING together
-- ---------------------------------------------------------
-- "For each project, how many TODO tasks are there?
--  Only show projects with at least 1 TODO task."
--
-- WHERE filters ROWS (only TODO tasks) BEFORE grouping.
-- HAVING filters GROUPS (only groups with count >= 1) AFTER counting.

SELECT
    p.name AS project,
    COUNT(t.id) AS todo_count
FROM projects p
JOIN tasks t ON p.id = t.project_id
WHERE t.status = 'TODO'
GROUP BY p.id, p.name
HAVING COUNT(t.id) >= 1;
-- Step by step:
-- 1. FROM projects p            -> start with all 3 projects
-- 2. JOIN tasks t ON ...        -> attach tasks (INNER: matched only)
--                                  combined result: 8 rows (all tasks with their project)
-- 3. WHERE t.status = 'TODO'    -> filter rows BEFORE grouping: keep only TODO tasks
--                                  down to 4 rows
-- 4. GROUP BY p.id, p.name      -> group remaining rows by project
--    (aggregation per group)     -> COUNT(t.id) computed per project from TODO-only rows
-- 5. HAVING COUNT(t.id) >= 1    -> keep groups with at least 1 TODO task
-- 6. SELECT p.name,             -> output surviving project names
--           COUNT(t.id) AS todo_count -> and their TODO counts


-- ---------------------------------------------------------
-- Calculating Percentages
-- ---------------------------------------------------------
-- "What percentage of tasks is in each status?"
--

SELECT
    status,
    COUNT(*) AS task_count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM tasks), 1) AS percentage
FROM tasks
GROUP BY status
ORDER BY percentage DESC;
-- Step by step:
-- 1. FROM tasks                       -> all 8 rows
-- 2. GROUP BY status                  -> DONE(2), IN_PROGRESS(2), TODO(4)
-- 3. The subquery (SELECT COUNT(*) FROM tasks) returns 8 (the total)
--    TODO: 4 * 100.0 / 8 = 50.0%
--    IN_PROGRESS: 2 * 100.0 / 8 = 25.0%
--    DONE: 2 * 100.0 / 8 = 25.0%
-- 4. ROUND(..., 1) rounds to 1 decimal place
-- 5. ORDER BY percentage DESC         -> highest first


-- ============================================================
-- EXPANDED: NULL Behavior in GROUP BY
-- ============================================================
-- NULL values are grouped together into ONE group.
-- This can be surprising if you're not expecting it.

SELECT assignee_id, COUNT(*) AS task_count
FROM tasks
GROUP BY assignee_id;
-- Step by step:
-- 1. FROM tasks                       -> all 8 rows
-- 2. GROUP BY assignee_id             -> groups: 2(Bob), 3(Carol), NULL(unassigned)
--                                        NULL values are NOT ignored — they form their own group
--    (aggregation per group)           -> COUNT(*): Bob=3, Carol=2, NULL=3
-- 3. SELECT assignee_id, COUNT(*)     -> output group key and computed count
-- Output includes a row where assignee_id = NULL with count = 3
-- (the three unassigned tasks are grouped together)

-- TIP: Use COALESCE to give the NULL group a readable label:
SELECT
    COALESCE(u.name, 'Unassigned') AS assignee,
    COUNT(t.id) AS task_count
FROM tasks t
LEFT JOIN users u ON t.assignee_id = u.id
GROUP BY u.id, u.name
ORDER BY task_count DESC;
-- Step by step:
-- 1. FROM tasks t                            -> all 8 tasks
-- 2. LEFT JOIN users u ON ...                -> attach user names (NULLs stay as NULL)
-- 3. GROUP BY u.id, u.name                   -> group by user (NULL forms its own group)
--    (aggregation per group)                 -> COUNT(t.id): Bob=3, NULL=3, Carol=2
-- 4. SELECT COALESCE(u.name, 'Unassigned'),  -> replace the NULL label with readable text
--           COUNT(t.id) AS task_count        -> output computed count per group
-- 5. ORDER BY task_count DESC                -> busiest first
