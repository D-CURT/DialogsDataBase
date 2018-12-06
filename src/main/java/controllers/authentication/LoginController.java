package controllers.authentication;

import controllers.AbstractController;
import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import utils.context.RequestContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AbstractController {
    private HibernateUserImpl userHandler;

    @Override
    public void init() throws ServletException {
        userHandler = new HibernateUserImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user;
        if ((user = userHandler.getUser(login, password)) != null) {
            RequestContext.getInstance().setUser(user);
            forward(INDEX_URL, req, resp);
        } else {
            forwardError(INDEX_URL, "User not found", req, resp);
        }
    }
}
