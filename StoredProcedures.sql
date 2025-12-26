DELIMITER //

-- 1. Öğrenci Bilgilerini Getiren Prosedür
CREATE PROCEDURE GetStudentInfo(IN p_studentId INT)
BEGIN
    SELECT 
        s.id, 
        s.name, 
        s.surname, 
        s.email, 
        s.gpa, 
        d.departmentName, 
        CONCAT(l.name, ' ', l.surname) AS SupervisorName
    FROM Student s
    INNER JOIN Department d ON s.departmentId = d.deptId
    LEFT JOIN Lecturer l ON s.supervisorId = l.id
    WHERE s.id = p_studentId;
END //

-- 2. Öğrencinin Aldığı Dersleri Getiren Prosedür
CREATE PROCEDURE GetStudentCourses(IN p_studentId INT)
BEGIN
    SELECT 
        c.courseCode, 
        c.courseName, 
        c.credits, 
        e.grade,
        e.enrollmentDate
    FROM Enrollment e
    INNER JOIN Course c ON e.courseId = c.courseId
    WHERE e.studentId = p_studentId;
END //

-- 3. Öğrencinin Danışman Bilgisini Getiren Prosedür
CREATE PROCEDURE GetSupervisorInfo(IN p_studentId INT)
BEGIN
    SELECT 
        l.id AS SupervisorID,
        l.name, 
        l.surname, 
        l.email, 
        l.phone,
        d.departmentName
    FROM Student s
    INNER JOIN Lecturer l ON s.supervisorId = l.id
    INNER JOIN Department d ON l.departmentId = d.deptId
    WHERE s.id = p_studentId;
END //

-- 4. Akademisyen (Lecturer) Bilgilerini Getiren Prosedür
CREATE PROCEDURE GetLecturerInfo(IN p_lecturerId INT)
BEGIN
    SELECT 
        l.id, 
        l.name, 
        l.surname, 
        l.email, 
        l.phone, 
        d.departmentName
    FROM Lecturer l
    INNER JOIN Department d ON l.departmentId = d.deptId
    WHERE l.id = p_lecturerId;
END //

-- 5. Akademisyenin Verdiği Dersleri Getiren Prosedür
CREATE PROCEDURE GetLecturerCourses(IN p_lecturerId INT)
BEGIN
    SELECT 
        c.courseCode, 
        c.courseName, 
        c.credits
    FROM LecturerCourse lc
    INNER JOIN Course c ON lc.courseId = c.courseId
    WHERE lc.lecturerId = p_lecturerId;
END //

-- 6. Akademisyenin Danışmanlığını Yaptığı Öğrencileri Getiren Prosedür
CREATE PROCEDURE GetSupervisees(IN p_lecturerId INT)
BEGIN
    SELECT 
        s.id, 
        s.name, 
        s.surname, 
        s.email, 
        s.gpa,
        d.departmentName
    FROM Student s
    INNER JOIN Department d ON s.departmentId = d.deptId
    WHERE s.supervisorId = p_lecturerId;
END //

DELIMITER ;