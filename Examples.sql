USE StudentSystemDB;
GO

-- 1. ADIM: BÖLÜMLER (DEPARTMENT)

INSERT INTO Department (departmentName, hod, nStudent, nInstructor)
VALUES 
('Computer Engineering', 'Prof. Dr. Ali Veli', 120, 15),    -- ID: 1
('Industrial Engineering', 'Prof. Dr. Ayşe Yılmaz', 90, 10), -- ID: 2
('Architecture', 'Prof. Dr. Mehmet Öz', 60, 8);             -- ID: 3 otomatik artan şekilde girdik  int identity (1,1):*
GO

-- 2. ADIM: HOCALAR (LECTURER)

INSERT INTO Lecturer (name, surname, email, phone, departmentId)
VALUES 
('Engin', 'Demiroğ', 'engin@uni.edu', '555-1111', 1), -- ID: 1 (Bilgisayar)
('Salih', 'Eriş', 'salih@uni.edu', '555-2222', 1),    -- ID: 2 (Bilgisayar)
('Fatma', 'Çelik', 'fatma@uni.edu', '555-3333', 2),   -- ID: 3 (Endüstri)
('Sinan', 'Mimar', 'sinan@uni.edu', '555-8888', 3);   -- ID: 4 (Mimarlık - Yeni)
GO

-- 3. ADIM: DERSLER (COURSE)

INSERT INTO Course (courseName, courseCode, credits, departmentId)
VALUES 
('Introduction to Programming', 'CS101', 4, 1), -- ID: 1
('Data Structures', 'CS202', 3, 1),             -- ID: 2
('Production Planning', 'IE301', 3, 2),         -- ID: 3
('Artificial Intelligence', 'CS404', 5, 1),     -- ID: 4 (Öğrencisi olmayan ders)
('History of Art', 'ARCH101', 3, 3);            -- ID: 5 (Mimarlık dersi)
GO

-- 4. ADIM: ÖĞRENCİLER (STUDENT)

INSERT INTO Student (name, surname, dateOfBirth, gender, email, phone, address, gpa, departmentId, supervisorId)
VALUES 
('Burak', 'Yılmaz', '2001-05-19', 'Male', 'burak@st.uni.edu', '532-1001', 'Ankara', 3.45, 1, 1),   -- ID: 1
('Zeynep', 'Kaya', '2002-08-22', 'Female', 'zeynep@st.uni.edu', '532-1002', 'Istanbul', 3.90, 1, 2), -- ID: 2
('Cem', 'Öztürk', '2001-01-15', 'Male', 'cem@st.uni.edu', '532-1003', 'Izmir', 2.80, 2, 3),        -- ID: 3
('Mehmet', 'Demir', '2003-01-01', 'Male', 'mehmet@st.uni.edu', '555-9999', 'Ankara', 0.00, 1, 1);    -- ID: 4 (Ders kaydı olmayan öğrenci)
GO

-- 5. ADIM: HOCA - DERS EŞLEŞTİRMESİ (LECTURERCOURSE)
-- Hangi hoca hangi dersi veriyor?
INSERT INTO LecturerCourse (lecturerId, courseId)
VALUES 
(1, 1), -- Engin Hoca -> CS101
(2, 2), -- Salih Hoca -> CS202
(3, 3), -- Fatma Hoca -> IE301
(4, 5); -- Sinan Hoca -> ARCH101
-- not CS404 (ai) dersine bilerek hoca atamadık. sorgu testi .
GO

-- 6. ADIM: DERS KAYITLARI (ENROLLMENT)
-- Öğrenciler dersleri seçiyor.
INSERT INTO Enrollment (studentId, courseId, grade)
VALUES 
(1, 1, 85.50), -- Burak -> CS101
(1, 2, 70.00), -- Burak -> CS202
(2, 1, 95.00), -- Zeynep -> CS101
(3, 3, 60.00), -- Cem -> IE301
(2, 2, NULL);  -- Zeynep -> CS202 (NULL Testi için bilerek)
GO
