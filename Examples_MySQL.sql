USE StudentSystemDB;

INSERT INTO Department (departmentName, hod, nStudent, nInstructor)
VALUES 
('Computer Engineering', 'Prof. Dr. Ali Veli', 120, 15),
('Industrial Engineering', 'Prof. Dr. Ayşe Yılmaz', 90, 10),
('Architecture', 'Prof. Dr. Mehmet Öz', 60, 8);

INSERT INTO Lecturer (name, surname, email, phone, departmentId)
VALUES 
('Engin', 'Demiroğ', 'engin@uni.edu', '555-1111', 1),
('Salih', 'Eriş', 'salih@uni.edu', '555-2222', 1),
('Fatma', 'Çelik', 'fatma@uni.edu', '555-3333', 2);

INSERT INTO Course (courseName, courseCode, credits, departmentId)
VALUES 
('Introduction to Programming', 'CS101', 4, 1),
('Data Structures', 'CS202', 3, 1),
('Production Planning', 'IE301', 3, 2);

INSERT INTO Student (name, surname, dateOfBirth, gender, email, phone, address, gpa, departmentId, supervisorId)
VALUES 
('Burak', 'Yılmaz', '2001-05-19', 'Male', 'burak@st.uni.edu', '532-1001', 'Ankara', 3.45, 1, 1),
('Zeynep', 'Kaya', '2002-08-22', 'Female', 'zeynep@st.uni.edu', '532-1002', 'Istanbul', 3.90, 1, 2),
('Cem', 'Öztürk', '2001-01-15', 'Male', 'cem@st.uni.edu', '532-1003', 'Izmir', 2.80, 2, 3);

INSERT INTO Enrollment (studentId, courseId, grade)
VALUES 
(1, 1, 85.50),
(1, 2, 70.00),
(2, 1, 95.00),
(3, 3, 60.00);

INSERT INTO LecturerCourse (lecturerId, courseId)
VALUES 
(1, 1),
(1, 2),
(2, 1),
(3, 3);





