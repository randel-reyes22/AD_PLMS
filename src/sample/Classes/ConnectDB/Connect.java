package sample.Classes.ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection Link() {

        //MSSQL Server connection string
        final String LocalURL = "jdbc:sqlserver://Server=RANDEL-PC;Database=DPLMS; IntegratedSecurity=True; Uid=auth_windows";
        //final String url2 = "jdbc:sqlserver://Provider=SQLOLEDB.1;Integrated Security=SSPI;Persist Security Info=False;Initial Catalog=DPLMS;Data Source=RANDEL-PC";

        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(LocalURL);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;

    }
}
