package Data.DAO;

import java.util.List;

public interface DAO<Subject,Key> {
boolean createInTableBySubject(Subject stuff);
Subject readToSubjectById(Key key);
List<Subject> readToSubjectList();
boolean updateInTableBySubject(Subject stuff);
boolean deleteInTableBySubject(Subject stuff);
}
