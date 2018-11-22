package controllers;

import dao.impl.jdbc.JDBCQuestionImpl;
import dao.interfaces.JDBCQuestion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class QuestionController extends AbstractController {
    private JDBCQuestion handler;

    @Override
    public void init() {
        handler = new JDBCQuestionImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String question = req.getParameter("question");
        try {
            handler.removeQuestion(userName, question);
            req.setAttribute("data","The question removed");
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String question = req.getParameter("question");
        try {
            handler.addQuestion(question);
            req.setAttribute("data", "The question added");
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }
}
