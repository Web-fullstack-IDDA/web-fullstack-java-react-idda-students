-- ============================================================
-- 01: CREATE DATABASE & Basic Navigation
-- ============================================================

-- ---------------------------------------------------------
-- Step 1: Show existing databases
-- ---------------------------------------------------------
-- SHOW DATABASES lists every database on the MySQL server.
-- You'll see system databases (mysql, sys, information_schema,
-- performance_schema) plus any you've created.

SHOW DATABASES;


-- ---------------------------------------------------------
-- Step 2: Create the IronBoard database
-- ---------------------------------------------------------
-- CREATE DATABASE creates a new, empty database.
--
-- WHY IF NOT EXISTS?
-- Without it, running this script a second time throws:
--   ERROR 1007: Can't create database 'ironboard'; database exists
-- With it, MySQL silently skips if it already exists.

CREATE DATABASE IF NOT EXISTS ironboard;


-- ---------------------------------------------------------
-- Step 3: Select the database
-- ---------------------------------------------------------
-- USE tells MySQL: "all following commands apply to this database."
--
-- COMMON MISTAKE: Forgetting USE before CREATE TABLE.
-- Without it, you'll get:
--   ERROR 1046: No database selected
--

USE ironboard;


-- ---------------------------------------------------------
-- Step 4: Verify
-- ---------------------------------------------------------
-- After USE, SHOW TABLES lists all tables in the current database.

SHOW TABLES;

-- You should see: Empty set (no tables yet)


-- ---------------------------------------------------------
-- Bonus: Drop and recreate (for a clean slate)
-- ---------------------------------------------------------
-- DROP DATABASE deletes the entire database and ALL its data.
--
-- WARNING: This is irreversible. In production, you would
-- NEVER run this. In development, it's useful for resetting.
--
-- Uncomment the lines below ONLY if you need a fresh start:

-- DROP DATABASE IF EXISTS ironboard;
-- CREATE DATABASE ironboard;
-- USE ironboard;

