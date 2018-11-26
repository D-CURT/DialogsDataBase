package dao.impl.hibernate;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

import java.util.ArrayList;
import java.util.List;

public class RelationsDAO {
    public static void askQuestion(String username, String content) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = UserDAO.getUser(username);
        QuestionDAO.addQuestion(new Question(content));
        Question question = QuestionDAO.getQuestion(content);
        Relations relation;
        if (getRelation(user, question, session) != null) {
            transaction.rollback();
        } else {
            relation = new Relations(user, question);
            session.save(relation);
            transaction.commit();
        }
        session.close();
    }

    public static void answerQuestion(String username, String questionContent, String answerContent) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = UserDAO.getUser(username);
        Question question = QuestionDAO.getQuestion(questionContent);
        AnswerDAO.addAnswer(new Answer(answerContent), session);
        Answer answer = AnswerDAO.getAnswer(answerContent, session);
        Relations relation = getRelation(user, question, session);
        relation.setAnswer(answer);
        session.update(relation);
        transaction.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public static List<Relations> getRelations() {
        List<Relations> relations = new ArrayList<>();
        Query query = SessionFactoryManager.getInstance()
                .getSession()
                .createQuery("from Relations");
        query.getResultList().forEach(o -> relations.add((Relations) o));
        return relations;
    }

    public static void removeRelation(Relations relation, Session session) {
        session.delete(relation);
        session.close();
    }

    private static Relations getRelation(User user, Question question, Session session) {
        Query query = session.createQuery(
                "from Relations where user =: user and question =: question and answer is null ");
        query.setParameter("user", user);
        query.setParameter("question", question);

        return (Relations) query.uniqueResult();
    }
}
