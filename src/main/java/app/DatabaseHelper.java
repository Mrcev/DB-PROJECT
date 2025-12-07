package app;

import java.sql.*;

public class DatabaseHelper {

    String url = "jdbc:mysql://localhost:3306/ ... ";
    String user = "";
    String password = "";

    // Conencting to mySql method
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Student Informtion Retrieval

    public ResultSet getStudentInfo(int student_id) {
        ResultSet rs = null;
        try {
            Connection conn = getConnection();
            String sql = "";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);

            rs = preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet studentCourses(int student_id) {
        ResultSet rs = null;
        try {
            Connection conn = getConnection();
            String sql = "";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, student_id);

            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    // to be continued...
}