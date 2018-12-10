package controllers.authentication.filters;

import controllers.authentication.LoginController;
import entities.users.User;
import utils.security.SecurityUtils;
import utils.security.UserRoleRequestWrapper;
import utils.security.UserUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        User user;

        user = UserUtils.getLoginedUser(request);

        if (servletPath.equals(LoginController.MAPPING)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        HttpServletRequest wrapRequest = request;

        if (user != null) {
            String userName = user.getLogin();
            String role = user.getRole();

            wrapRequest = new UserRoleRequestWrapper(userName, role, request);
        }

        if (SecurityUtils.isSecurityPage(request)) {
            if (user == null) {
                String requestUri = request.getRequestURI();

                int redirectId = UserUtils.storeRedirectAfterLoginUrl(requestUri);
                response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
            }

            boolean hasPermission = SecurityUtils.hasPermission(request);
            if (!hasPermission) {
                request.getServletContext().getRequestDispatcher("/WEB-INF/views/accessDeniedView.jsp")
                        .forward(request, response);

            }
        }
        filterChain.doFilter(wrapRequest, response);
    }

    @Override
    public void destroy() {

    }
}
