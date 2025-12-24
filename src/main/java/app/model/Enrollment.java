package app.model;
import java.sql.Timestamp;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private double grade;
    private Timestamp enrollmentDate;

    public Enrollment(int enrollmentId, int studentId, int courseId, double grade, Timestamp enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
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

}
