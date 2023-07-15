package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDB {

    private static final String DB_URL = "jdbc:mysql://localhost:4306/movieMGT_DB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    

    public static void main(String[] args) {

        Connection con = MysqlDB.getConnection();
        if (con == null) {
            System.out.println("Failed to connect");
        } else {
            System.out.println("Successful connection");
        }

    }

}
