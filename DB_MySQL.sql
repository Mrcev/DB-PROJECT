CREATE DATABASE IF NOT EXISTS StudentSystemDB;
USE StudentSystemDB;

CREATE TABLE Department (
    deptId INT AUTO_INCREMENT PRIMARY KEY,
    departmentName VARCHAR(50) NOT NULL,
    hod VARCHAR(30),
    nStudent INT DEFAULT 0,
    nInstructor INT DEFAULT 0
);

CREATE TABLE Lecturer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name CHAR(30) NOT NULL,
    surname CHAR(20) NOT NULL,
    email VARCHAR(50),
    phone VARCHAR(10),
    departmentId INT,
    FOREIGN KEY (departmentId) REFERENCES Department(deptId)
);

CREATE TABLE Student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name CHAR(30) NOT NULL,
    surname CHAR(20) NOT NULL,
    dateOfBirth DATE,
    gender VARCHAR(10),
    email VARCHAR(50),
    phone VARCHAR(10),
    address VARCHAR(255),
    gpa DECIMAL(3, 2),
    departmentId INT,
    supervisorId INT,
    FOREIGN KEY (departmentId) REFERENCES Department(deptId),
    FOREIGN KEY (supervisorId) REFERENCES Lecturer(id) ON DELETE SET NULL
);

CREATE TABLE Course (
    courseId INT AUTO_INCREMENT PRIMARY KEY,
    courseName VARCHAR(30) NOT NULL,
    courseCode VARCHAR(10) NOT NULL,
    credits INT,
    departmentId INT,
    FOREIGN KEY (departmentId) REFERENCES Department(deptId)
);

CREATE TABLE Enrollment (
    enrollmentId INT AUTO_INCREMENT PRIMARY KEY,
    studentId INT NOT NULL,
    courseId INT NOT NULL,
    grade DECIMAL(5, 2),
    enrollmentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE
);

CREATE TABLE LecturerCourse (
    lecturerId INT NOT NULL,
    courseId INT NOT NULL,
    PRIMARY KEY (lecturerId, courseId),
    FOREIGN KEY (lecturerId) REFERENCES Lecturer(id) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE
);





