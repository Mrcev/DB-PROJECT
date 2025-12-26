package app.model;

public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private int credits;
    private int departmentId;
    private String departmentName;

    public Course(int courseId, String courseName, String courseCode, int credits, int departmentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.departmentId = departmentId;
    }

    public Course(int courseId, String courseName, String courseCode, int credits, int departmentId, String departmentName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
