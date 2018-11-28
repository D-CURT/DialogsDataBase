package dao.impl.hibernate;

import entities.Answer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
public class HibernateAnswerImpl extends AbstractHibernateImpl{
    public void addAnswer(Answer answer, Session session) {
        if (getAnswer(answer.getContent(), session) == null) {
            session.save(answer);
        }
    }

    public Answer getAnswer(int id) {
        return SessionFactoryManager.getInstance()
                                    .getSession()
                                    .get(Answer.class, id);
    }

    public Answer getAnswer(String content, Session session) {
        Query query = session.createQuery("from Answer where content=:content");
        query.setParameter("content", content);

        return (Answer) query.uniqueResult();
    }

    public List getAnswers() {
        return getTable("Answer");
    }

    @Override
    public int countRows() {
        return countTableRows("Answer");
    }
}
