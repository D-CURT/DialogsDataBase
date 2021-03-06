package controllers;

import dao.impl.hibernate.HibernateQuestionImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/quest")
public class QuestionController extends AbstractController {
    private HibernateQuestionImpl handler;

    @Override
    public void init() {
        handler = new HibernateQuestionImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String question = req.getParameter("question");
        handler.removeQuestion(question);
        req.setAttribute("data","The question removed");
        forward(INDEX_URL, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String question = req.getParameter("question");
        handler.addQuestion(question);
        req.setAttribute("data", "The question added");
        forward(INDEX_URL, req, resp);
    }

    public HibernateQuestionImpl getHandler() {
        return handler;
    }
}
