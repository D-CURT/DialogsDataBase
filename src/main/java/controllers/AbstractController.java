package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbstractController extends HttpServlet {
    final String INDEX_URL = "/index.jsp";

    final void forward(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(url).forward(request,response);
    }

    final void forwardError(String url, String error, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.getRequestDispatcher(url).forward(request, response);
    }
}
