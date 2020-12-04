
package Data.DAO;
import Data.GetConnection;
import Data.Things.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO implements DAO<Doctor, String> {
        GetConnection GT = new GetConnection();

        public DoctorDAO() {
        }

        @Override
        public boolean createInTableBySubject(Doctor doctor) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLDoctor.INSERT.QUERY);
                        statement.setString(1, doctor.getName());
                        statement.setString(2, doctor.getSurname());
                        statement.setString(3, doctor.getPatronymic());
                        statement.setString(4, doctor.getSpecialization());
                        statement.executeUpdate();
                        return true;
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }finally {
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
        public Doctor readToSubjectById(String key) {
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet rs = null;
                final Doctor result = new Doctor();
                result.setId(-1);
                try {connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLDoctor.GET.QUERY);
                        statement.setLong(1, Long.parseLong(key));
                        rs = statement.executeQuery();
                        if (rs.next()) {
                                result.setId(rs.getLong("id"));
                                result.setName(rs.getString("name"));
                                result.setSurname(rs.getString("surname"));
                                result.setPatronymic(rs.getString("patronymic"));
                                result.setSpecialization(rs.getString("specialization"));
                        }
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }finally {
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
        public boolean updateInTableBySubject(Doctor doctor) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLDoctor.UPDATE.QUERY);
                        statement.setString(1, doctor.getName());
                        statement.setString(2, doctor.getSurname());
                        statement.setString(3, doctor.getPatronymic());
                        statement.setString(4, doctor.getSpecialization());
                        statement.setLong(5, doctor.getId());
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
        public List<Doctor> readToSubjectList() {
                List<Doctor> list =new ArrayList<Doctor>();
                Connection connection = null;
                Statement statement = null;
                Doctor result;
                ResultSet rs = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.createStatement();
                        rs = statement.executeQuery(SQLDoctor.GETLIST.QUERY);
                        result = new Doctor();
                        while (rs.next()) {
                                result = new Doctor();
                                result.setId(rs.getLong("id"));
                                result.setName(rs.getString("name"));
                                result.setSurname(rs.getString("surname"));
                                result.setPatronymic(rs.getString("patronymic"));
                                result.setSpecialization(rs.getString("specialization"));
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
                        System.out.println("end of listreading");
                return list;
        }

        @Override
        public boolean deleteInTableBySubject(Doctor doctor) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLDoctor.DELETE.QUERY);
                        statement.setLong(1, doctor.getId());
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



        enum SQLDoctor {
                GETLIST("SELECT id, name, surname, patronymic, specialization FROM doctors"),
                GET("SELECT id, name, surname, patronymic, specialization FROM doctors WHERE id = (?)"),
                INSERT("INSERT INTO doctors (id, name, surname, patronymic, specialization) VALUES (DEFAULT, (?), (?), (?), (?))"),
                DELETE("DELETE FROM doctors WHERE id = (?)"),
                UPDATE("UPDATE doctors SET name = (?), surname = (?), patronymic = (?) , specialization = (?) WHERE id = (?)");

                String QUERY;

                SQLDoctor(String QUERY) {
                        this.QUERY = QUERY;
                }
        }

}
