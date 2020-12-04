package Data.DAO;


import Data.GetConnection;
import Data.Things.Patient;
import static Data.GetConnection.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PatientDAO implements DAO<Patient, String> {
GetConnection GT = new GetConnection();
        public PatientDAO() {

        }

        @Override
        public boolean createInTableBySubject(Patient patient) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection =GT.setConnect();
                        statement = connection.prepareStatement(SQLPatient.INSERT.QUERY);
                        statement.setString(1, patient.getName());
                        statement.setString(2, patient.getSurname());
                        statement.setString(3, patient.getPatronymic());
                        statement.setString(4, patient.getTelephone());
                        statement.executeUpdate();
                        return true;
                } catch (SQLException ex) {
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

        @Override
        public Patient readToSubjectById(String key) {
                final Patient result = new Patient();
                result.setId(-1);
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet rs = null;
                try {
                        connection =GT.setConnect();
                        statement = connection.prepareStatement(SQLPatient.GET.QUERY);
                        statement.setLong(1, Long.parseLong(key));
                        rs = statement.executeQuery();
                        if (rs.next()) {
                                result.setId(rs.getLong("id"));
                                result.setName(rs.getString("name"));
                                result.setSurname(rs.getString("surname"));
                                result.setPatronymic(rs.getString("patronymic"));
                                result.setTelephone(rs.getString("telephone"));
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } finally {
                        try {
                                if (rs != null) {
                                        rs.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
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
                return result;
        }
        
                @Override
        public List<Patient> readToSubjectList() {
                List<Patient> list =new ArrayList<Patient>();
                Connection connection = null;
                Statement statement = null;
                Patient result;
                ResultSet rs = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.createStatement();
                        rs = statement.executeQuery(SQLPatient.GETLIST.QUERY);
                        while (rs.next()) {
                                result = new Patient();
                                result.setId(rs.getLong("id"));
                                result.setName(rs.getString("name"));
                                result.setSurname(rs.getString("surname"));
                                result.setPatronymic(rs.getString("patronymic"));
                                result.setTelephone(rs.getString("telephone"));
                                list.add(result);
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                } finally {
                        try {
                                if (rs != null) {
                                        rs.close();
                                }
                        } catch (SQLException ex) {
                                ex.printStackTrace();
                        }
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
                return list;
        }

        @Override
        public boolean updateInTableBySubject(Patient patient) {
                 Connection connection = null;
                PreparedStatement statement = null;
                try  {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLPatient.UPDATE.QUERY);
                        statement.setString(1, patient.getName());
                        statement.setString(2, patient.getSurname());
                        statement.setString(3, patient.getPatronymic());
                        statement.setString(4, patient.getTelephone());
                        statement.setLong(5, patient.getId());
                        statement.executeUpdate();
                        return true;
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
                finally {
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

        @Override
        public boolean deleteInTableBySubject(Patient patient) {
                Connection connection = null;
                PreparedStatement statement = null;
                try  {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLPatient.DELETE.QUERY);
                        statement.setLong(1, patient.getId());
                        statement.executeUpdate();
                        return true;
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
                finally {
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


        enum SQLPatient {
                GETLIST("SELECT id, name, surname, patronymic, telephone FROM patients"),
                GET("SELECT id, name, surname, patronymic, telephone FROM patients WHERE id = (?)"),
                INSERT("INSERT INTO patients (id, name, surname, patronymic, telephone) VALUES (DEFAULT, (?), (?), (?), (?))"),
                DELETE("DELETE FROM patients WHERE id = (?)"),
                UPDATE("UPDATE patients SET name = (?) , surname = (?) , patronymic = (?) , telephone = (?) WHERE id = (?)");

                String QUERY;

                SQLPatient(String QUERY) {
                        this.QUERY = QUERY;
                }
        }
}
