package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private String userName;
    private String role;
    private HttpServletRequest request;

    public UserRoleRequestWrapper(String userName, String role, HttpServletRequest request) {
        super(request);
        this.userName = userName;
        this.role =  role;
        this.request = request;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.role == null) {
            return  this.request.isUserInRole(role);
        }
        return this.role.equals(role);
    }

    @Override
    public Principal getUserPrincipal() {
        if (userName == null) {
            return request.getUserPrincipal();
        }

        return () -> userName;
    }
}
