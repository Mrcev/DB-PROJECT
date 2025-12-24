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
    String password = ""; // write your own password

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
                System.err.println("Database connection failed!");
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
                        rs.getInt("supervisorId")
                );
            }
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error getting student info: " + e.getMessage());
            e.printStackTrace();
        }
        return s;
    }

    public List<Course> studentCourses(int student_id){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.courseCode, c.courseName, c.credits, e.grade, e.enrollmentDate, c.courseId, c.departmentId FROM Enrollment e INNER JOIN Course c ON e.courseId = c.courseId WHERE e.studentId = ?";
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
                        rs.getInt("departmentId")
                );
                courses.add(c);
            }

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
                        rs.getInt("departmentId")
                );
            }

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
                        rs.getInt("departmentId")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return l;
    }

    public List<Course> lecturerCourses(int lecturer_id){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.courseCode, c.courseName, c.credits, c.courseId, c.departmentId FROM LecturerCourse lc INNER JOIN Course c ON lc.courseId = c.courseId WHERE lc.lecturerId = ?";
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
                        rs.getInt("departmentId")
                );
                courses.add(c);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public List<Student> getSupervisees(int lecturer_id){
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.surname, s.email, s.gpa, d.departmentName, s.dateOfBirth, s.gender, s.phone, s.address, s.departmentId, s.supervisorId FROM Student s INNER JOIN Department d ON s.departmentId = d.deptId WHERE s.supervisorId = ?";
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
                        rs.getInt("supervisorId")
                );
                students.add(s);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

}
