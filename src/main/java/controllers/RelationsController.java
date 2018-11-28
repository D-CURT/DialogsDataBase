package controllers;

import dao.impl.hibernate.HibernateRelationsImpl;
import dao.impl.jdbc.JDBCRelationsImpl;
import dao.interfaces.JDBCRelations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RelationsController extends AbstractController {
//    private JDBCRelations handler;
    private HibernateRelationsImpl handler;

    @Override
    public void init() {
//        handler = new JDBCRelationsImpl();
        handler = new HibernateRelationsImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("multipleData", handler.getFullData().toString()/*.split(";")*/);
            forward(INDEX_URL, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String section = req.getParameter("section");
        String userName = req.getParameter("userName");
        String question = req.getParameter("question");
        String answer;

            if (section.equals("ASK_QUESTION")) {
                handler.askQuestion(userName, question);
            }
            if (section.equals("ANSWER_QUESTION")) {
                answer = req.getParameter("answer");
                handler.answerQuestion(userName, question, answer);
                req.setAttribute("data", "The answer received");
            }
            forward(INDEX_URL, req, resp);
    }
}
