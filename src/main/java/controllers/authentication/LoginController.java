package controllers.authentication;

import controllers.AbstractController;
import controllers.pages_controllers.MainPageController;
import utils.SecurityUtils;
import utils.UserUtils;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends AbstractController {
    public static final String MAPPING = "/login";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SecurityUtils.hasPermission(UserUtils.getLoginedUser(req))) {
            forward(MainPageController.MAPPING, req, resp);
        }
    }
}
