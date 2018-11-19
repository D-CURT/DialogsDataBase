package controllers;

import dao.impl.JDBCHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CommunicationController extends AbstractController{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String section = req.getParameter("section");
        String userName;
        String question;
        String answer;
        JDBCHandler jdbc = new JDBCHandler();
        try {
            switch (section) {
                case "ADD_USER": jdbc.addUser(req.getParameter("userName"));
                    break;
                case "ADD_QUESTION": jdbc.addQuestion(req.getParameter("question"));
                    break;
                case "ASK_QUESTION":
                    userName = req.getParameter("userName");
                    question = req.getParameter("question");
                    jdbc.askQuestion(userName, question);
                    break;
                case "ANSWER_QUESTION":
                    userName = req.getParameter("userName");
                    question = req.getParameter("question");
                    answer = req.getParameter("answer");
                    jdbc.answerQuestion(userName, question, answer);
                    break;
                case "REMOVE_USER": jdbc.removeUser(req.getParameter("userName"));
                    break;
                case "REMOVE_QUESTION":
                    userName = req.getParameter("userName");
                    question = req.getParameter("question");
                    jdbc.removeQuestion(userName, question);
                    break;
                case "GET_FULL_DATA": ;
                    req.setAttribute("data", jdbc.getFullData());
                    break;
            }
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }
}
