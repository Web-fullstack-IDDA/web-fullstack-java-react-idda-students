-- ============================================================
-- 02: JOINs -- Combining Multiple Tables
-- ============================================================
--
-- CONTEXT:
-- Until now, every query touched ONE table. But our data spans
-- three tables: tasks belong to projects, projects to users.
-- JOINs let you combine tables into a single result set.
--
-- KEY CONCEPT: INNER JOIN vs LEFT JOIN
-- ---------------------------------------------------------
-- INNER JOIN: Returns ONLY rows with a match in BOTH tables.
--   If a task has no assignee (NULL), INNER JOIN drops that row.
--
-- LEFT JOIN: Returns ALL rows from the LEFT table.
--   If there's no match on the right, columns are NULL.
--   The task with no assignee still appears -- assignee is NULL.
--
-- PATTERN: Choose the right JOIN
-- ---------------------------------------------------------
-- Ask yourself: "Do I want to see rows with no match?"
--   YES -> LEFT JOIN
--   NO  -> INNER JOIN
-- ============================================================

USE ironboard;


-- ---------------------------------------------------------
-- INNER JOIN: Tasks with their project names
-- ---------------------------------------------------------
-- "What are all the tasks and which project does each one belong to?"
--
-- Every task HAS a project (project_id is NOT NULL), so
-- INNER JOIN is fine here -- no rows will be lost.

SELECT
    tasks.title,
    tasks.status,
    projects.name AS project_name
FROM tasks
INNER JOIN projects ON tasks.project_id = projects.id;
-- Step by step:
-- 1. FROM tasks                          -> start with all 8 task rows
-- 2. INNER JOIN projects ON ...          -> for each task, find the project where
--                                           tasks.project_id = projects.id
--                                           every task has a project, so all 8 rows match
-- 3. SELECT tasks.title, tasks.status,
--           projects.name                -> pick columns from both tables
--
-- ON clause: specifies how the tables relate.
-- tasks.project_id = projects.id means "match task's project_id to project's id"


-- ---------------------------------------------------------
-- INNER JOIN: Multiple tables
-- ---------------------------------------------------------
-- "What are all the tasks with their project name and assignee name?"
--

SELECT
    t.title,
    t.status,
    p.name AS project_name,
    u.name AS assignee_name
FROM tasks t
INNER JOIN projects p ON t.project_id = p.id
INNER JOIN users u ON t.assignee_id = u.id;
-- Step by step:
-- 1. FROM tasks t                        -> start with all 8 tasks
-- 2. INNER JOIN projects p ON ...        -> match each task to its project (8 rows, all match)
-- 3. INNER JOIN users u ON ...           -> match each task to its assignee
--                                           tasks 6, 7, 8 have assignee_id = NULL
--                                           NULL ≠ any user.id, so INNER JOIN drops them
-- 4. SELECT t.title, t.status, ...       -> pick columns from all three tables


-- ---------------------------------------------------------
-- LEFT JOIN: Keep ALL tasks, even unassigned ones
-- ---------------------------------------------------------
-- "What are all the tasks with their project and assignee, including unassigned ones?"
--
-- LEFT JOIN returns all rows from the LEFT table (tasks).
-- If there's no matching user, the user columns are NULL.
--
-- WHY LEFT JOIN here?
-- We want to see ALL tasks, including unassigned ones.
-- Unassigned tasks are just as important to track.

SELECT
    t.title,
    t.status,
    p.name AS project_name,
    u.name AS assignee_name
FROM tasks t
INNER JOIN projects p ON t.project_id = p.id
LEFT JOIN users u ON t.assignee_id = u.id;
-- Step by step:
-- 1. FROM tasks t                        -> start with all 8 tasks
-- 2. INNER JOIN projects p ON ...        -> match each task to its project (all 8 match)
-- 3. LEFT JOIN users u ON ...            -> try to match each task to its assignee
--                                           tasks 6, 7, 8 have no match (NULL assignee_id)
--                                           LEFT JOIN keeps them anyway, filling u.* with NULL
-- 4. SELECT t.title, ..., u.name         -> pick columns; unmatched rows show NULL for u.name
-- Result: all 8 rows — unassigned tasks show NULL for assignee_name
-- NOTE: Now all 8 rows! Tasks 6-8 show NULL for assignee_name.

-- TIP: Use INNER JOIN for required relationships (task -> project)
-- and LEFT JOIN for optional relationships (task -> assignee).


-- ---------------------------------------------------------
-- LEFT JOIN + IS NULL: Find rows with NO match
-- ---------------------------------------------------------
-- "Which tasks have no assignee?"
--

SELECT t.title, t.status
FROM tasks t
LEFT JOIN users u ON t.assignee_id = u.id
WHERE u.id IS NULL;
-- Step by step:
-- 1. FROM tasks t                   -> start with all 8 tasks
-- 2. LEFT JOIN users u ON ...       -> try to match each task to a user
--                                      tasks 6, 7, 8 have no match -> u.* columns are NULL
-- 3. WHERE u.id IS NULL             -> keep only rows where the join found NO match
--                                      this filters out the 5 matched rows
-- 4. SELECT t.title, t.status       -> show just the task info for unmatched rows
-- Result: 3 rows — the unassigned tasks (6, 7, 8)


-- "Which users have no tasks assigned to them?"
--

SELECT u.name, u.email
FROM users u
LEFT JOIN tasks t ON u.id = t.assignee_id
WHERE t.id IS NULL;
-- Step by step:
-- 1. FROM users u                   -> start with all 4 users
-- 2. LEFT JOIN tasks t ON ...       -> try to match each user to their assigned tasks
--                                      David (user 4) has no tasks -> t.* columns are NULL
--                                      Bob/Carol match multiple tasks (duplicated rows)
--                                      Alice matches 0 tasks as assignee -> t.* is NULL
-- 3. WHERE t.id IS NULL             -> keep only users with NO matching tasks
-- 4. SELECT u.name, u.email         -> show their info


-- ---------------------------------------------------------
-- Projects with their owner
-- ---------------------------------------------------------
-- "What are all the projects and who owns each one?"
--
-- All projects, including those with no owner (NULL owner_id).

SELECT
    p.name AS project_name,
    p.description,
    u.name AS owner_name,
    u.email AS owner_email
FROM projects p
LEFT JOIN users u ON p.owner_id = u.id;
-- Step by step:
-- 1. FROM projects p                -> start with all 3 projects
-- 2. LEFT JOIN users u ON ...       -> match each project to its owner via owner_id
--                                      all 3 projects have an owner, so all match
--                                      LEFT JOIN ensures ownerless projects would still appear
-- 3. SELECT p.name, ..., u.name     -> pick columns from both tables


-- ---------------------------------------------------------
-- Full picture: Tasks with project AND owner AND assignee
-- ---------------------------------------------------------
-- "For each task, what is its project, who owns the project, and who is it assigned to?"
--
-- Three JOINs in one query. This is common in real applications.
--
-- PATTERN: Chain JOINs to traverse relationships.
-- tasks -> projects -> users (owner)
-- tasks -> users (assignee)

SELECT
    t.title        AS task,
    t.status,
    p.name         AS project,
    owner.name     AS project_owner,
    assignee.name  AS assigned_to
FROM tasks t
INNER JOIN projects p    ON t.project_id  = p.id
LEFT JOIN  users owner   ON p.owner_id    = owner.id
LEFT JOIN  users assignee ON t.assignee_id = assignee.id;
-- Step by step:
-- 1. FROM tasks t                           -> start with all 8 tasks
-- 2. INNER JOIN projects p ON ...           -> match each task to its project (all 8 match)
-- 3. LEFT JOIN users owner ON p.owner_id    -> match each project to its owner
--                                              (LEFT: keeps tasks even if project has no owner)
-- 4. LEFT JOIN users assignee ON t.assignee_id -> match each task to its assignee
--                                              (LEFT: keeps tasks with NULL assignee)
--                                              users table is joined TWICE with different aliases
-- 5. SELECT columns from all four references -> task info + project + owner name + assignee name


-- ============================================================
-- Anti-Join Pattern
-- ============================================================
-- We showed "unassigned tasks" and "users with no tasks" above.
-- Here's another common anti-join: "projects with no tasks."
--
-- "Which tasks have no assignee?"
SELECT t.title, t.status, u.name AS assignee_name
FROM tasks t
LEFT JOIN users u ON t.assignee_id = u.id
WHERE u.id IS NULL;
-- Step by step:
-- 1. FROM tasks t                   -> all 8 tasks
-- 2. LEFT JOIN users u ON ...       -> attach user info; unassigned tasks get NULL
-- 3. WHERE u.id IS NULL             -> keep only the unmatched (unassigned) tasks
-- 4. SELECT t.title, t.status, ...  -> assignee_name will be NULL for all returned rows
