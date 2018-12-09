package controllers.authentication.filters;

import controllers.authentication.LoginController;
import dao.impl.hibernate.HibernateUserImpl;
import entities.users.User;
import sun.misc.BASE64Decoder;
import utils.SecurityUtils;
import utils.UserRoleRequestWrapper;
import utils.UserUtils;
import utils.context.RequestContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

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
