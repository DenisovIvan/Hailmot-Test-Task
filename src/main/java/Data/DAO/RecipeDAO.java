package Data.DAO;

import Data.GetConnection;
import Data.Things.Recipe;
import java.sql.Connection;
import static Data.GetConnection.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements DAO<Recipe, String> {
GetConnection GT = new GetConnection();
        @Override
        public boolean createInTableBySubject(Recipe recipe) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection =GT.setConnect();
                        statement = connection.prepareStatement(SQLRecipe.INSERT.QUERY);
                        statement.setString(1, recipe.getDiscription());
                        statement.setLong(2, recipe.getPatient().getId());
                        statement.setLong(3, recipe.getDoctor().getId());
                        statement.setObject(4, recipe.getCalendar());
                        statement.setInt(5, recipe.getValidaty());
                        statement.setLong(6, recipe.getPriority().getId());
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
        public Recipe readToSubjectById(String key) {
                Connection connection = null;
                PreparedStatement statement = null;
                ResultSet rs = null;
                final Recipe result = new Recipe();
                result.setId(-1);
                try {
                         connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLRecipe.GET.QUERY);
                        statement.setLong(1, Long.parseLong(key));
                        rs = statement.executeQuery();
                        if (rs.next()) {
                                result.setId(rs.getLong("id"));
                                result.setDiscription(rs.getString("discription"));
                                result.setPatient(new Recipe.RecipePatient(rs.getLong("pt_id"), rs.getString("pt_name"), rs.getString("pt_surname"), rs.getString("pt_patronymic")));
                                result.setDoctor(new Recipe.RecipeDoctor(rs.getLong("doc_id"), rs.getString("doc_name"), rs.getString("doc_surname"), rs.getString("doc_patronymic")));
                                result.setCalendar(rs.getTimestamp("calendar"));
                                result.setValidaty(rs.getInt("validaty"));
                                result.setPriority(new Recipe.RecipePriority(rs.getLong("pr_id"), rs.getString("pr_name")));
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
        public List<Recipe> readToSubjectList() {
                Connection connection = null;
                Statement statement = null;
                ResultSet rs = null;
                List<Recipe> list = new ArrayList<Recipe>();
                Recipe result;
                try { connection = GT.setConnect();
                        statement = connection.createStatement();
                        rs = statement.executeQuery(SQLRecipe.GETLIST.QUERY);
                        
                        while (rs.next()) {
                                result = new Recipe();
                                result.setId(rs.getLong("id"));
                                result.setDiscription(rs.getString("discription"));
                                result.setPatient(new Recipe.RecipePatient(rs.getLong("pt_id"), rs.getString("pt_name"), rs.getString("pt_surname"), rs.getString("pt_patronymic")));
                                result.setDoctor(new Recipe.RecipeDoctor(rs.getLong("doc_id"), rs.getString("doc_name"), rs.getString("doc_surname"), rs.getString("doc_patronymic")));
                                result.setCalendar(rs.getTimestamp("calendar"));
                                result.setValidaty(rs.getInt("validaty"));
                                result.setPriority(new Recipe.RecipePriority(rs.getLong("pr_id"), rs.getString("pr_name")));
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
        public boolean updateInTableBySubject(Recipe recipe) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLRecipe.UPDATE.QUERY);
                        statement.setString(1, recipe.getDiscription());
                        statement.setLong(2, recipe.getPatient().getId());
                        statement.setLong(3, recipe.getDoctor().getId());
                        statement.setObject(4, recipe.getCalendar());
                        statement.setInt(5, recipe.getValidaty());
                        statement.setLong(6, recipe.getPriority().getId());
                        statement.setLong(7, recipe.getId());
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
        public boolean deleteInTableBySubject(Recipe recipe) {
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                        connection = GT.setConnect();
                        statement = connection.prepareStatement(SQLRecipe.DELETE.QUERY);
                        statement.setLong(1, recipe.getId());
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

        enum SQLRecipe {
                GETLIST("SELECT re.id, re.discription, "
                        + "pt.id AS pt_id, pt.name AS pt_name, pt.surname AS pt_surname, pt.patronymic AS pt_patronymic, "
                        + "doc.id AS doc_id, doc.name AS doc_name, doc.surname AS doc_surname, doc.patronymic AS doc_patronymic "
                        + ", re.calendar , re.validaty, pr.id AS pr_id, pr.name AS pr_name "
                        + "FROM recipes AS re "
                        + "LEFT JOIN patients AS pt ON re.patientID = pt.id "
                        + "LEFT JOIN doctors AS doc ON re.doctorID = doc.id "
                        + "LEFT JOIN priorities AS pr ON re.priorityID = pr.id"),
                GET("SELECT re.id, re.discription, "
                        + "pt.id AS pt_id, pt.name AS pt_name, pt.surname AS pt_surname, pt.patronymic AS pt_patronymic, "
                        + "doc.id AS doc_id, doc.name AS doc_name, doc.surname AS doc_surname, doc.patronymic AS doc_patronymic "
                        + ", re.calendar , re.validaty, pr.id AS pr_id, pr.name AS pr_name "
                        + "FROM recipes AS re "
                        + "LEFT JOIN patients AS pt ON re.patientID = pt.id "
                        + "LEFT JOIN doctors AS doc ON re.doctorID = doc.id "
                        + "LEFT JOIN priorities AS pr ON re.priorityID = pr.id"
                        + " WHERE re.id = (?)"),
                INSERT("INSERT INTO recipes (id, discription, patientID, doctorID, calendar, validaty, priorityID ) VALUES (DEFAULT, (?), (?), (?), (?), (?), (?))"),
                DELETE("DELETE FROM recipes WHERE id = (?)"),
                UPDATE("UPDATE recipes SET discription = (?), patientID = (?) , doctorID = (?) , calendar = (?) , validaty = (?) , priorityID = (?) WHERE id = (?)");

                String QUERY;

                SQLRecipe(String QUERY) {
                        this.QUERY = QUERY;
                }
        }
}
