package controllers.authentication.filters;

import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import utils.context.RequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/login")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String login = servletRequest.getParameter("login");
        String password = servletRequest.getParameter("password");

        PrintWriter out = servletResponse.getWriter();
        if ((login == null || password == null) || (login.isEmpty() || password.isEmpty())) {
            out.write("Fields cannot be empty!");
        }

        User user;
        HibernateUserImpl impl = new HibernateUserImpl();
        if ((user = impl.getUser(login, password)) == null) {
            out.write("User not found");
        } else {
            servletRequest.setAttribute("user", user);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
