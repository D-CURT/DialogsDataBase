package controllers.pages_controllers;

import controllers.AbstractController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPageController extends AbstractController {
    public static final String MAPPING = "/main";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(INDEX_URL, req, resp);
    }
}
