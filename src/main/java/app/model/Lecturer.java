package app.model;
public class Lecturer {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private int departmentId;
    private String departmentName;

    public Lecturer(int id, String name, String surname, String email, String phone, int departmentId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
    }

    public Lecturer(int id, String name, String surname, String email, String phone, int departmentId, String departmentName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
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

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
