package app.controller;

import app.database.DatabaseHelper;
import app.model.Course;
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
    public void handleStudentEnter(ActionEvent event) {
        //  Get Input
        String studentID = infoEnterStudent.getText();
        studentOutputFlow.getChildren().clear();

        //  Background Validation
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
                    printToStudentConsole("Name: " + student.getName() + " " + student.getSurname() + "\nEmail: " + student.getEmail() + "\nGPA: " + String.format("%.2f", student.getGpa()) + "\n", Color.DARKBLUE, false);
                } else {
                    printToStudentConsole("Student not found.", Color.RED, false);
                }
                break;
            case "SUPERVISOR":
                printToStudentConsole("Fetching Supervisor...\n", Color.BLACK, true);
                Lecturer supervisor = dbHelper.getSupervisorInfo(studentId);
                if (supervisor != null) {
                    printToStudentConsole("Supervisor: " + supervisor.getName() + " " + supervisor.getSurname() + "\nEmail: " + supervisor.getEmail() + "\nPhone: " + supervisor.getPhone(), Color.DARKGREEN, false);
                } else {
                    printToStudentConsole("Supervisor not found.", Color.RED, false);
                }
                break;
            case "COURSES":
                printToStudentConsole("Student Courses:\n", Color.BLACK, true);
                List<Course> courses = dbHelper.studentCourses(studentId);
                if (!courses.isEmpty()) {
                    StringBuilder courseList = new StringBuilder();
                    for (Course course : courses) {
                        courseList.append("- ").append(course.getCourseName()).append(" (").append(course.getCourseCode()).append(")\n");
                    }
                    printToStudentConsole(courseList.toString(), Color.DARKMAGENTA, false);
                } else {
                    printToStudentConsole("No courses found.", Color.RED, false);
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
    public void handleLecturerEnter(ActionEvent event) {
        String lecturerID = infoEnterLecturer.getText();
        lecturerOutputFlow.getChildren().clear();

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
                    printToLecturerConsole(lecturer.getName() + " " + lecturer.getSurname() + "\nEmail: " + lecturer.getEmail() + "\nPhone: " + lecturer.getPhone(), Color.DARKBLUE, false);
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
                        studentList.append("- ").append(student.getName()).append(" ").append(student.getSurname())
                            .append(" (ID: ").append(student.getId()).append(")\n");
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
                        courseList.append("- ").append(course.getCourseName()).append(" (").append(course.getCourseCode()).append(")\n");
                    }
                    printToLecturerConsole(courseList.toString(), Color.DARKMAGENTA, false);
                } else {
                    printToLecturerConsole("No courses found.", Color.RED, false);
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
        lecturerOutputFlow.getChildren().add(textNode); }}
