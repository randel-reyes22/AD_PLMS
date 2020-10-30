package sample.Classes.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection Link() {

        //MSSQL Server connection string
        final String LocalURL = "jdbc:sqlserver://ServerName=RANDEL-PC;Database=DPLMS; IntegratedSecurity=True;";
        final String URL2 = "jdbc:sqlserver://RANDEL-PC:1433;databaseName=DPLMS;integratedSecurity=true";
        final String URL = "jdbc:sqlserver://Integrated Security=SSPI;Persist Security Info=False;Initial Catalog=DPLMS;Data Source=RANDEL-PC:1433";

        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(LocalURL);
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
