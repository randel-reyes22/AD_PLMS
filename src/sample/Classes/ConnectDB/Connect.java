package sample.Classes.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection Link() {

        //MSSQL Server connection string
        final String LappyURL = "jdbc:sqlserver://RANDEL-LAPPY:1433;instanceName=SQLEXPRESS;databaseName=DPLMS;IntegratedSecurity=True;";
        final String PCURL = "jdbc:sqlserver://RANDEL-PC:1433;databaseName=DPLMS;IntegratedSecurity=True;";

        Connection conn = null;

        try
        {
            conn = DriverManager.getConnection(PCURL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            try {
                conn.close();
            } catch (NullPointerException | SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;

    }
}
