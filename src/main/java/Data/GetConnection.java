package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {


         public Connection setConnect() {
                  Connection connection = null;
                try {
                        Class.forName("org.hsqldb.jdbc.JDBCDriver");
                        connection = DriverManager.getConnection("jdbc:hsqldb:file:database/mydatabase;hsqldb.lock_file=false", "SA", "");
                } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
                 return connection;
        }



}
