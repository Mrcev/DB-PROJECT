package app.controller;

import app.database.DatabaseHelper;
import app.model.Course;
import app.model.Enrollment;
import app.model.Lecturer;
import app.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class mainController {

    // --- Inject FXML elements ---
    @FXML private TabPane mainTabPane;
    @FXML private Tab studentTab;
    @FXML private Tab lecturerTab;

    @FXML private TextField infoEnterStudent;
    @FXML private TextField infoEnterLecturer;

    @FXML private TextFlow studentOutputFlow;
    @FXML private TextFlow lecturerOutputFlow;

    // --- State Variables ---
    private String currentStudentAction = "";
    private String currentLecturerAction = "";
    private DatabaseHelper dbHelper;

    public mainController() {
        dbHelper = new DatabaseHelper();
    }

    // --- Student Group Actions ---
    @FXML
    public void GetStudentInfo(ActionEvent event) {
        //  Switch Tab to Student
        mainTabPane.getSelectionModel().select(studentTab);
        //  Set Context (Remember user wants "INFO")
        currentStudentAction = "INFO";
        infoEnterStudent.setPromptText("Enter ID to view Info");
        //  Clear previous output and focus input
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void GetSupervisor(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "SUPERVISOR";
        infoEnterStudent.setPromptText("Enter ID to find Supervisor");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void GetCourses(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "COURSES";
        infoEnterStudent.setPromptText("Enter ID to view Courses");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void DeleteStudent(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "DELETE";
        infoEnterStudent.setPromptText("Enter ID to DELETE");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void EnrollStudent(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "ENROLL";
        infoEnterStudent.setPromptText("Enter: StudentID, CourseID");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void AddStudent(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "ADD";
        infoEnterStudent.setPromptText("Enter: name, surname, dateOfBirth(YYYY-MM-DD), gender, email, phone, address, gpa, departmentId, supervisorId");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void UpdateStudent(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "UPDATE";
        infoEnterStudent.setPromptText("Enter: id, name, surname, dateOfBirth(YYYY-MM-DD), gender, email, phone, address, gpa, departmentId, supervisorId");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void handleStudentEnter(ActionEvent event) {
        //  Get Input
        String studentID = infoEnterStudent.getText();
        studentOutputFlow.getChildren().clear();

        // Handle ENROLL case separately (requires two IDs)
        if (currentStudentAction.equals("ENROLL")) {
            String[] parts = studentID.split(",");
            if (parts.length == 2) {
                try {
                    int sId = Integer.parseInt(parts[0].trim());
                    int cId = Integer.parseInt(parts[1].trim());
                    printToStudentConsole("Enrolling Student...\n", Color.BLACK, true);
                    boolean enrolled = dbHelper.enroll(sId, cId);
                    if (enrolled) {
                        printToStudentConsole("Enrolled successfully.", Color.GREEN, false);
                    } else {
                        printToStudentConsole("Enrollment failed. Check if student and course exist.", Color.RED, false);
                    }
                } catch (NumberFormatException e) {
                    printToStudentConsole("Invalid format. Use: StudentID, CourseID", Color.RED, true);
                }
            } else {
                printToStudentConsole("Invalid format. Use: StudentID, CourseID", Color.RED, true);
            }
            return;
        }

        // Handle ADD and UPDATE operations (comma-separated input)
        if (currentStudentAction.equals("ADD") || currentStudentAction.equals("UPDATE")) {
            String[] parts = studentID.split(",");
            try {
                if (currentStudentAction.equals("ADD")) {
                    if (parts.length == 10) {
                        String name = parts[0].trim();
                        String surname = parts[1].trim();
                        java.sql.Date dateOfBirth = java.sql.Date.valueOf(parts[2].trim());
                        String gender = parts[3].trim();
                        String email = parts[4].trim();
                        String phone = parts[5].trim();
                        String address = parts[6].trim();
                        double gpa = Double.parseDouble(parts[7].trim());
                        int departmentId = Integer.parseInt(parts[8].trim());
                        int supervisorId = Integer.parseInt(parts[9].trim());
                        
                        printToStudentConsole("Adding Student...\n", Color.BLACK, true);
                        boolean added = dbHelper.addStudent(name, surname, dateOfBirth, gender, email, phone, address, gpa, departmentId, supervisorId);
                        if (added) {
                            printToStudentConsole("Student added successfully.", Color.GREEN, false);
                        } else {
                            printToStudentConsole("Failed to add student. Check if department and supervisor exist.", Color.RED, false);
                        }
                    } else {
                        printToStudentConsole("Invalid format. Expected 10 comma-separated values.", Color.RED, true);
                    }
                } else if (currentStudentAction.equals("UPDATE")) {
                    if (parts.length == 11) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String surname = parts[2].trim();
                        java.sql.Date dateOfBirth = java.sql.Date.valueOf(parts[3].trim());
                        String gender = parts[4].trim();
                        String email = parts[5].trim();
                        String phone = parts[6].trim();
                        String address = parts[7].trim();
                        double gpa = Double.parseDouble(parts[8].trim());
                        int departmentId = Integer.parseInt(parts[9].trim());
                        int supervisorId = Integer.parseInt(parts[10].trim());
                        
                        printToStudentConsole("Updating Student...\n", Color.BLACK, true);
                        boolean updated = dbHelper.updateStudent(id, name, surname, dateOfBirth, gender, email, phone, address, gpa, departmentId, supervisorId);
                        if (updated) {
                            printToStudentConsole("Student updated successfully.", Color.GREEN, false);
                        } else {
                            printToStudentConsole("Failed to update student. Check if student exists.", Color.RED, false);
                        }
                    } else {
                        printToStudentConsole("Invalid format. Expected 11 comma-separated values.", Color.RED, true);
                    }
                }
            } catch (Exception e) {
                printToStudentConsole("Error: " + e.getMessage() + "\nCheck your input format.", Color.RED, true);
            }
            return;
        }

        // Handle Course operations (they use course ID, not student ID)
        if (currentStudentAction.equals("COURSE_INFO") || currentStudentAction.equals("COURSE_STUDENTS") || currentStudentAction.equals("DELETE_COURSE") || currentStudentAction.equals("ADD_COURSE")) {
            if (currentStudentAction.equals("ADD_COURSE")) {
                String[] parts = studentID.split(",");
                if (parts.length == 4) {
                    try {
                        String courseName = parts[0].trim();
                        String courseCode = parts[1].trim();
                        int credits = Integer.parseInt(parts[2].trim());
                        int departmentId = Integer.parseInt(parts[3].trim());
                        
                        printToStudentConsole("Adding Course...\n", Color.BLACK, true);
                        boolean added = dbHelper.addCourse(courseName, courseCode, credits, departmentId);
                        if (added) {
                            printToStudentConsole("Course added successfully.", Color.GREEN, false);
                        } else {
                            printToStudentConsole("Failed to add course. Check if department exists.", Color.RED, false);
                        }
                    } catch (Exception e) {
                        printToStudentConsole("Error: " + e.getMessage() + "\nCheck your input format.", Color.RED, true);
                    }
                } else {
                    printToStudentConsole("Invalid format. Expected: courseName, courseCode, credits, departmentId", Color.RED, true);
                }
                return;
            }
            
            try {
                int courseId = Integer.parseInt(studentID.trim());
                switch (currentStudentAction) {
                    case "COURSE_INFO":
                        printToStudentConsole("Course Information:\n", Color.BLACK, true);
                        Course course = dbHelper.getCourseInfo(courseId);
                        if (course != null) {
                            StringBuilder info = new StringBuilder();
                            info.append("Course ID: ").append(course.getCourseId()).append("\n");
                            info.append("Course Name: ").append(course.getCourseName()).append("\n");
                            info.append("Course Code: ").append(course.getCourseCode()).append("\n");
                            info.append("Credits: ").append(course.getCredits()).append("\n");
                            info.append("Department ID: ").append(course.getDepartmentId()).append("\n");
                            info.append("Department: ").append(course.getDepartmentName() != null ? course.getDepartmentName() : "N/A").append("\n");
                            printToStudentConsole(info.toString(), Color.DARKBLUE, false);
                        } else {
                            printToStudentConsole("Course not found.", Color.RED, false);
                        }
                        break;
                    case "COURSE_STUDENTS":
                        printToStudentConsole("Enrolled Students:\n", Color.BLACK, true);
                        List<Student> enrolledStudents = dbHelper.courseStudents(courseId);
                        if (!enrolledStudents.isEmpty()) {
                            StringBuilder studentList = new StringBuilder();
                            for (Student s : enrolledStudents) {
                                studentList.append("ID: ").append(s.getId()).append("\n");
                                studentList.append("Name: ").append(s.getName()).append(" ").append(s.getSurname()).append("\n");
                                studentList.append("Email: ").append(s.getEmail()).append("\n");
                                studentList.append("Phone: ").append(s.getPhone() != null ? s.getPhone() : "N/A").append("\n");
                                studentList.append("GPA: ").append(String.format("%.2f", s.getGpa())).append("\n");
                                studentList.append("Department ID: ").append(s.getDepartmentId()).append("\n");
                                studentList.append("Department: ").append(s.getDepartmentName() != null ? s.getDepartmentName() : "N/A").append("\n");
                                studentList.append("Supervisor ID: ").append(s.getSupervisorId()).append("\n");
                                studentList.append("Supervisor: ").append(s.getSupervisorName() != null ? s.getSupervisorName() : "N/A").append("\n");
                                studentList.append("---\n");
                            }
                            printToStudentConsole(studentList.toString(), Color.DARKGREEN, false);
                        } else {
                            printToStudentConsole("No students enrolled.", Color.RED, false);
                        }
                        break;
                    case "DELETE_COURSE":
                        printToStudentConsole("Deleting Course...\n", Color.BLACK, true);
                        boolean courseDeleted = dbHelper.deleteCourse(courseId);
                        if (courseDeleted) {
                            printToStudentConsole("Course deleted successfully.", Color.GREEN, false);
                        } else {
                            printToStudentConsole("Delete failed. Course may not exist or has enrollments.", Color.RED, false);
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                printToStudentConsole("Invalid Course ID format.", Color.RED, true);
            }
            return;
        }

        //  Background Validation for Student operations
        if (!validateStudentID(studentID)) {
            printToStudentConsole("Invalid Student ID.\nID must start with 'S' followed by numbers.\nExample: S12345", Color.RED, true);
            return; }

        //  Perform Action based on which button was clicked earlier
        printToStudentConsole("ID Validated: " + studentID + "\n", Color.GREEN, false);

        int studentId;
        try {
            if (studentID.toUpperCase().startsWith("S")) {
                studentId = Integer.parseInt(studentID.substring(1));
            } else {
                studentId = Integer.parseInt(studentID);
            }
        } catch (NumberFormatException e) {
            printToStudentConsole("Invalid Student ID format.", Color.RED, true);
            return;
        }

        switch (currentStudentAction) {
            case "INFO":
                printToStudentConsole("Displaying Student Information...\n", Color.BLACK, true);
                Student student = dbHelper.getStudentInfo(studentId);
                if (student != null) {
                    StringBuilder info = new StringBuilder();
                    info.append("ID: ").append(student.getId()).append("\n");
                    info.append("Name: ").append(student.getName()).append(" ").append(student.getSurname()).append("\n");
                    info.append("Email: ").append(student.getEmail()).append("\n");
                    info.append("Phone: ").append(student.getPhone() != null ? student.getPhone() : "N/A").append("\n");
                    info.append("Address: ").append(student.getAddress() != null ? student.getAddress() : "N/A").append("\n");
                    info.append("Date of Birth: ").append(student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "N/A").append("\n");
                    info.append("Gender: ").append(student.getGender() != null ? student.getGender() : "N/A").append("\n");
                    info.append("GPA: ").append(String.format("%.2f", student.getGpa())).append("\n");
                    info.append("Department ID: ").append(student.getDepartmentId()).append("\n");
                    info.append("Department: ").append(student.getDepartmentName() != null ? student.getDepartmentName() : "N/A").append("\n");
                    info.append("Supervisor ID: ").append(student.getSupervisorId()).append("\n");
                    info.append("Supervisor: ").append(student.getSupervisorName() != null ? student.getSupervisorName() : "N/A").append("\n");
                    printToStudentConsole(info.toString(), Color.DARKBLUE, false);
                } else {
                    printToStudentConsole("Student not found.", Color.RED, false);
                }
                break;
            case "SUPERVISOR":
                printToStudentConsole("Fetching Supervisor...\n", Color.BLACK, true);
                Lecturer supervisor = dbHelper.getSupervisorInfo(studentId);
                if (supervisor != null) {
                    StringBuilder info = new StringBuilder();
                    info.append("Supervisor ID: ").append(supervisor.getId()).append("\n");
                    info.append("Name: ").append(supervisor.getName()).append(" ").append(supervisor.getSurname()).append("\n");
                    info.append("Email: ").append(supervisor.getEmail()).append("\n");
                    info.append("Phone: ").append(supervisor.getPhone() != null ? supervisor.getPhone() : "N/A").append("\n");
                    info.append("Department ID: ").append(supervisor.getDepartmentId()).append("\n");
                    info.append("Department: ").append(supervisor.getDepartmentName() != null ? supervisor.getDepartmentName() : "N/A").append("\n");
                    printToStudentConsole(info.toString(), Color.DARKGREEN, false);
                } else {
                    printToStudentConsole("Supervisor not found.", Color.RED, false);
                }
                break;
            case "COURSES":
                printToStudentConsole("Student Courses:\n", Color.BLACK, true);
                List<Enrollment> enrollments = dbHelper.studentCourses(studentId);
                if (!enrollments.isEmpty()) {
                    StringBuilder courseList = new StringBuilder();
                    for (Enrollment enrollment : enrollments) {
                        courseList.append("Enrollment ID: ").append(enrollment.getEnrollmentId()).append("\n");
                        courseList.append("Course ID: ").append(enrollment.getCourseId()).append("\n");
                        courseList.append("Course Name: ").append(enrollment.getCourseName()).append("\n");
                        courseList.append("Course Code: ").append(enrollment.getCourseCode()).append("\n");
                        courseList.append("Credits: ").append(enrollment.getCredits()).append("\n");
                        courseList.append("Grade: ").append(enrollment.getGrade() > 0 ? String.format("%.2f", enrollment.getGrade()) : "N/A").append("\n");
                        courseList.append("Enrollment Date: ").append(enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().toString() : "N/A").append("\n");
                        courseList.append("Department ID: ").append(enrollment.getDepartmentId()).append("\n");
                        courseList.append("Department: ").append(enrollment.getDepartmentName() != null ? enrollment.getDepartmentName() : "N/A").append("\n");
                        courseList.append("---\n");
                    }
                    printToStudentConsole(courseList.toString(), Color.DARKMAGENTA, false);
                } else {
                    printToStudentConsole("No courses found.", Color.RED, false);
                }
                break;
            case "DELETE":
                printToStudentConsole("Deleting Student...\n", Color.BLACK, true);
                boolean deleted = dbHelper.deleteStudent(studentId);
                if (deleted) {
                    printToStudentConsole("Student deleted successfully.", Color.GREEN, false);
                } else {
                    printToStudentConsole("Delete failed. Student may not exist.", Color.RED, false);
                }
                break;
            default:
                printToStudentConsole("Please select an action from the menu on the left first.", Color.GREY, false); }}

    @FXML
    public void GetLecturerInfo(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "INFO";
        infoEnterLecturer.setPromptText("Enter ID to view Profile");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void GetSupervisee(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "SUPERVISEE";
        infoEnterLecturer.setPromptText("Enter ID to see Students");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void GetLecturerCourses(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "COURSES";
        infoEnterLecturer.setPromptText("Enter ID to see Classes");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void DeleteLecturer(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "DELETE";
        infoEnterLecturer.setPromptText("Enter ID to DELETE");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void AddLecturer(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "ADD";
        infoEnterLecturer.setPromptText("Enter: name, surname, email, phone, departmentId");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void UpdateLecturer(ActionEvent event) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = "UPDATE";
        infoEnterLecturer.setPromptText("Enter: id, name, surname, email, phone, departmentId");
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus(); }

    @FXML
    public void handleLecturerEnter(ActionEvent event) {
        String lecturerID = infoEnterLecturer.getText();
        lecturerOutputFlow.getChildren().clear();

        // Handle ADD and UPDATE operations (comma-separated input)
        if (currentLecturerAction.equals("ADD") || currentLecturerAction.equals("UPDATE")) {
            String[] parts = lecturerID.split(",");
            try {
                if (currentLecturerAction.equals("ADD")) {
                    if (parts.length == 5) {
                        String name = parts[0].trim();
                        String surname = parts[1].trim();
                        String email = parts[2].trim();
                        String phone = parts[3].trim();
                        int departmentId = Integer.parseInt(parts[4].trim());
                        
                        printToLecturerConsole("Adding Lecturer...\n", Color.BLACK, true);
                        boolean added = dbHelper.addLecturer(name, surname, email, phone, departmentId);
                        if (added) {
                            printToLecturerConsole("Lecturer added successfully.", Color.GREEN, false);
                        } else {
                            printToLecturerConsole("Failed to add lecturer. Check if department exists.", Color.RED, false);
                        }
                    } else {
                        printToLecturerConsole("Invalid format. Expected 5 comma-separated values.", Color.RED, true);
                    }
                } else if (currentLecturerAction.equals("UPDATE")) {
                    if (parts.length == 6) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String surname = parts[2].trim();
                        String email = parts[3].trim();
                        String phone = parts[4].trim();
                        int departmentId = Integer.parseInt(parts[5].trim());
                        
                        printToLecturerConsole("Updating Lecturer...\n", Color.BLACK, true);
                        boolean updated = dbHelper.updateLecturer(id, name, surname, email, phone, departmentId);
                        if (updated) {
                            printToLecturerConsole("Lecturer updated successfully.", Color.GREEN, false);
                        } else {
                            printToLecturerConsole("Failed to update lecturer. Check if lecturer exists.", Color.RED, false);
                        }
                    } else {
                        printToLecturerConsole("Invalid format. Expected 6 comma-separated values.", Color.RED, true);
                    }
                }
            } catch (Exception e) {
                printToLecturerConsole("Error: " + e.getMessage() + "\nCheck your input format.", Color.RED, true);
            }
            return;
        }

        if (!validateLecturerID(lecturerID)) {
            printToLecturerConsole("Invalid Lecturer ID.\nID must start with 'L'.\nExample: L999", Color.RED, true);
            return; }
        printToLecturerConsole("ID Validated: " + lecturerID + "\n", Color.GREEN, false);
        
        int lecturerId;
        try {
            if (lecturerID.toUpperCase().startsWith("L")) {
                lecturerId = Integer.parseInt(lecturerID.substring(1));
            } else {
                lecturerId = Integer.parseInt(lecturerID);
            }
        } catch (NumberFormatException e) {
            printToLecturerConsole("Invalid Lecturer ID format.", Color.RED, true);
            return;
        }

        switch (currentLecturerAction) {
            case "INFO":
                printToLecturerConsole("Lecturer Profile:\n", Color.BLACK, true);
                Lecturer lecturer = dbHelper.getLecturerInfo(lecturerId);
                if (lecturer != null) {
                    StringBuilder info = new StringBuilder();
                    info.append("Lecturer ID: ").append(lecturer.getId()).append("\n");
                    info.append("Name: ").append(lecturer.getName()).append(" ").append(lecturer.getSurname()).append("\n");
                    info.append("Email: ").append(lecturer.getEmail()).append("\n");
                    info.append("Phone: ").append(lecturer.getPhone() != null ? lecturer.getPhone() : "N/A").append("\n");
                    info.append("Department ID: ").append(lecturer.getDepartmentId()).append("\n");
                    info.append("Department: ").append(lecturer.getDepartmentName() != null ? lecturer.getDepartmentName() : "N/A").append("\n");
                    printToLecturerConsole(info.toString(), Color.DARKBLUE, false);
                } else {
                    printToLecturerConsole("Lecturer not found.", Color.RED, false);
                }
                break;
            case "SUPERVISEE":
                printToLecturerConsole("Supervising:\n", Color.BLACK, true);
                List<Student> students = dbHelper.getSupervisees(lecturerId);
                if (!students.isEmpty()) {
                    StringBuilder studentList = new StringBuilder();
                    for (Student student : students) {
                        studentList.append("ID: ").append(student.getId()).append("\n");
                        studentList.append("Name: ").append(student.getName()).append(" ").append(student.getSurname()).append("\n");
                        studentList.append("Email: ").append(student.getEmail()).append("\n");
                        studentList.append("Phone: ").append(student.getPhone() != null ? student.getPhone() : "N/A").append("\n");
                        studentList.append("Address: ").append(student.getAddress() != null ? student.getAddress() : "N/A").append("\n");
                        studentList.append("Date of Birth: ").append(student.getDateOfBirth() != null ? student.getDateOfBirth().toString() : "N/A").append("\n");
                        studentList.append("Gender: ").append(student.getGender() != null ? student.getGender() : "N/A").append("\n");
                        studentList.append("GPA: ").append(String.format("%.2f", student.getGpa())).append("\n");
                        studentList.append("Department ID: ").append(student.getDepartmentId()).append("\n");
                        studentList.append("Department: ").append(student.getDepartmentName() != null ? student.getDepartmentName() : "N/A").append("\n");
                        studentList.append("Supervisor ID: ").append(student.getSupervisorId()).append("\n");
                        studentList.append("Supervisor: ").append(student.getSupervisorName() != null ? student.getSupervisorName() : "N/A").append("\n");
                        studentList.append("---\n");
                    }
                    printToLecturerConsole(studentList.toString(), Color.DARKGREEN, false);
                } else {
                    printToLecturerConsole("No supervisees found.", Color.RED, false);
                }
                break;
            case "COURSES":
                printToLecturerConsole("Teaching:\n", Color.BLACK, true);
                List<Course> courses = dbHelper.lecturerCourses(lecturerId);
                if (!courses.isEmpty()) {
                    StringBuilder courseList = new StringBuilder();
                    for (Course course : courses) {
                        courseList.append("Course ID: ").append(course.getCourseId()).append("\n");
                        courseList.append("Course Name: ").append(course.getCourseName()).append("\n");
                        courseList.append("Course Code: ").append(course.getCourseCode()).append("\n");
                        courseList.append("Credits: ").append(course.getCredits()).append("\n");
                        courseList.append("Department ID: ").append(course.getDepartmentId()).append("\n");
                        courseList.append("Department: ").append(course.getDepartmentName() != null ? course.getDepartmentName() : "N/A").append("\n");
                        courseList.append("---\n");
                    }
                    printToLecturerConsole(courseList.toString(), Color.DARKMAGENTA, false);
                } else {
                    printToLecturerConsole("No courses found.", Color.RED, false);
                }
                break;
            case "DELETE":
                printToLecturerConsole("Deleting Lecturer...\n", Color.BLACK, true);
                boolean deleted = dbHelper.deleteLecturer(lecturerId);
                if (deleted) {
                    printToLecturerConsole("Lecturer deleted successfully.", Color.GREEN, false);
                } else {
                    printToLecturerConsole("Delete failed. Lecturer may not exist.", Color.RED, false);
                }
                break;
            default:
                printToLecturerConsole("Please select an action from the menu on the left first.", Color.GREY, false); }}

    // --- Helper / Validation Methods ---

    private boolean validateStudentID(String id) {
        // Validation: Must start with 'S' and have length > 1
        return id != null && id.toUpperCase().startsWith("S") && id.length() > 1; }

    private boolean validateLecturerID(String id) {
        // Validation: Must start with 'L' and have length > 1
        return id != null && id.toUpperCase().startsWith("L") && id.length() > 1; }

    private void printToStudentConsole(String message, Color color, boolean isBold) {
        Text textNode = new Text(message);
        textNode.setFill(color);
        textNode.setFont(Font.font("Verdana", isBold ? FontWeight.BOLD : FontWeight.NORMAL, 13));
        studentOutputFlow.getChildren().add(textNode); }

    private void printToLecturerConsole(String message, Color color, boolean isBold) {
        Text textNode = new Text(message);
        textNode.setFill(color);
        textNode.setFont(Font.font("Verdana", isBold ? FontWeight.BOLD : FontWeight.NORMAL, 13));
        lecturerOutputFlow.getChildren().add(textNode); }

    // --- Course Operations ---
    @FXML
    public void GetCourseInfo(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "COURSE_INFO";
        infoEnterStudent.setPromptText("Enter Course ID");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void GetCourseStudents(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "COURSE_STUDENTS";
        infoEnterStudent.setPromptText("Enter Course ID");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void DeleteCourse(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "DELETE_COURSE";
        infoEnterStudent.setPromptText("Enter Course ID to DELETE");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }

    @FXML
    public void AddCourse(ActionEvent event) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = "ADD_COURSE";
        infoEnterStudent.setPromptText("Enter: courseName, courseCode, credits, departmentId");
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus(); }
}


