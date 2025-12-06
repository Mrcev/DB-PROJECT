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

        switch (currentStudentAction) {
            case "INFO":
                printToStudentConsole("Displaying Student Information...\n", Color.BLACK, true);
                printToStudentConsole("Name: John Doe\nYear: 2\nMajor: CS\nGPA: 3.8", Color.DARKBLUE, false);
                break;
            case "SUPERVISOR":
                printToStudentConsole("Fetching Supervisor...\n", Color.BLACK, true);
                printToStudentConsole("Supervisor: Dr. Alan Smith\nOffice: 304\nEmail: asmith@uni.edu", Color.DARKGREEN, false);
                break;
            case "COURSES":
                printToStudentConsole("Student Courses:\n", Color.BLACK, true);
                printToStudentConsole("- JavaFX Programming (CS101)\n- Database Systems (CS202)", Color.DARKMAGENTA, false);
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
        switch (currentLecturerAction) {
            case "INFO":
                printToLecturerConsole("Lecturer Profile:\nDr. Smith\nPhD in AI\nTenure Track", Color.DARKBLUE, false);
                break;
            case "SUPERVISEE":
                printToLecturerConsole("Supervising:\n- S12345 (John)\n- S67890 (Jane)\n- S55441 (Mike)", Color.DARKGREEN, false);
                break;
            case "COURSES":
                printToLecturerConsole("Teaching:\n- Intro to AI (Mon 9AM)\n- Advanced Java (Wed 2PM)", Color.DARKMAGENTA, false);
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