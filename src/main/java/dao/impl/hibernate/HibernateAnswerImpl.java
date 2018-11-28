package dao.impl.hibernate;

import entities.Answer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.connectors.SessionFactoryManager;

import java.util.List;

import static utils.queries.HQLSection.SELECT_ANSWER_BY_CONTENT;

@SuppressWarnings("JpaQlInspection")
public class HibernateAnswerImpl extends AbstractHibernateImpl{
    private static final String TABLE_NAME = "Answer";

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
        final String CONTENT_FIELD = "content";
        Query query = session.createQuery(SELECT_ANSWER_BY_CONTENT.getHql());
        query.setParameter(CONTENT_FIELD, content);

        return (Answer) query.uniqueResult();
    }

    public List getAnswers() {
        return getTable(TABLE_NAME);
    }

    @Override
    public int countRows() {
        return countTableRows(TABLE_NAME);
    }
}
