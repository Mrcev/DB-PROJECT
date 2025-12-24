package app.model;

public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private int credits;
    private int departmentId;

    public Course(int courseId, String courseName, String courseCode, int credits, int departmentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.departmentId = departmentId;
    }

    public int getCourseId() {
        return courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public int getCredits() {
        return credits;
    }
    public int getDepartmentId() {
        return departmentId;
    }
}
