module database_frontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires mysql.connector.j;

    // "opens" allows JavaFX to use reflection to access your controller code
    opens app to javafx.fxml;

    // "exports" makes your package visible to the JavaFX system
    exports app;
}