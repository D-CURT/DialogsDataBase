package controllers;

import dao.impl.jdbc.JDBCRelationsImpl;
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
        try {
            req.setAttribute("multipleData", handler.getFullData().split(";"));
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String section = req.getParameter("section");
        String userName = req.getParameter("userName");
        String question = req.getParameter("question");
        String answer;
        String message;

        try {
            if (section.equals("ASK_QUESTION")) {
                if (handler.askQuestion(userName, question) > 0)
                    message = "The question is asked";
                else message = "The question already asked";
                req.setAttribute("data", message);
            }
            if (section.equals("ANSWER_QUESTION")) {
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
