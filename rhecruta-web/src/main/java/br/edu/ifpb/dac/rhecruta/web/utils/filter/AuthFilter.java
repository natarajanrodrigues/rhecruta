/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.utils.filter;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.User;
import br.edu.ifpb.dac.rhecruta.shared.domain.enums.Role;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro Arthur
 */

@WebFilter(filterName = "AuthFilter", urlPatterns = {
    "/faces/candidate/*", "/faces/appraiser/*", "/faces/manager/*"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        System.out.println("OOOOIII!");
        User loggedUser = (User) httpRequest.getSession().getAttribute("loggedUser");

        if (loggedUser == null) {
            httpResponse.sendRedirect("/quickserv-web/faces/index.xhtml");
        } else {

            String path = httpRequest.getRequestURI();
            path = path.substring(httpRequest.getContextPath().length());
            Role role = loggedUser.getRole();
            if(isUnauthorizedAccess(role, path)) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
            chain.doFilter(request, response);
        }
    }

    private boolean isUnauthorizedAccess(Role role, String path) {
        switch (role) {
            case CANDIDATE:
                if (!path.startsWith("/faces/candidate")) {
                    return true;
                }
                break;
            case APPRAISER:
                if (!path.startsWith("/faces/appraiser")) {
                    return true;
                }
                break;
            case MANAGER:
                if (!path.startsWith("/faces/manager")) {
                    return true;
                }
                break;
        } return false;
    }

    @Override
    public void destroy() {
    }

}
