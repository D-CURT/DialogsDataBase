package dao.impl.hibernate;

import entities.Answer;
import entities.Question;
import entities.Relations;
import entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.SessionFactoryManager;

public class RelationsDAO {
    public static void askQuestion(String username, String content) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = UserDAO.getUser(username);
        QuestionDAO.addQuestion(new Question(content));
        Question question = QuestionDAO.getQuestion(content);
        Relations relations = new Relations(user, question);
        session.save(relations);
        transaction.commit();
        session.close();
    }

    public static void answerQuestion(String username, String question, String answer) {
        Session session = SessionFactoryManager.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = UserDAO.getUser(username);
        Question question1 = QuestionDAO.getQuestion(question);
        AnswerDAO.addAnswer(new Answer(answer));
        Answer answer1 = AnswerDAO.getAnswer(answer);
        Relations relations = getRelation(user, question1, session);
        relations.setAnswer(answer1);
        session.update(relations);
        transaction.commit();
        session.close();
    }

    private static Relations getRelation(User user, Question question, Session session) {
        Query query = session.createQuery(
                "from Relations where user =: user and question =: question");
        query.setParameter("user", user);
        query.setParameter("question", question);
        return (Relations) query.uniqueResult();
    }
}
