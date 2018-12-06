package controllers.authentication;

import controllers.AbstractController;
import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import utils.context.RequestContext;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends AbstractController {
    private HibernateUserImpl userHandler;

    @Override
    public void init() throws ServletException {
        userHandler = new HibernateUserImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            RequestContext.getInstance().setUser((User) req.getAttribute("user"));
            forward(INDEX_URL, req, resp);
    }

    public HibernateUserImpl getUserHandler() {
        return userHandler;
    }
}
