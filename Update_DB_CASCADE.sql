-- Script to update existing database with CASCADE policies
-- Run this if your database already exists
-- IMPORTANT: First run Check_Constraints.sql to see your constraint names!

USE StudentSystemDB;

-- Step 1: Find and drop existing foreign key constraints
-- (You may need to adjust constraint names based on Check_Constraints.sql results)

-- Update Enrollment table - studentId foreign key
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = 'StudentSystemDB' 
    AND TABLE_NAME = 'Enrollment' 
    AND COLUMN_NAME = 'studentId' 
    AND REFERENCED_TABLE_NAME = 'Student'
    LIMIT 1
);

SET @sql = CONCAT('ALTER TABLE Enrollment DROP FOREIGN KEY ', @constraint_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Update Enrollment table - courseId foreign key
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = 'StudentSystemDB' 
    AND TABLE_NAME = 'Enrollment' 
    AND COLUMN_NAME = 'courseId' 
    AND REFERENCED_TABLE_NAME = 'Course'
    LIMIT 1
);

SET @sql = CONCAT('ALTER TABLE Enrollment DROP FOREIGN KEY ', @constraint_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add CASCADE constraints to Enrollment
ALTER TABLE Enrollment 
ADD CONSTRAINT enrollment_student_fk 
FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE CASCADE;

ALTER TABLE Enrollment 
ADD CONSTRAINT enrollment_course_fk 
FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE;

-- Update Student table - supervisorId foreign key
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = 'StudentSystemDB' 
    AND TABLE_NAME = 'Student' 
    AND COLUMN_NAME = 'supervisorId' 
    AND REFERENCED_TABLE_NAME = 'Lecturer'
    LIMIT 1
);

SET @sql = CONCAT('ALTER TABLE Student DROP FOREIGN KEY ', @constraint_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add SET NULL constraint to Student
ALTER TABLE Student 
ADD CONSTRAINT student_supervisor_fk 
FOREIGN KEY (supervisorId) REFERENCES Lecturer(id) ON DELETE SET NULL;

-- Update LecturerCourse table - lecturerId foreign key
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = 'StudentSystemDB' 
    AND TABLE_NAME = 'LecturerCourse' 
    AND COLUMN_NAME = 'lecturerId' 
    AND REFERENCED_TABLE_NAME = 'Lecturer'
    LIMIT 1
);

SET @sql = CONCAT('ALTER TABLE LecturerCourse DROP FOREIGN KEY ', @constraint_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Update LecturerCourse table - courseId foreign key
SET @constraint_name = (
    SELECT CONSTRAINT_NAME 
    FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
    WHERE TABLE_SCHEMA = 'StudentSystemDB' 
    AND TABLE_NAME = 'LecturerCourse' 
    AND COLUMN_NAME = 'courseId' 
    AND REFERENCED_TABLE_NAME = 'Course'
    LIMIT 1
);

SET @sql = CONCAT('ALTER TABLE LecturerCourse DROP FOREIGN KEY ', @constraint_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add CASCADE constraints to LecturerCourse
ALTER TABLE LecturerCourse 
ADD CONSTRAINT lecturercourse_lecturer_fk 
FOREIGN KEY (lecturerId) REFERENCES Lecturer(id) ON DELETE CASCADE;

ALTER TABLE LecturerCourse 
ADD CONSTRAINT lecturercourse_course_fk 
FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE;

