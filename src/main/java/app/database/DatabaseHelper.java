package app.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import app.model.Course;
import app.model.Lecturer;
import app.model.Student;

public class DatabaseHelper {

    String url = "jdbc:mysql://localhost:3306/StudentSystemDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String user = "root";
    String password = "123456"; // write here your own password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace(); // reasearch the reason warning
        }
    }
    // to connect the database
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Student getStudentInfo(int student_id){
        Student s = null;
        String query = "SELECT s.id, s.name, s.surname, s.email, s.gpa, d.departmentName, CONCAT(l.name, ' ', l.surname) AS SupervisorName, s.dateOfBirth, s.gender, s.phone, s.address, s.departmentId, s.supervisorId FROM Student s INNER JOIN Department d ON s.departmentId = d.deptId LEFT JOIN Lecturer l ON s.supervisorId = l.id WHERE s.id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return null;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("gpa"),
                        rs.getInt("departmentId"),
                        rs.getInt("supervisorId"),
                        rs.getString("departmentName"),
                        rs.getString("SupervisorName")
                );
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return s;
    }

    public List<Course> studentCourses(int student_id){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.courseId, c.courseName, c.courseCode, c.credits, c.departmentId, d.departmentName, e.grade, e.enrollmentDate FROM Enrollment e INNER JOIN Course c ON e.courseId = c.courseId INNER JOIN Department d ON c.departmentId = d.deptId WHERE e.studentId = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("courseCode"),
                        rs.getInt("credits"),
                        rs.getInt("departmentId"),
                        rs.getString("departmentName")
                );
                courses.add(c);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public Lecturer getSupervisorInfo(int student_id){
        Lecturer l = null;
        String query = "SELECT l.id AS SupervisorID, l.name, l.surname, l.email, l.phone, d.departmentName, l.departmentId FROM Student s INNER JOIN Lecturer l ON s.supervisorId = l.id INNER JOIN Department d ON l.departmentId = d.deptId WHERE s.id = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                l = new Lecturer(
                        rs.getInt("SupervisorID"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("departmentId"),
                        rs.getString("departmentName")
                );
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return l;
    }

    public Lecturer getLecturerInfo(int lecturer_id){
        Lecturer l = null;
        String query = "SELECT l.id, l.name, l.surname, l.email, l.phone, d.departmentName, l.departmentId FROM Lecturer l INNER JOIN Department d ON l.departmentId = d.deptId WHERE l.id = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, lecturer_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                l = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("departmentId"),
                        rs.getString("departmentName")
                );
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return l;
    }

    public List<Course> lecturerCourses(int lecturer_id){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.courseId, c.courseName, c.courseCode, c.credits, c.departmentId, d.departmentName FROM LecturerCourse lc INNER JOIN Course c ON lc.courseId = c.courseId INNER JOIN Department d ON c.departmentId = d.deptId WHERE lc.lecturerId = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, lecturer_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("courseCode"),
                        rs.getInt("credits"),
                        rs.getInt("departmentId"),
                        rs.getString("departmentName")
                );
                courses.add(c);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Student> getSupervisees(int lecturer_id){
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.surname, s.email, s.gpa, d.departmentName, s.dateOfBirth, s.gender, s.phone, s.address, s.departmentId, s.supervisorId, CONCAT(l.name, ' ', l.surname) AS SupervisorName FROM Student s INNER JOIN Department d ON s.departmentId = d.deptId LEFT JOIN Lecturer l ON s.supervisorId = l.id WHERE s.supervisorId = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, lecturer_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("gpa"),
                        rs.getInt("departmentId"),
                        rs.getInt("supervisorId"),
                        rs.getString("departmentName"),
                        rs.getString("SupervisorName")
                );
                students.add(s);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    public boolean deleteStudent(int student_id){
        String query = "DELETE FROM Student WHERE id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("ERROR: Database connection is null!");
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            
            if (result == 0) {
                System.out.println("WARNING: No student found with ID: " + student_id);
            }
            
            return result > 0;

        } catch (SQLException e) {
            System.err.println("SQL ERROR deleting student: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }

    public boolean enroll(int student_id, int course_id){
        String query = "INSERT INTO Enrollment (studentId, courseId) VALUES (?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);
            preparedStatement.setInt(2, course_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteLecturer(int lecturer_id){
        String query = "DELETE FROM Lecturer WHERE id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, lecturer_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Course getCourseInfo(int course_id){
        Course c = null;
        String query = "SELECT c.courseId, c.courseName, c.courseCode, c.credits, c.departmentId, d.departmentName FROM Course c INNER JOIN Department d ON c.departmentId = d.deptId WHERE c.courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return null;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, course_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                c = new Course(
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("courseCode"),
                        rs.getInt("credits"),
                        rs.getInt("departmentId"),
                        rs.getString("departmentName")
                );
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public List<Student> courseStudents(int course_id){
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.surname, s.email, s.gpa, d.departmentName, CONCAT(l.name, ' ', l.surname) AS SupervisorName, s.dateOfBirth, s.gender, s.phone, s.address, s.departmentId, s.supervisorId FROM Student s INNER JOIN Enrollment e ON s.id = e.studentId INNER JOIN Department d ON s.departmentId = d.deptId LEFT JOIN Lecturer l ON s.supervisorId = l.id WHERE e.courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return students;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, course_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("gpa"),
                        rs.getInt("departmentId"),
                        rs.getInt("supervisorId"),
                        rs.getString("departmentName"),
                        rs.getString("SupervisorName")
                );
                students.add(s);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    public boolean deleteCourse(int course_id){
        String query = "DELETE FROM Course WHERE courseId = ? AND NOT EXISTS (SELECT 1 FROM Enrollment WHERE courseId = ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, course_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*
    public boolean addStudent(String name, String surname, java.util.Date dateOfBirth, String gender, String email, String phone, String address, double gpa, int departmentId, int supervisorId){
        String query = "INSERT INTO Student (name, surname, dateOfBirth, gender, email, phone, address, gpa, departmentId, supervisorId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setDate(3, new java.sql.Date(dateOfBirth.getTime()));
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, address);
            preparedStatement.setDouble(8, gpa);
            preparedStatement.setInt(9, departmentId);
            preparedStatement.setInt(10, supervisorId);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public boolean updateStudent(int id, String name, String surname, java.util.Date dateOfBirth, String gender, String email, String phone, String address, double gpa, int departmentId, int supervisorId){
        String query = "UPDATE Student SET name = ?, surname = ?, dateOfBirth = ?, gender = ?, email = ?, phone = ?, address = ?, gpa = ?, departmentId = ?, supervisorId = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setDate(3, new java.sql.Date(dateOfBirth.getTime()));
            preparedStatement.setString(4, gender);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, address);
            preparedStatement.setDouble(8, gpa);
            preparedStatement.setInt(9, departmentId);
            preparedStatement.setInt(10, supervisorId);
            preparedStatement.setInt(11, id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public List<Student> studentFilter(String name, String surname, int st_id, String dept, double gpa_min, double gpa_max, String sortBy){
        List<Student> students = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT s.* FROM Student s INNER JOIN Department d ON s.departmentId = d.deptId WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query.append(" AND s.name LIKE ?");
            params.add("%" + name + "%");
        }
        if (surname != null && !surname.isEmpty()) {
            query.append(" AND s.surname LIKE ?");
            params.add("%" + surname + "%");
        }
        if (st_id > 0) {
            query.append(" AND s.id = ?");
            params.add(st_id);
        }
        if (dept != null && !dept.isEmpty()) {
            query.append(" AND d.departmentName LIKE ?");
            params.add("%" + dept + "%");
        }
        if (gpa_min >= 0) {
            query.append(" AND s.gpa >= ?");
            params.add(gpa_min);
        }
        if (gpa_max > 0) {
            query.append(" AND s.gpa <= ?");
            params.add(gpa_max);
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy);
        }

        try {
            Connection conn = getConnection();
            if (conn == null) {
                return students;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("dateOfBirth"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("gpa"),
                        rs.getInt("departmentId"),
                        rs.getInt("supervisorId")
                );
                students.add(s);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }
    */

    /*
    public boolean updateEnrollment(int student_id, int course_id, double grade){
        String query = "UPDATE Enrollment SET grade = ? WHERE studentId = ? AND courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDouble(1, grade);
            preparedStatement.setInt(2, student_id);
            preparedStatement.setInt(3, course_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public boolean disenroll(int student_id, int course_id){
        String query = "DELETE FROM Enrollment WHERE studentId = ? AND courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, student_id);
            preparedStatement.setInt(2, course_id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public boolean addLecturer(String name, String surname, String email, String phone, int departmentId){
        String query = "INSERT INTO Lecturer (name, surname, email, phone, departmentId) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, departmentId);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public boolean updateLecturer(int id, String name, String surname, String email, String phone, int departmentId){
        String query = "UPDATE Lecturer SET name = ?, surname = ?, email = ?, phone = ?, departmentId = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, departmentId);
            preparedStatement.setInt(6, id);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public List<Lecturer> lecturerFilter(String name, String surname, int lecturer_id, String dept, String sortBy){
        List<Lecturer> lecturers = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT l.* FROM Lecturer l INNER JOIN Department d ON l.departmentId = d.deptId WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query.append(" AND l.name LIKE ?");
            params.add("%" + name + "%");
        }
        if (surname != null && !surname.isEmpty()) {
            query.append(" AND l.surname LIKE ?");
            params.add("%" + surname + "%");
        }
        if (lecturer_id > 0) {
            query.append(" AND l.id = ?");
            params.add(lecturer_id);
        }
        if (dept != null && !dept.isEmpty()) {
            query.append(" AND d.departmentName LIKE ?");
            params.add("%" + dept + "%");
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy);
        }

        try {
            Connection conn = getConnection();
            if (conn == null) {
                return lecturers;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Lecturer l = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("departmentId")
                );
                lecturers.add(l);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lecturers;
    }
    */

    /*
    public List<Lecturer> getCourseLecturers(int course_id){
        List<Lecturer> lecturers = new ArrayList<>();
        String query = "SELECT l.* FROM Lecturer l INNER JOIN LecturerCourse lc ON l.id = lc.lecturerId WHERE lc.courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return lecturers;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, course_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Lecturer l = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("departmentId")
                );
                lecturers.add(l);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lecturers;
    }
    */

    /*
    public boolean addCourse(String courseName, String courseCode, int credits, int departmentId){
        String query = "INSERT INTO Course (courseName, courseCode, credits, departmentId) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseCode);
            preparedStatement.setInt(3, credits);
            preparedStatement.setInt(4, departmentId);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public boolean updateCourse(int courseId, String courseName, String courseCode, int credits, int departmentId){
        String query = "UPDATE Course SET courseName = ?, courseCode = ?, credits = ?, departmentId = ? WHERE courseId = ?";
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return false;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, courseCode);
            preparedStatement.setInt(3, credits);
            preparedStatement.setInt(4, departmentId);
            preparedStatement.setInt(5, courseId);

            int result = preparedStatement.executeUpdate();
            conn.close();
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    */

    /*
    public List<Course> courseFilter(String name, int course_id, String dept, String sortBy){
        List<Course> courses = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT c.* FROM Course c INNER JOIN Department d ON c.departmentId = d.deptId WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query.append(" AND c.courseName LIKE ?");
            params.add("%" + name + "%");
        }
        if (course_id > 0) {
            query.append(" AND c.courseId = ?");
            params.add(course_id);
        }
        if (dept != null && !dept.isEmpty()) {
            query.append(" AND d.departmentName LIKE ?");
            params.add("%" + dept + "%");
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy);
        }

        try {
            Connection conn = getConnection();
            if (conn == null) {
                return courses;
            }

            PreparedStatement preparedStatement = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("courseCode"),
                        rs.getInt("credits"),
                        rs.getInt("departmentId")
                );
                courses.add(c);
            }
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }
    */

}
