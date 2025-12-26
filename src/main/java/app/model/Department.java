package app.model;

public class Department {
    private int deptId;
    private String departmentName;
    private String hod;
    private int nStudent;
    private int nInstructor;

    public Department(int deptId, String departmentName, String hod, int nStudent, int nInstructor) {
        this.deptId = deptId;
        this.departmentName = departmentName;
        this.hod = hod;
        this.nStudent = nStudent;
        this.nInstructor = nInstructor;
    }
    public int getDeptId() {
        return deptId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public String getHod() {
        return hod;
    }
    public int getnStudent() {
        return nStudent;
    }
    public int getnInstructor() {
        return nInstructor;
    }

}
