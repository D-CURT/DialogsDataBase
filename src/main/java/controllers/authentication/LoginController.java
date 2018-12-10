package controllers.authentication;

import controllers.AbstractController;
import controllers.pages_controllers.MainPageController;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AbstractController {
    public static final String MAPPING = "/login";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(MainPageController.MAPPING, req, resp);
    }
}
