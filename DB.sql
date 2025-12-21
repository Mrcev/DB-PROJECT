-- Veritabanı oluştur
CREATE DATABASE StudentSystemDB;
GO

-- Veritabanına bağlan
USE StudentSystemDB;
GO

---------------------------------------------------------------------------------------


-- BÖLÜM TABLOSU
CREATE TABLE Department (
    deptId INT IDENTITY(1,1) PRIMARY KEY, -- Benzersiz ID  1'den başlar otomatik artar 
    departmentName VARCHAR(50) NOT NULL,  -- Bölüm adı 
    hod VARCHAR(30),                      -- Bölüm Başkanı  
    nStudent INT DEFAULT 0,               -- Toplam öğrenci sayısı (Varsayılan 0) 
    nInstructor INT DEFAULT 0             -- Toplam hoca sayısı (Varsayılan 0) 
);
GO


---------------------------------------------------------------------------------------


-- HOCA TABLOSU
CREATE TABLE Lecturer (
    id INT IDENTITY(1,1) PRIMARY KEY,     -- Hocanın idsi 
    name CHAR(30) NOT NULL,            -- Hoca adı 
    surname CHAR(20) NOT NULL,         -- Hoca soyadı 
    email VARCHAR(50),                    -- İletişim e-postası 
    phone VARCHAR(10),                    -- Telefon numarası 
    departmentId INT,                     -- RELATION Hocanın olduğu bölüm 

    -- Bir bölümün birçok hocası olabilir (1-to-Many) 
    FOREIGN KEY (departmentId) REFERENCES Department(deptId)
);
GO


---------------------------------------------------------------------------------------


-- ÖĞRENCİ TABLOSU
CREATE TABLE Student (
    id INT IDENTITY(1,1) PRIMARY KEY,     -- Öğrenci No Otomatik artan 
    name CHAR(30) NOT NULL,            -- Adı 
    surname CHAR(20) NOT NULL,         -- Soyadı 
    dateOfBirth DATE,                     -- Doğum tarihi (YYYY-MM-DD) 
    gender VARCHAR(10),                   -- Cinsiyet 
    email VARCHAR(50),                    -- E-posta 
    phone VARCHAR(10),                    -- Telefon 
    address VARCHAR(255),                 -- Adres bilgisi 
    gpa DECIMAL(3, 2),                    -- Not ortalaması (Örn: 3,50) 
    departmentId INT,                     -- RELATION Kayıtlı olduğu bölüm 
    supervisorId INT,                     -- RELATION Danışman hocası 

    --Bir bölümün birçok öğrencisi olur (Department -> Student) 
    FOREIGN KEY (departmentId) REFERENCES Department(deptId),

    --Bir hocanın birçok danışan öğrencisi olabilir (Lecturer -> Student) 
    FOREIGN KEY (supervisorId) REFERENCES Lecturer(id)
);
GO


---------------------------------------------------------------------------------------


-- DERS TABLOSU
CREATE TABLE Course (
    courseId INT IDENTITY(1,1) PRIMARY KEY, -- Dersin benzersiz IDsi 
    courseName VARCHAR(30) NOT NULL,        -- Dersin tam adı 
    courseCode VARCHAR(10) NOT NULL,        -- Dersin kodu
    credits INT,                            -- Dersin kredi değeri 
    departmentId INT,                       -- RELATION Dersi açan bölüm 

    --Bir bölüm birçok ders açabilir
    FOREIGN KEY (departmentId) REFERENCES Department(deptId)
);
GO


---------------------------------------------------------------------------------------


-- DERS KAYIT TABLOSU
CREATE TABLE Enrollment (
    enrollmentId INT IDENTITY(1,1) PRIMARY KEY, -- Kayıt işlem numarası 
    studentId INT NOT NULL,                     -- RELATION Dersi alan öğrenci 
    courseId INT NOT NULL,                      -- RELATION Alınan ders 
    grade DECIMAL(5, 2),                        -- Öğrencinin bu dersteki notu 
    enrollmentDate DATETIME DEFAULT GETDATE(),  -- Kayıt tarihi 

    --Öğrenci ve Ders arasında Many-to-Many bağlantı 
    FOREIGN KEY (studentId) REFERENCES Student(id),
    FOREIGN KEY (courseId) REFERENCES Course(courseId)
);
GO

---------------------------------------------------------------------------------------

-- KONTROL SORGULARI
SELECT * FROM Course;
SELECT * FROM Department;
SELECT * FROM Enrollment;
SELECT * FROM Lecturer;
SELECT * FROM Student;

---------------------------------------------------------------------------------------