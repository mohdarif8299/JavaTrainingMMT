package driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    public static java.sql.Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Class Not Found");
            return null;
        }

        java.sql.Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mmt_db", "root", "");
        } catch (SQLException e) {
            System.out.println("Error in Connection with Database mmt_db");
        }
        return con;
    }
}
