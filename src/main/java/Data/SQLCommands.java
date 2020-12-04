package Data;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.io.FileUtils;

public class SQLCommands {

        public static boolean createTables() {
                GetConnection GT = new GetConnection();
                Statement statement = null;
                Connection connection = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.createStatement();
                        statement.executeUpdate(readToString("CreateSQLScript.sql"));
                        return true;
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } catch (Exception ex) {
                        ex.printStackTrace();
                } finally {
                        try {
                                if (statement != null) {
                                        statement.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                        try {
                                if (connection != null) {
                                        connection.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                }
                return false;
        }

        public static String readToString(String fname) throws Exception {
                File file = new File(fname);
                String string = FileUtils.readFileToString(file, "utf-8");
                return string;
        }

        public static void checkprior() {
                GetConnection GT = new GetConnection();
                Statement statement = null;
                Connection connection = null;
                ResultSet rs = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.createStatement();
                        rs = statement.executeQuery("SELECT  id, name FROM priorities");
                        boolean integrality = true;
                        while (rs.next()) {
                                if (!(rs.getString("name").equals("Normal")&&rs.getLong("id")==1)&&
                                        !(rs.getString("name").equals("Cito")&&rs.getLong("id")==2)&&
                                        !(rs.getString("name").equals("Statim")&&rs.getLong("id")==3)) {
                                        integrality = false;
                                }
                        }
                        if (!integrality) {
                                System.out.println("Incorrect priorities data,refreshing");
                                statement.execute("DELETE FROM priorities");
                                addprior();
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } catch (Exception ex) {
                        ex.printStackTrace();
                } finally {
                        try {
                                if (statement != null) {
                                        statement.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                        try {
                                if (connection != null) {
                                        connection.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                }
        }

        public static void addprior() {
                GetConnection GT = new GetConnection();
                Statement statement = null;
                Connection connection = null;
                ResultSet rs = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.createStatement();
                         statement.execute("INSERT INTO priorities ( id , name ) VALUES ( 1 , 'Normal' )");
                          statement.execute("INSERT INTO priorities ( id , name ) VALUES ( 2 , 'Cito' )");
                         statement.execute("INSERT INTO priorities ( id , name ) VALUES ( 3 , 'Statim' )");
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } catch (Exception ex) {
                        ex.printStackTrace();
                } finally {
                        try {
                                if (statement != null) {
                                        statement.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                        try {
                                if (connection != null) {
                                        connection.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
                }
        }

}
