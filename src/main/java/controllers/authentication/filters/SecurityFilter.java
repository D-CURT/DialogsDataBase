package controllers.authentication.filters;

import controllers.authentication.LoginController;
import entities.users.User;
import utils.UserRoleRequestWrapper;
import utils.UserUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();

        User user = UserUtils.getLoginedUser(request);

        if (servletPath.equals(LoginController.MAPPING)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        HttpServletRequest wrapRequest = request;

        if (user != null) {
            String userName = user.getLogin();
            String role = user.getRole();

            wrapRequest = new UserRoleRequestWrapper(userName, role, request);
        }
    }

    @Override
    public void destroy() {

    }
}
