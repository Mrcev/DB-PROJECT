module student.record.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens app to javafx.fxml;
    opens app.controller to javafx.fxml;
    opens app.model to javafx.base;

    exports app;
    exports app.controller;
    exports app.model;
    exports app.database;
}