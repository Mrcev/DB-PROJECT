package app.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import app.model.Course;
import app.model.Enrollment;
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
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return null;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetStudentInfo(?)}");
            callableStatement.setInt(1, student_id);

            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
                s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getDouble("gpa"),
                        rs.getString("departmentName"),
                        rs.getString("SupervisorName")
                );

                String query = "SELECT dateOfBirth, gender, phone, address, departmentId, supervisorId FROM Student WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, student_id);
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    s.setDateOfBirth(rs2.getDate("dateOfBirth"));
                    s.setGender(rs2.getString("gender"));
                    s.setPhone(rs2.getString("phone"));
                    s.setAddress(rs2.getString("address"));
                    s.setDepartmentId(rs2.getInt("departmentId"));
                    s.setSupervisorId(rs2.getInt("supervisorId"));
                }
                ps.close();
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in getStudentInfo: " + e.getMessage());
            e.printStackTrace();
        }
        return s;
    }

    public List<Enrollment> studentCourses(int student_id){
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return enrollments;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetStudentCourses(?)}");
            callableStatement.setInt(1, student_id);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                double grade = rs.getObject("grade") != null ? rs.getDouble("grade") : 0.0;
                Timestamp enrollmentDate = rs.getTimestamp("enrollmentDate");

                String query = "SELECT e.enrollmentId, c.courseId, c.departmentId, d.departmentName FROM Enrollment e INNER JOIN Course c ON e.courseId = c.courseId INNER JOIN Department d ON c.departmentId = d.deptId WHERE e.studentId = ? AND c.courseCode = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, student_id);
                ps.setString(2, rs.getString("courseCode"));
                ResultSet rs2 = ps.executeQuery();
                
                int enrollmentId = 0;
                int courseId = 0;
                int departmentId = 0;
                String departmentName = null;
                
                if (rs2.next()) {
                    enrollmentId = rs2.getInt("enrollmentId");
                    courseId = rs2.getInt("courseId");
                    departmentId = rs2.getInt("departmentId");
                    departmentName = rs2.getString("departmentName");
                }
                ps.close();

                Enrollment enrollment = new Enrollment(
                        enrollmentId,
                        student_id,
                        courseId,
                        grade,
                        enrollmentDate,
                        rs.getString("courseCode"),
                        rs.getString("courseName"),
                        rs.getInt("credits"),
                        departmentId,
                        departmentName
                );
                enrollments.add(enrollment);
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in studentCourses: " + e.getMessage());
            e.printStackTrace();
        }
        return enrollments;
    }

    public Lecturer getSupervisorInfo(int student_id){
        Lecturer l = null;
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return null;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetSupervisorInfo(?)}");
            callableStatement.setInt(1, student_id);

            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
                l = new Lecturer(
                        rs.getInt("SupervisorID"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        0,
                        rs.getString("departmentName")
                );

                String query = "SELECT departmentId FROM Lecturer WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, rs.getInt("SupervisorID"));
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    l.setDepartmentId(rs2.getInt("departmentId"));
                }
                ps.close();
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in getSupervisorInfo: " + e.getMessage());
            e.printStackTrace();
        }
        return l;
    }

    public Lecturer getLecturerInfo(int lecturer_id){
        Lecturer l = null;
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return null;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetLecturerInfo(?)}");
            callableStatement.setInt(1, lecturer_id);

            ResultSet rs = callableStatement.executeQuery();

            if (rs.next()) {
                l = new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        0,
                        rs.getString("departmentName")
                );

                String query = "SELECT departmentId FROM Lecturer WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, lecturer_id);
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    l.setDepartmentId(rs2.getInt("departmentId"));
                }
                ps.close();
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in getLecturerInfo: " + e.getMessage());
            e.printStackTrace();
        }
        return l;
    }

    public List<Course> lecturerCourses(int lecturer_id){
        List<Course> courses = new ArrayList<>();
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return courses;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetLecturerCourses(?)}");
            callableStatement.setInt(1, lecturer_id);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getString("courseCode"),
                        rs.getString("courseName"),
                        rs.getInt("credits")
                );

                String query = "SELECT c.courseId, c.departmentId, d.departmentName FROM Course c INNER JOIN Department d ON c.departmentId = d.deptId WHERE c.courseCode = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, rs.getString("courseCode"));
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    c.setCourseId(rs2.getInt("courseId"));
                    c.setDepartmentId(rs2.getInt("departmentId"));
                    c.setDepartmentName(rs2.getString("departmentName"));
                }
                ps.close();
                courses.add(c);
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in lecturerCourses: " + e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }

    public List<Student> getSupervisees(int lecturer_id){
        List<Student> students = new ArrayList<>();
        try {
            Connection conn = getConnection();
            if (conn == null) {
                return students;
            }

            CallableStatement callableStatement = conn.prepareCall("{call GetSupervisees(?)}");
            callableStatement.setInt(1, lecturer_id);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getDouble("gpa"),
                        rs.getString("departmentName"),
                        null
                );

                String query = "SELECT dateOfBirth, gender, phone, address, departmentId, supervisorId FROM Student WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, rs.getInt("id"));
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    s.setDateOfBirth(rs2.getDate("dateOfBirth"));
                    s.setGender(rs2.getString("gender"));
                    s.setPhone(rs2.getString("phone"));
                    s.setAddress(rs2.getString("address"));
                    s.setDepartmentId(rs2.getInt("departmentId"));
                    s.setSupervisorId(rs2.getInt("supervisorId"));
                    if (rs2.getInt("supervisorId") > 0) {
                        String supQuery = "SELECT CONCAT(name, ' ', surname) AS SupervisorName FROM Lecturer WHERE id = ?";
                        PreparedStatement ps2 = conn.prepareStatement(supQuery);
                        ps2.setInt(1, rs2.getInt("supervisorId"));
                        ResultSet rs3 = ps2.executeQuery();
                        if (rs3.next()) {
                            s.setSupervisorName(rs3.getString("SupervisorName"));
                        }
                        ps2.close();
                    }
                }
                ps.close();
                students.add(s);
            }
            callableStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Error in getSupervisees: " + e.getMessage());
            e.printStackTrace();
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
            System.err.println("Error in updateLecturer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

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
