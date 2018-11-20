package controllers;

import dao.impl.JDBCUserImpl;
import dao.interfaces.JDBCUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserController extends AbstractController {
    private JDBCUser handler;

    @Override
    public void init() {
        handler = new JDBCUserImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        try {
            handler.removeUser(userName);
            req.setAttribute("data", "The user removed");
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        try {
            handler.addUser(userName);
            req.setAttribute("data", "The user added");
            forward(INDEX_URL, req, resp);
        } catch (SQLException e) {
            forwardError(INDEX_URL, e.getMessage(), req, resp);
        }
    }
}
