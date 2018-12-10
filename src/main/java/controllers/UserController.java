package controllers;

import dao.impl.hibernate.HibernateUserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/user")
public class UserController extends AbstractController {
    private HibernateUserImpl handler;

    @Override
    public void init() {
        handler = new HibernateUserImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");

        handler.removeUser(userName);
        req.setAttribute("data", "The user removed");
        forward(INDEX_URL, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String userName = req.getParameter("userName");
        String passportKey = req.getParameter("passportKey");
        handler.addUser(login, password, userName, passportKey, null, null);
        req.setAttribute("data", "The user added");
        forward(INDEX_URL, req, resp);
    }

    public HibernateUserImpl getHandler() {
        return handler;
    }
}
