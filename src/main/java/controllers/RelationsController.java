package controllers;

import dao.impl.JDBCRelationsImpl;
import dao.interfaces.JDBCRelations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RelationsController extends AbstractController {
    private JDBCRelations handler;

    @Override
    public void init() {
        handler = new JDBCRelationsImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
        try {
            req.setAttribute("data", handler.getFullData());
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String section = req.getParameter("section");
        String userName;
        String question;
        String answer;

        try {
            if (section.equals("ASK_QUESTION")) {

                userName = req.getParameter("userName");
                question = req.getParameter("question");
                handler.askQuestion(userName, question);
                req.setAttribute("data", "The question is asked");
            }
            if (section.equals("ANSWER_QUESTION")) {
                userName = req.getParameter("userName");
                question = req.getParameter("question");
                answer = req.getParameter("answer");
                handler.answerQuestion(userName, question, answer);
                req.setAttribute("data", "The answer received");
            }
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }
}
