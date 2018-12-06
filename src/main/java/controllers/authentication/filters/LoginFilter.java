package controllers.authentication.filters;

import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import utils.context.RequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
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
        HibernateUserImpl hibernateUser = new HibernateUserImpl();
        if ((user = hibernateUser.getUser(login, password)) != null) {
            RequestContext.getInstance().setUser(user);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
