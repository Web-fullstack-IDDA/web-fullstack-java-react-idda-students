-- ============================================================
-- 02: SELECT Queries
-- ============================================================
--
-- CONTEXT:
-- SELECT is the most-used SQL command. You'll write hundreds
-- of SELECT queries for every INSERT or UPDATE. This script
-- covers: basic SELECT, WHERE filters, sorting, and limiting.
--
-- KEY CONCEPT: Query Execution Order
-- ---------------------------------------------------------
-- SQL does NOT execute in the order you write it:
--
--   Written order:   SELECT → FROM → WHERE → ORDER BY → LIMIT
--   Execution order: FROM → WHERE → SELECT → ORDER BY → LIMIT
--
--   1. FROM tasks          -- pick the table
--   2. WHERE status='TODO' -- filter rows
--   3. SELECT title, ...   -- pick columns
--   4. ORDER BY due_date   -- sort results
--   5. LIMIT 10            -- cap the count
--
-- ============================================================

USE ironboard;


-- ---------------------------------------------------------
-- Basic SELECT
-- ---------------------------------------------------------

-- Select ALL columns (good for exploration, bad for production)
SELECT * FROM projects;

-- Select SPECIFIC columns (preferred -- only fetch what you need)
--
-- WHY specific columns?
-- 1. Less data transferred (faster on large tables)
-- 2. Code is self-documenting (reader knows what data is used)
-- 3. Doesn't break if columns are added/removed
SELECT name, description FROM projects;

-- Column aliases for readability
SELECT name AS project_name, description AS project_desc
FROM projects;


-- ---------------------------------------------------------
-- WHERE: Filtering Rows
-- ---------------------------------------------------------

-- Exact match
SELECT title, status, priority
FROM tasks
WHERE status = 'TODO';

-- Not equal (two syntaxes, both work)
SELECT title, status
FROM tasks
WHERE status != 'DONE';

SELECT title, status
FROM tasks
WHERE status <> 'DONE';

-- PATTERN: Common WHERE Operators
-- ---------------------------------------------------------
-- =              Exact match
-- != or <>       Not equal
-- > < >= <=      Comparison (works on numbers, dates, strings)
-- BETWEEN        Inclusive range
-- LIKE           Pattern matching (% = any chars, _ = one char)
-- IN             Matches any value in a list
-- IS NULL        Checks for NULL (NOT = NULL, which doesn't work)


-- ---------------------------------------------------------
-- WHERE with dates and ranges
-- ---------------------------------------------------------

-- Tasks due before February 2026
SELECT title, due_date
FROM tasks
WHERE due_date < '2026-02-01';

-- Tasks due in January 2026 (inclusive range)
-- BETWEEN includes both endpoints: >= start AND <= end
SELECT title, due_date
FROM tasks
WHERE due_date BETWEEN '2026-01-01' AND '2026-01-31';


-- ---------------------------------------------------------
-- LIKE: Pattern Matching
-- ---------------------------------------------------------

-- Titles containing 'test' (MySQL LIKE is case-insensitive by default)
SELECT title FROM tasks
WHERE title LIKE '%test%';

-- Titles starting with 'Create'
SELECT title FROM tasks
WHERE title LIKE 'Create%';

-- Titles ending with 'tests'
SELECT title FROM tasks
WHERE title LIKE '%tests';


-- ---------------------------------------------------------
-- IN: Match Any Value in a List
-- ---------------------------------------------------------
-- IN is cleaner than multiple OR conditions.
--
-- Instead of:
--   WHERE status = 'TODO' OR status = 'IN_PROGRESS'
-- Use:
--   WHERE status IN ('TODO', 'IN_PROGRESS')

SELECT title, status
FROM tasks
WHERE status IN ('TODO', 'IN_PROGRESS');


-- ---------------------------------------------------------
-- IS NULL: Checking for Missing Values
-- ---------------------------------------------------------
-- COMMON MISTAKE: Using = NULL instead of IS NULL.
--
-- WHERE priority = NULL    -- WRONG: always returns 0 rows
-- WHERE priority IS NULL   -- CORRECT: finds rows with no priority
--
-- WHY? In SQL, NULL means "unknown." You can't compare with
-- "unknown" -- the result is also unknown (not true, not false).

SELECT title, priority
FROM tasks
WHERE priority IS NULL;

SELECT title, priority
FROM tasks
WHERE priority IS NOT NULL;


-- ---------------------------------------------------------
-- Combining Conditions: AND / OR
-- ---------------------------------------------------------

-- AND: both conditions must be true
SELECT title, status, priority
FROM tasks
WHERE status = 'TODO' AND priority = 'HIGH';

-- OR: either condition
SELECT title, status
FROM tasks
WHERE status = 'DONE' OR status = 'IN_PROGRESS';

-- TIP: Use parentheses to control precedence.
-- AND binds tighter than OR, which can cause surprises:
--   WHERE status = 'TODO' OR status = 'IN_PROGRESS' AND priority = 'HIGH'
--   -- This means: TODO (any priority) OR (IN_PROGRESS AND HIGH)
--   -- Probably NOT what you wanted. Use parentheses:
--   WHERE (status = 'TODO' OR status = 'IN_PROGRESS') AND priority = 'HIGH'
--   -- This means: (TODO or IN_PROGRESS) AND HIGH


-- ---------------------------------------------------------
-- ORDER BY: Sorting Results
-- ---------------------------------------------------------

-- Ascending (default -- earliest date first)
SELECT title, due_date
FROM tasks
ORDER BY due_date ASC;

-- Descending (latest date first)
SELECT title, due_date
FROM tasks
ORDER BY due_date DESC;

-- Multiple sort columns: status first, then priority
SELECT title, status, priority
FROM tasks
ORDER BY status ASC, priority DESC;


-- ---------------------------------------------------------
-- LIMIT: Restricting Result Count
-- ---------------------------------------------------------

-- Top 3 tasks with nearest due date
SELECT title, due_date
FROM tasks
ORDER BY due_date ASC
LIMIT 3;


-- ---------------------------------------------------------
-- Putting It All Together
-- ---------------------------------------------------------
-- "High-priority incomplete IronBoard tasks, nearest due date first"
--

SELECT title, status, due_date
FROM tasks
WHERE project_id = 1
  AND priority = 'HIGH'
  AND status != 'DONE'
ORDER BY due_date ASC
LIMIT 10;


-- ============================================================
-- EXPANDED: DISTINCT -- Remove Duplicate Values
-- ============================================================
-- DISTINCT returns only unique values for the selected columns.
-- Useful for discovering what values exist in a column.
-- DISTINCT comes after SELECT, think of the syntax like:
-- SELECT [modifier] column1, column2
--FROM table_name;

-- What statuses exist in the tasks table?
SELECT DISTINCT status FROM tasks;
-- Returns: DONE, IN_PROGRESS, TODO (one of each, no repeats)

-- What priorities exist?
SELECT DISTINCT priority FROM tasks;
-- Returns: HIGH, MEDIUM, LOW

-- DISTINCT across multiple columns: unique COMBINATIONS
SELECT DISTINCT status, priority FROM tasks;
-- Returns each unique (status, priority) pair


-- ============================================================
-- EXPANDED: COUNT -- Preview of Aggregation (Day 3)
-- ============================================================
-- COUNT(*) counts all rows matching the condition.
-- Full aggregation (GROUP BY, HAVING) is Day 3, but simple
-- counting is natural alongside SELECT.

-- Total number of tasks
SELECT COUNT(*) AS total_tasks FROM tasks;

-- How many TODO tasks?
SELECT COUNT(*) AS todo_count FROM tasks WHERE status = 'TODO';

-- How many tasks per project? (preview of GROUP BY)
-- We'll cover GROUP BY properly on Day 3.


-- ============================================================
-- EXPANDED: Common String Mistakes
-- ============================================================

-- MISTAKE 1: Forgetting quotes around string values
-- SELECT * FROM tasks WHERE status = TODO;
-- ERROR 1054: Unknown column 'TODO' in 'where clause'
-- MySQL thinks TODO is a column name, not a string value.
-- FIX: Always quote strings:
SELECT * FROM tasks WHERE status = 'TODO';

-- MISTAKE 2: Using double quotes instead of single quotes
-- In MySQL, double quotes work by default, but in strict mode
-- or other databases (PostgreSQL), double quotes mean column names.
-- PATTERN: Always use single quotes for string VALUES.
--   'TODO'  -- string value (correct everywhere)
--   "TODO"  -- may be interpreted as column name in some databases

-- MISTAKE 3: Escaping quotes inside strings
-- To include a quote in a string, double it:
-- INSERT INTO projects (name) VALUES ('O''Brien''s Project');
-- Or use backslash: 'O\'Brien\'s Project'
