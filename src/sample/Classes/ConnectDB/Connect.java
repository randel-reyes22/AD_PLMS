package sample.Classes.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection Link() {

        // SQLite connection string
        String url = "jdbc:sqlite:Loan.db";
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;

    }
}
