package app.model;
import java.sql.Timestamp;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private double grade;
    private Timestamp enrollmentDate;
    private String courseCode;
    private String courseName;
    private int credits;
    private int departmentId;
    private String departmentName;

    public Enrollment(int enrollmentId, int studentId, int courseId, double grade, Timestamp enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment(int enrollmentId, int studentId, int courseId, double grade, Timestamp enrollmentDate, String courseCode, String courseName, int credits, int departmentId, String departmentName) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }
    public double getGrade() {
        return grade;
    }
    public Timestamp getEnrollmentDate() {
        return enrollmentDate;
    }
    public int getStudentId() {
        return studentId;
    }
    public int getCourseId() {
        return courseId;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getCourseName() {
        return courseName;
    }
    public int getCredits() {
        return credits;
    }
    public int getDepartmentId() {
        return departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
