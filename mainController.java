package app;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
    private String currentAction = ""; // Stores current mode (INFO, DELETE, COURSES, etc.)

    // Instantiate Backend Helper
    private DatabaseHelper dbHelper = new DatabaseHelper();

    // =========================================================
    // 1. STUDENT OPERATIONS (Referencing Backend Manual Section 1)
    // =========================================================

    @FXML
    public void GetStudentInfo(ActionEvent event) {
        setupStudentTab("INFO", "Enter Student ID to view Info");
    }

    @FXML
    public void GetSupervisor(ActionEvent event) {
        setupStudentTab("SUPERVISOR", "Enter Student ID to find Supervisor");
    }

    @FXML
    public void GetCourses(ActionEvent event) {
        setupStudentTab("COURSES", "Enter Student ID to view Courses");
    }

    @FXML
    public void DeleteStudent(ActionEvent event) {
        setupStudentTab("DELETE_STUDENT", "Enter Student ID to DELETE");
    }

    @FXML
    public void EnrollStudent(ActionEvent event) {
        setupStudentTab("ENROLL", "Enter: StudentID, CourseID");
    }

    private void setupStudentTab(String action, String prompt) {
        mainTabPane.getSelectionModel().select(studentTab);
        currentAction = action;
        infoEnterStudent.setPromptText(prompt);
        infoEnterStudent.clear();
        studentOutputFlow.getChildren().clear();
        infoEnterStudent.requestFocus();
    }

    @FXML
    public void handleStudentEnter(ActionEvent event) {
        String input = infoEnterStudent.getText();
        studentOutputFlow.getChildren().clear();

        if (input == null || input.trim().isEmpty()) {
            printToConsole(studentOutputFlow, "Please enter a valid ID or parameters.", Color.RED, true);
            return;
        }

        try {
            switch (currentAction) {
                case "INFO":
                    // Calls getStudentInfo(int student_id)
                    int sId = Integer.parseInt(input);
                    printResultSet(dbHelper.getStudentInfo(sId), studentOutputFlow);
                    break;

                case "SUPERVISOR":
                    // Calls getSupervisorInfo(int student_id)
                    int supId = Integer.parseInt(input);
                    printResultSet(dbHelper.getSupervisorInfo(supId), studentOutputFlow);
                    break;

                case "COURSES":
                    // Calls studentCourses(int student_id)
                    int cId = Integer.parseInt(input);
                    printResultSet(dbHelper.studentCourses(cId), studentOutputFlow);
                    break;

                case "DELETE_STUDENT":
                    // Calls deleteStudent(int student_id)
                    int dId = Integer.parseInt(input);
                    boolean deleted = dbHelper.deleteStudent(dId);
                    printToConsole(studentOutputFlow, deleted ? "Student deleted successfully." : "Delete failed.", deleted ? Color.GREEN : Color.RED, true);
                    break;

                case "ENROLL":
                    // Calls enroll(int student_id, int course_id)
                    // Expecting input like "101, 202"
                    String[] parts = input.split(",");
                    if (parts.length == 2) {
                        boolean enrolled = dbHelper.enroll(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
                        printToConsole(studentOutputFlow, enrolled ? "Enrolled successfully." : "Enrollment failed.", enrolled ? Color.GREEN : Color.RED, true);
                    } else {
                        printToConsole(studentOutputFlow, "Format error. Use: StudentID, CourseID", Color.RED, true);
                    }
                    break;

                // --- Course Operations handled in Student Tab for convenience ---
                case "COURSE_INFO":
                    // Calls getCourseInfo(int course_id)
                    printResultSet(dbHelper.getCourseInfo(Integer.parseInt(input)), studentOutputFlow);
                    break;

                case "COURSE_STUDENTS":
                    // Calls courseStudents(int course_id)
                    printResultSet(dbHelper.courseStudents(Integer.parseInt(input)), studentOutputFlow);
                    break;

                case "DELETE_COURSE":
                    // Calls deleteCourse(int course_id)
                    boolean cDeleted = dbHelper.deleteCourse(Integer.parseInt(input));
                    printToConsole(studentOutputFlow, cDeleted ? "Course deleted successfully." : "Delete failed (Check enrollments).", cDeleted ? Color.GREEN : Color.RED, true);
                    break;

                default:
                    printToConsole(studentOutputFlow, "Unknown Action.", Color.GREY, false);
            }
        } catch (NumberFormatException e) {
            printToConsole(studentOutputFlow, "Input Error: Please enter numeric IDs.", Color.RED, true);
        } catch (Exception e) {
            printToConsole(studentOutputFlow, "Database Error: " + e.getMessage(), Color.RED, true);
        }
    }

    // =========================================================
    // 2. LECTURER OPERATIONS (Referencing Backend Manual Section 2)
    // =========================================================

    @FXML
    public void GetLecturerInfo(ActionEvent event) {
        setupLecturerTab("L_INFO", "Enter Lecturer ID");
    }

    @FXML
    public void GetSupervisee(ActionEvent event) {
        setupLecturerTab("L_SUPERVISEE", "Enter Lecturer ID");
    }

    @FXML
    public void GetLecturerCourses(ActionEvent event) {
        setupLecturerTab("L_COURSES", "Enter Lecturer ID");
    }

    @FXML
    public void DeleteLecturer(ActionEvent event) {
        setupLecturerTab("DELETE_LECTURER", "Enter Lecturer ID to DELETE");
    }

    private void setupLecturerTab(String action, String prompt) {
        mainTabPane.getSelectionModel().select(lecturerTab);
        currentAction = action;
        infoEnterLecturer.setPromptText(prompt);
        infoEnterLecturer.clear();
        lecturerOutputFlow.getChildren().clear();
        infoEnterLecturer.requestFocus();
    }

    @FXML
    public void handleLecturerEnter(ActionEvent event) {
        String input = infoEnterLecturer.getText();
        lecturerOutputFlow.getChildren().clear();

        if (input == null || input.trim().isEmpty()) {
            printToConsole(lecturerOutputFlow, "Please enter a valid ID.", Color.RED, true);
            return;
        }

        try {
            int lId = Integer.parseInt(input);

            switch (currentAction) {
                case "L_INFO":
                    // Calls getLecturerInfo(int lecturer_id)
                    printResultSet(dbHelper.getLecturerInfo(lId), lecturerOutputFlow);
                    break;
                case "L_SUPERVISEE":
                    // Calls getSupervisees(int lecturer_id)
                    printResultSet(dbHelper.getSupervisees(lId), lecturerOutputFlow);
                    break;
                case "L_COURSES":
                    // Calls lecturerCourses(int lecturer_id)
                    printResultSet(dbHelper.lecturerCourses(lId), lecturerOutputFlow);
                    break;
                case "DELETE_LECTURER":
                    // Calls deleteLecturer(int lecturer_id)
                    boolean deleted = dbHelper.deleteLecturer(lId);
                    printToConsole(lecturerOutputFlow, deleted ? "Lecturer deleted." : "Delete failed.", deleted ? Color.GREEN : Color.RED, true);
                    break;
                default:
                    printToConsole(lecturerOutputFlow, "Unknown Action.", Color.GREY, false);
            }
        } catch (NumberFormatException e) {
            printToConsole(lecturerOutputFlow, "Input Error: ID must be numeric.", Color.RED, true);
        }
    }

    // =========================================================
    // 3. COURSE OPERATIONS (Referencing Backend Manual Section 3)
    // =========================================================

    // Note: Output for courses is routed to the Student Tab for display convenience
    // to avoid creating a new Tab in FXML, preserving layout as requested.

    @FXML
    public void GetCourseInfo(ActionEvent event) {
        setupStudentTab("COURSE_INFO", "Enter Course ID");
    }

    @FXML
    public void GetCourseStudents(ActionEvent event) {
        setupStudentTab("COURSE_STUDENTS", "Enter Course ID");
    }

    @FXML
    public void DeleteCourse(ActionEvent event) {
        setupStudentTab("DELETE_COURSE", "Enter Course ID to DELETE");
    }


    // --- Helper Methods ---

    private void printToConsole(TextFlow flow, String message, Color color, boolean isBold) {
        Text textNode = new Text(message + "\n");
        textNode.setFill(color);
        textNode.setFont(Font.font("Courier New", isBold ? FontWeight.BOLD : FontWeight.NORMAL, 13));
        flow.getChildren().add(textNode);
    }

    private void printResultSet(ResultSet rs, TextFlow flow) {
        if (rs == null) {
            printToConsole(flow, "No data found or Database Error.", Color.RED, false);
            return;
        }
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            // Print Header
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
                header.append(meta.getColumnName(i)).append("\t");
            }
            printToConsole(flow, header.toString(), Color.DARKBLUE, true);
            printToConsole(flow, "--------------------------------------------------", Color.BLACK, false);

            // Print Rows
            boolean found = false;
            while (rs.next()) {
                found = true;
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    row.append(rs.getString(i)).append("\t");
                }
                printToConsole(flow, row.toString(), Color.BLACK, false);
            }

            if (!found) {
                printToConsole(flow, "(No records returned)", Color.GREY, false);
            }

        } catch (SQLException e) {
            printToConsole(flow, "Error reading data: " + e.getMessage(), Color.RED, false);
        }
    }
}