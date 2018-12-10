package controllers.pages_controllers;

import controllers.AbstractController;
import utils.SecurityUtils;

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
        if (SecurityUtils.hasPermission(req)) {
            forward(INDEX_URL, req, resp);
        }
    }
}
