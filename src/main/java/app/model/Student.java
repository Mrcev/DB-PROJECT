package app.model;
import java.util.Date;

public class Student {
    private int id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String address;
    private double gpa;
    private int departmentId;
    private int supervisorId;
    private String departmentName;
    private String supervisorName;

    public Student(int id, String name, String surname, Date dateOfBirth, String gender, String email, String phone, String address, double gpa, int departmentId, int supervisorId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gpa = gpa;
        this.departmentId = departmentId;
        this.supervisorId = supervisorId;
    }

    public Student(int id, String name, String surname, Date dateOfBirth, String gender, String email, String phone, String address, double gpa, int departmentId, int supervisorId, String departmentName, String supervisorName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gpa = gpa;
        this.departmentId = departmentId;
        this.supervisorId = supervisorId;
        this.departmentName = departmentName;
        this.supervisorName = supervisorName;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getGender() {
        return gender;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public double getGpa() {
        return gpa;
    }
    public int getDepartmentId() {
        return departmentId;
    }
    public int getSupervisorId() {
        return supervisorId;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public String getSupervisorName() {
        return supervisorName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public Student(int id, String name, String surname, String email, double gpa, String departmentName, String supervisorName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gpa = gpa;
        this.departmentName = departmentName;
        this.supervisorName = supervisorName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

}
