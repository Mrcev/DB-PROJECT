package app.controller;

import app.database.DatabaseHelper;
import app.model.Course;
import app.model.Lecturer;
import app.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;
import java.util.Optional;

public class mainController { // PascalCase name

    @FXML private TabPane mainTabPane;
    @FXML private Tab studentTab;
    @FXML private Tab lecturerTab;
    @FXML private TextField infoEnterStudent;
    @FXML private TextField infoEnterLecturer;
    @FXML private TextFlow studentOutputFlow;
    @FXML private TextFlow lecturerOutputFlow;

    private String currentStudentAction = "";
    private String currentLecturerAction = "";
    private DatabaseHelper dbHelper;

    public mainController() {
        dbHelper = new DatabaseHelper();
    }

    // --- Helper for Confirmation Dialogs  ---
    private boolean confirmAction(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // --- Student Group Actions ---
    @FXML public void GetStudentInfo(ActionEvent event) { setStudentContext("INFO", "Enter Student ID (e.g., 1 or S1)"); }
    @FXML public void GetSupervisor(ActionEvent event) { setStudentContext("SUPERVISOR", "Enter Student ID to find Supervisor"); }
    @FXML public void GetCourses(ActionEvent event) { setStudentContext("COURSES", "Enter Student ID to view Courses"); }
    @FXML public void DeleteStudent(ActionEvent event) {
        if (confirmAction("Confirm Delete", "Are you sure you want to delete this student?")) {
            setStudentContext("DELETE", "Enter Student ID to DELETE");
        }
    }
    @FXML public void EnrollStudent(ActionEvent event) {
        setStudentContext("ENROLL", "Enter: StudentID, CourseID");
    }
    @FXML public void AddStudent(ActionEvent event) {
        setStudentContext("ADD", "Enter: Name, Surname, Email, GPA, DeptID");
        printToStudentConsole("Format: John, Doe, john@example.com, 3.5, 1\n", Color.BLUE, false);
    }
    @FXML public void UpdateStudent(ActionEvent event) {
        setStudentContext("UPDATE", "Enter: ID, Name, Surname, Email, GPA, DeptID");
        printToStudentConsole("Format: 1, John, Doe, john@example.com, 3.8, 1\n", Color.BLUE, false);
    }
    private void setStudentContext(String action, String prompt) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentStudentAction = action;
        infoEnterStudent.setPromptText(prompt);
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus();
    }
    @FXML public void handleStudentEnter(ActionEvent event) {
        String input = infoEnterStudent.getText().trim();
        if (input.isEmpty()) return;
        studentOutputFlow.getChildren().clear();

        try {
            switch (currentStudentAction) {
                case "INFO":
                    Student s = dbHelper.getStudentInfo(parseId(input));
                    if (s != null) {
                        printToStudentConsole("Student Found:\n", Color.DARKBLUE, true);
                        printToStudentConsole("Name: " + s.getName() + " " + s.getSurname() + "\nGPA: " + s.getGpa() + "\nDept: " + s.getDepartmentName() + "\n", Color.BLACK, false);
                    } else printToStudentConsole("Student not found.", Color.RED, false);
                    break;

                case "DELETE":
                    if (dbHelper.deleteStudent(parseId(input))) printToStudentConsole("Success: Student deleted.", Color.GREEN, true);
                    else printToStudentConsole("Error: Could not delete student.", Color.RED, true);
                    break;

                case "ENROLL":
                    String[] parts = input.split(",");
                    if (parts.length == 2 && dbHelper.enroll(parseId(parts[0]), parseId(parts[1]))) {
                        printToStudentConsole("Enrollment successful.", Color.GREEN, true);
                    } else printToStudentConsole("Enrollment failed.", Color.RED, true);
                    break;
            }
        } catch (Exception e) {
            printToStudentConsole("Error: " + e.getMessage(), Color.RED, true);
        }
    }

    // --- Lecturer Operations ---
    @FXML public void GetLecturerInfo(ActionEvent event) { setLecturerContext("INFO", "Enter Lecturer ID"); }
    @FXML public void GetSupervisee(ActionEvent event) { setLecturerContext("SUPERVISEE", "Enter Lecturer ID to see students"); }
    @FXML public void GetLecturerCourses(ActionEvent event) { setLecturerContext("COURSES", "Enter Lecturer ID to see classes"); }
    @FXML public void UpdateLecturer(ActionEvent event) {
        setLecturerContext("UPDATE", "ID, Name, Surname, Email, Phone, DeptID");
        printToLecturerConsole("Format: L1, Jane, Doe, jane@uni.edu, 5550001, 1\n", Color.BLUE, false);
    }
    @FXML public void AddLecturer(ActionEvent event) {
        setLecturerContext("ADD", "Name, Surname, Email, Phone, DeptID");
        printToLecturerConsole("Format: Jane, Doe, jane@uni.edu, 5550001, 1\n", Color.BLUE, false);
    }
    @FXML public void DeleteLecturer(ActionEvent event) {
        if (confirmAction("Confirm Delete Lecturer", "Warning: Deleting a lecturer will set their supervisees' supervisor field to NULL.")) {
            setLecturerContext("DELETE", "Enter Lecturer ID (e.g., L1 or 1)");
        }
    }
    @FXML public void handleLecturerEnter(ActionEvent event) {
        String input = infoEnterLecturer.getText().trim();
        if (input.isEmpty()) return;
        lecturerOutputFlow.getChildren().clear();

        try {
            if (currentLecturerAction.equals("UPDATE")) {
                String[] p = input.split(",");
                if (p.length == 6) {
                    // Mapping input to Lecturer model
                    int id = parseId(p[0].trim());
                    boolean success = dbHelper.updateLecturer(id, p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim(), Integer.parseInt(p[5].trim()));
                    printToLecturerConsole(success ? "Update Successful" : "Update Failed", success ? Color.GREEN : Color.RED, true);
                } else {
                    printToLecturerConsole("Error: 6 values required.", Color.RED, true);
                }
            } else if (currentLecturerAction.equals("INFO")) {
                Lecturer l = dbHelper.getLecturerInfo(parseId(input));
                if (l != null) {
                    printToLecturerConsole("Name: " + l.getName() + " " + l.getSurname() + "\nDept: " + l.getDepartmentName(), Color.BLACK, false);
                }
            }
        } catch (Exception e) {
            printToLecturerConsole("Error: " + e.getMessage(), Color.RED, true);
        }
    }
    private void setLecturerContext(String action, String prompt) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentLecturerAction = action;
        infoEnterLecturer.setPromptText(prompt);
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus();
    }

    // --- Course Operations ---
    @FXML public void GetCourseInfo(ActionEvent event) { setStudentContext("COURSE_INFO", "Enter Course ID"); }
    @FXML public void GetCourseStudents(ActionEvent event) { setStudentContext("COURSE_STUDENTS", "Enter Course ID"); }
    @FXML public void AddCourse(ActionEvent event) { setStudentContext("ADD_COURSE", "Enter: Name, Code, Credits, DeptID"); }
    @FXML public void DeleteCourse(ActionEvent event) {
        if (confirmAction("Confirm Delete", "Are you sure?")) {
            setStudentContext("DELETE_COURSE", "Enter Course ID to DELETE");
        }
    }

    // --- Helper Methods ---
    private int parseId(String input) throws NumberFormatException {
        // Automatically handles formats like "S1", "L101", or just "1"
        String clean = input.toUpperCase().replaceAll("[SL]", "").trim();
        return Integer.parseInt(clean);
    }

    private void printToStudentConsole(String message, Color color, boolean isBold) {
        Text t = new Text(message);
        t.setFill(color);
        t.setFont(Font.font("Verdana", isBold ? FontWeight.BOLD : FontWeight.NORMAL, 13));
        studentOutputFlow.getChildren().add(t);
    }

    private void printToLecturerConsole(String message, Color color, boolean isBold) {
        Text t = new Text(message);
        t.setFill(color);
        t.setFont(Font.font("Verdana", isBold ? FontWeight.BOLD : FontWeight.NORMAL, 13));
        lecturerOutputFlow.getChildren().add(t);
    }
}