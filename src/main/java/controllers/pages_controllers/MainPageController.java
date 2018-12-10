package controllers.pages_controllers;

import controllers.AbstractController;
import entities.users.User;
import utils.security.SecurityConfig;
import utils.security.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageController extends AbstractController {
    public static final String MAPPING = "/main";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = UserUtils.getLoginedUser(req);
        if (user != null) {
            String infoPage = SecurityConfig.Roles.valueOf(user.getRole()).getInfoPage();
            forward(infoPage, req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            forwardError(INDEX_URL, req.getParameter(ERROR), req, resp);
        }
    }
}
