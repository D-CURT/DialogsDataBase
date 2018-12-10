package controllers.authentication.filters;

import controllers.AbstractController;
import controllers.authentication.LoginController;
import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import sun.misc.BASE64Decoder;
import utils.security.UserUtils;
import utils.context.RequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        if (!servletPath.equals("/index.jsp")) {
            if (UserUtils.getLoginedUser(request) == null) {
                String excluded = "Basic ";
                String authorization = request.getHeader("Authorization").substring(excluded.length());
                authorization = new String(new BASE64Decoder().decodeBuffer(authorization));

                String[] strings = authorization.split(":");
                String login = strings[0];
                String password = strings[1];

                PrintWriter out = servletResponse.getWriter();
                if ((login == null || password == null) || (login.isEmpty() || password.isEmpty())) {
                    out.write("Fields cannot be empty!");
                }

                User user;
                HibernateUserImpl hibernateUser = new HibernateUserImpl();
                if ((user = hibernateUser.getUser(login, password)) != null) {
                    RequestContext.getInstance().setUser(user);
                    UserUtils.storeLoginedUser(servletRequest, user);
                }
            }

            if (servletPath.equals(LoginController.MAPPING)) {
                filterChain.doFilter(request, response);
            }
        } else filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
